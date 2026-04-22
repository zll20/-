package com.yb.algorithm.recommend;

import com.yb.utils.FileUtil;
import lombok.Data;
import org.springframework.stereotype.Component;
import weka.classifiers.Classifier;
import weka.classifiers.trees.RandomForest;
import weka.core.*;

import java.io.*;
import java.lang.reflect.Field;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * 推荐工厂类
 *
 * 功能概述：
 * 1. 提供多种推荐算法的实现，包括协同过滤（用户-物品矩阵、物品-用户矩阵）和基于机器学习的推荐。
 * 2. 支持模型训练、保存、加载和缓存管理。
 * 3. 实现静态工厂方法，方便外部调用构建不同类型的推荐器。
 *
 * 核心组件：
 * - ModelStorage：负责模型的持久化与缓存管理。
 * - UserItemMatrixRecommender：基于用户-物品矩阵的协同过滤推荐器（用协）。
 * - ItemUserMatrixRecommender：基于物品-用户矩阵的协同过滤推荐器（物协）。
 * - ModelLoader：用于加载训练好的模型并进行混合推荐。
 * - AlgorithmTrainBuilder：用于训练新的推荐模型。
 */
@Component
public class RecommendAlgorithmFactory {

    // 模型存储路径
    private static final String MODEL_BASE_DIR = "algorithm/recommend/models/";

    // 模型缓存
    private static final Map<String, Object> MODEL_CACHE = new ConcurrentHashMap<>();

    // 用户-物品矩阵缓存
    private static final Map<Long, Map<Long, Double>> userItemMatrix = new ConcurrentHashMap<>();

    // 物品-用户矩阵缓存
    private static final Map<Long, Map<Long, Double>> itemUserMatrix = new ConcurrentHashMap<>();

    /**
     * 模型存储工具类
     *
     * 负责模型的持久化与缓存管理，支持模型的保存、加载、存在性检查和缓存清理。
     */
    public static class ModelStorage {

        /**
         * 保存模型到本地
         *
         * @param modelName 模型名称
         * @param model     待保存的模型对象
         * @throws Exception 如果模型未实现Serializable接口或保存失败
         */
        public static void saveModel(String modelName, Object model) throws Exception {
            if (!(model instanceof Serializable)) {
                throw new IllegalArgumentException("模型必须实现Serializable接口");
            }

            String filePath = getModelFilePath(modelName);
            File modelFile = new File(filePath);

            // 确保目录存在
            File parentDir = modelFile.getParentFile();
            if (!parentDir.exists()) {
                parentDir.mkdirs();
            }

            try (ObjectOutputStream oos = new ObjectOutputStream(
                    new FileOutputStream(modelFile))) {
                oos.writeObject(model);
                MODEL_CACHE.put(modelName, model); // 写入内存缓存
                System.out.println("模型已保存: " + filePath);
            }
        }

        /**
         * 加载模型（优先从缓存读取）
         *
         * @param modelName 模型名称
         * @return 加载的模型对象
         * @throws Exception 如果模型文件不存在或加载失败
         */
        public static Object loadModel(String modelName) throws Exception {
            // 先查缓存
            if (MODEL_CACHE.containsKey(modelName)) {
                return MODEL_CACHE.get(modelName);
            }

            String filePath = getModelFilePath(modelName);
            File modelFile = new File(filePath);

            if (!modelFile.exists()) {
                throw new FileNotFoundException("模型文件不存在: " + filePath);
            }

            try (ObjectInputStream ois = new ObjectInputStream(
                    new FileInputStream(modelFile))) {
                Object model = ois.readObject();
                MODEL_CACHE.put(modelName, model);
                return model;
            }
        }

        /**
         * 检查模型是否存在
         *
         * @param modelName 模型名称
         * @return 是否存在
         */
        public static boolean hasModel(String modelName) {
            if (MODEL_CACHE.containsKey(modelName)) {
                return true;
            }

            String filePath = getModelFilePath(modelName);
            return new File(filePath).exists();
        }

        /**
         * 清除模型缓存
         */
        public static void clearCache() {
            MODEL_CACHE.clear();
        }

        /**
         * 获取模型文件完整路径
         *
         * @param modelName 模型名称
         * @return 完整路径
         */
        private static String getModelFilePath(String modelName) {
            // 确保有正确的后缀
            if (!modelName.endsWith(".ser") && !modelName.endsWith(".model")) {
                modelName = modelName + ".model";
            }

            // 构建完整路径
            String projectRoot = FileUtil.getClassPathStatic();
            String modelPath = projectRoot + "/" + MODEL_BASE_DIR + modelName;
            return modelPath;
        }
    }

    /**
     * 用户-物品矩阵推荐器（用协）
     *
     * 基于用户相似度计算，为指定用户推荐未交互过的物品。
     * 使用余弦相似度衡量用户之间的相似性。
     */
    public static class UserItemMatrixRecommender {
        protected Map<Long, Map<Long, Double>> matrix; // {用户ID -> {物品ID -> 评分}}
        protected List<Long> allItemIds;

        public UserItemMatrixRecommender(Map<Long, Map<Long, Double>> matrix) {
            this.matrix = matrix;
            // 提取所有物品ID
            this.allItemIds = extractAllItemIds(matrix);
        }

        /**
         * 推荐物品（基于用户相似度 - 用协）
         *
         * @param userId              用户ID
         * @param numRecommendations  推荐数量
         * @return 推荐物品列表
         */
        public List<Long> recommendItems(Long userId, int numRecommendations) {
            if (!matrix.containsKey(userId)) {
                return new ArrayList<>();
            }

            // 获取用户已交互的物品
            Map<Long, Double> userInteractions = matrix.get(userId);
            Set<Long> interactedItems = userInteractions.keySet();

            // 基于用户相似度的协同过滤
            Map<Long, Double> itemScores = new HashMap<>();

            // 计算用户相似度
            Map<Long, Double> userSimilarities = calculateUserSimilarities(userId);

            // 基于相似用户推荐物品
            for (Map.Entry<Long, Double> entry : userSimilarities.entrySet()) {
                Long similarUserId = entry.getKey();
                double similarity = entry.getValue();

                Map<Long, Double> similarUserItems = matrix.get(similarUserId);
                if (similarUserItems == null) continue;

                for (Map.Entry<Long, Double> itemEntry : similarUserItems.entrySet()) {
                    Long itemId = itemEntry.getKey();
                    // 跳过用户已交互的物品
                    if (interactedItems.contains(itemId)) continue;

                    double score = itemEntry.getValue() * similarity;
                    itemScores.put(itemId,
                            itemScores.getOrDefault(itemId, 0.0) + score);
                }
            }

            // 返回推荐结果
            return sortAndLimit(itemScores, numRecommendations);
        }

        /**
         * 计算用户相似度（余弦相似度）
         *
         * @param userId 用户ID
         * @return 相似用户及其相似度
         */
        protected Map<Long, Double> calculateUserSimilarities(Long userId) {
            Map<Long, Double> similarities = new HashMap<>();
            Map<Long, Double> userVector = matrix.get(userId);

            if (userVector == null) return similarities;

            for (Map.Entry<Long, Map<Long, Double>> entry : matrix.entrySet()) {
                Long otherUserId = entry.getKey();
                if (otherUserId.equals(userId)) continue;

                double similarity = cosineSimilarity(userVector, entry.getValue());
                if (similarity > 0) {
                    similarities.put(otherUserId, similarity);
                }
            }

            return similarities;
        }

        /**
         * 计算余弦相似度
         *
         * @param vec1 向量1
         * @param vec2 向量2
         * @return 余弦相似度
         */
        protected double cosineSimilarity(Map<Long, Double> vec1, Map<Long, Double> vec2) {
            double dotProduct = 0.0;
            double norm1 = 0.0;
            double norm2 = 0.0;

            // 找出共同项
            Set<Long> commonKeys = new HashSet<>(vec1.keySet());
            commonKeys.retainAll(vec2.keySet());

            for (Long key : commonKeys) {
                double v1 = vec1.get(key);
                double v2 = vec2.get(key);
                dotProduct += v1 * v2;
            }

            for (double v : vec1.values()) {
                norm1 += v * v;
            }

            for (double v : vec2.values()) {
                norm2 += v * v;
            }

            if (norm1 == 0 || norm2 == 0) return 0.0;

            return dotProduct / (Math.sqrt(norm1) * Math.sqrt(norm2));
        }

        /**
         * 排序并限制数量
         *
         * @param itemScores 物品评分映射
         * @param limit      限制数量
         * @return 排序后的物品ID列表
         */
        protected List<Long> sortAndLimit(Map<Long, Double> itemScores, int limit) {
            return itemScores.entrySet().stream()
                    .sorted((e1, e2) -> Double.compare(e2.getValue(), e1.getValue()))
                    .limit(limit)
                    .map(Map.Entry::getKey)
                    .collect(Collectors.toList());
        }

        /**
         * 提取所有物品ID
         *
         * @param matrix 用户-物品矩阵
         * @return 所有物品ID列表
         */
        protected List<Long> extractAllItemIds(Map<Long, Map<Long, Double>> matrix) {
            Set<Long> itemIdSet = new HashSet<>();
            for (Map<Long, Double> itemMap : matrix.values()) {
                itemIdSet.addAll(itemMap.keySet());
            }
            return new ArrayList<>(itemIdSet);
        }

        /**
         * 获取用户已交互的物品
         *
         * @param userId 用户ID
         * @return 已交互物品集合
         */
        protected Set<Long> getUserInteractedItems(Long userId) {
            if (matrix.containsKey(userId)) {
                return matrix.get(userId).keySet();
            }
            return new HashSet<>();
        }
    }

    /**
     * 物品-用户矩阵推荐器（物协）
     *
     * 基于物品相似度计算，为指定用户推荐未交互过的物品。
     * 使用余弦相似度衡量物品之间的相似性。
     */
    public static class ItemUserMatrixRecommender {
        protected Map<Long, Map<Long, Double>> matrix; // {物品ID -> {用户ID -> 评分}}
        protected List<Long> allItemIds;
        protected List<Long> allUserIds;

        public ItemUserMatrixRecommender(Map<Long, Map<Long, Double>> matrix) {
            this.matrix = matrix;
            // 提取所有物品ID和用户ID
            this.allItemIds = extractAllItemIds(matrix);
            this.allUserIds = extractAllUserIds(matrix);
        }

        /**
         * 推荐物品（基于物品相似度 - 物协）
         *
         * @param userId              用户ID
         * @param numRecommendations  推荐数量
         * @return 推荐物品列表
         */
        public List<Long> recommendItems(Long userId, int numRecommendations) {
            // 获取用户已交互的物品
            Set<Long> userInteractedItems = getUserInteractedItems(userId);

            if (userInteractedItems.isEmpty()) {
                return new ArrayList<>();
            }

            // 基于物品相似度的协同过滤
            Map<Long, Double> itemScores = new HashMap<>();

            // 计算物品相似度矩阵
            Map<Long, Map<Long, Double>> itemSimilarities = calculateItemSimilarities();

            // 对于用户交互过的每个物品，推荐相似物品
            for (Long interactedItemId : userInteractedItems) {
                Map<Long, Double> similarItems = itemSimilarities.get(interactedItemId);
                if (similarItems == null) continue;

                // 获取用户对当前物品的评分
                double userRating = getItemUserRating(interactedItemId, userId);

                for (Map.Entry<Long, Double> entry : similarItems.entrySet()) {
                    Long candidateItemId = entry.getKey();
                    double similarity = entry.getValue();

                    // 跳过用户已交互的物品
                    if (userInteractedItems.contains(candidateItemId)) continue;

                    // 推荐分数 = 用户对物品的评分 * 物品相似度
                    double score = userRating * similarity;
                    itemScores.put(candidateItemId,
                            itemScores.getOrDefault(candidateItemId, 0.0) + score);
                }
            }

            // 返回推荐结果
            return sortAndLimit(itemScores, numRecommendations);
        }

        /**
         * 计算物品相似度矩阵（基于共同用户）
         *
         * @return 物品相似度矩阵
         */
        private Map<Long, Map<Long, Double>> calculateItemSimilarities() {
            Map<Long, Map<Long, Double>> similarities = new HashMap<>();

            List<Long> itemIds = new ArrayList<>(matrix.keySet());
            int n = itemIds.size();

            // 为每个物品计算与其他物品的相似度
            for (int i = 0; i < n; i++) {
                Long item1 = itemIds.get(i);
                Map<Long, Double> item1Users = matrix.get(item1);
                if (item1Users == null || item1Users.isEmpty()) continue;

                for (int j = i + 1; j < n; j++) {
                    Long item2 = itemIds.get(j);
                    Map<Long, Double> item2Users = matrix.get(item2);
                    if (item2Users == null || item2Users.isEmpty()) continue;

                    // 计算余弦相似度
                    double similarity = calculateItemCosineSimilarity(item1Users, item2Users);

                    if (similarity > 0) {
                        // 双向存储相似度
                        similarities.computeIfAbsent(item1, k -> new HashMap<>())
                                .put(item2, similarity);
                        similarities.computeIfAbsent(item2, k -> new HashMap<>())
                                .put(item1, similarity);
                    }
                }
            }

            return similarities;
        }

        /**
         * 计算物品间的余弦相似度（基于共同用户评分）
         *
         * @param item1Users 物品1的用户评分
         * @param item2Users 物品2的用户评分
         * @return 余弦相似度
         */
        private double calculateItemCosineSimilarity(Map<Long, Double> item1Users, Map<Long, Double> item2Users) {
            double dotProduct = 0.0;
            double norm1 = 0.0;
            double norm2 = 0.0;

            // 找出共同用户
            Set<Long> commonUsers = new HashSet<>(item1Users.keySet());
            commonUsers.retainAll(item2Users.keySet());

            for (Long userId : commonUsers) {
                double v1 = item1Users.get(userId);
                double v2 = item2Users.get(userId);
                dotProduct += v1 * v2;
            }

            for (double v : item1Users.values()) {
                norm1 += v * v;
            }

            for (double v : item2Users.values()) {
                norm2 += v * v;
            }

            if (norm1 == 0 || norm2 == 0) return 0.0;

            return dotProduct / (Math.sqrt(norm1) * Math.sqrt(norm2));
        }

        /**
         * 获取用户对物品的评分
         *
         * @param itemId 物品ID
         * @param userId 用户ID
         * @return 用户对物品的评分
         */
        private double getItemUserRating(Long itemId, Long userId) {
            if (matrix.containsKey(itemId) && matrix.get(itemId).containsKey(userId)) {
                return matrix.get(itemId).get(userId);
            }
            return 0.0;
        }

        /**
         * 获取用户已交互的物品
         *
         * @param userId 用户ID
         * @return 已交互物品集合
         */
        private Set<Long> getUserInteractedItems(Long userId) {
            Set<Long> interactedItems = new HashSet<>();

            for (Map.Entry<Long, Map<Long, Double>> entry : matrix.entrySet()) {
                Long itemId = entry.getKey();
                Map<Long, Double> users = entry.getValue();

                if (users.containsKey(userId)) {
                    interactedItems.add(itemId);
                }
            }

            return interactedItems;
        }

        /**
         * 提取所有物品ID
         *
         * @param matrix 物品-用户矩阵
         * @return 所有物品ID列表
         */
        private List<Long> extractAllItemIds(Map<Long, Map<Long, Double>> matrix) {
            return new ArrayList<>(matrix.keySet());
        }

        /**
         * 提取所有用户ID
         *
         * @param matrix 物品-用户矩阵
         * @return 所有用户ID列表
         */
        private List<Long> extractAllUserIds(Map<Long, Map<Long, Double>> matrix) {
            Set<Long> userIds = new HashSet<>();
            for (Map<Long, Double> userMap : matrix.values()) {
                userIds.addAll(userMap.keySet());
            }
            return new ArrayList<>(userIds);
        }

        /**
         * 排序并限制数量
         *
         * @param itemScores 物品评分映射
         * @param limit      限制数量
         * @return 排序后的物品ID列表
         */
        private List<Long> sortAndLimit(Map<Long, Double> itemScores, int limit) {
            return itemScores.entrySet().stream()
                    .sorted((e1, e2) -> Double.compare(e2.getValue(), e1.getValue()))
                    .limit(limit)
                    .map(Map.Entry::getKey)
                    .collect(Collectors.toList());
        }
    }

    /**
     * 模型加载器（支持混合推荐）
     *
     * 加载训练好的Weka模型及其元数据，结合模型预测和协同过滤推荐，提供更全面的推荐结果。
     */
    public static class ModelLoader {
        private Classifier wekaModel;
        private String modelType;
        private String modelName;

        // 训练时保存的元数据
        private List<Long> allItemIds;
        private Map<Long, Map<String, Object>> userFeatures;
        private Map<Long, Map<String, Object>> itemFeatures;
        private List<String> featureNames;
        private Map<Long, Set<Long>> userInteractedItems; // 用户交互过的物品

        public ModelLoader(String modelName) throws Exception {
            this.modelName = modelName;
            loadModel();
        }

        /**
         * 加载模型和元数据
         *
         * @throws Exception 如果模型加载失败
         */
        private void loadModel() {
            try {
                Object modelObj = ModelStorage.loadModel(modelName);

                if (modelObj instanceof Classifier) {
                    this.wekaModel = (Classifier) modelObj;

                    // 判断模型类型
                    if (wekaModel instanceof RandomForest) {
                        modelType = "随机森林";
                    } else if (wekaModel.getClass().getName().contains("MultilayerPerceptron")) {
                        modelType = "NCF深度学习";
                    } else {
                        modelType = "Unknown";
                    }

                    // 尝试加载元数据
                    try {
                        loadModelMetadata();
                    } catch (Exception e) {
                        System.out.println("模型元数据加载失败: " + e.getMessage());
                    }
                } else {
                    throw new IllegalArgumentException("加载的模型不是Weka Classifier类型");
                }
            } catch (Exception e)
            {
                System.out.println("模型加载失败: " + e.getMessage());
            }
        }

        /**
         * 加载模型元数据
         *
         * @throws Exception 如果元数据加载失败
         */
        private void loadModelMetadata() throws Exception {
            String metadataFile = modelName.replace(".model", "_metadata.ser");
            try {
                Object metadataObj = ModelStorage.loadModel(metadataFile);
                if (metadataObj instanceof Map) {
                    Map<String, Object> metadata = (Map<String, Object>) metadataObj;
                    this.allItemIds = (List<Long>) metadata.get("allItemIds");
                    this.userFeatures = (Map<Long, Map<String, Object>>) metadata.get("userFeatures");
                    this.itemFeatures = (Map<Long, Map<String, Object>>) metadata.get("itemFeatures");
                    this.featureNames = (List<String>) metadata.get("featureNames");
                    this.userInteractedItems = (Map<Long, Set<Long>>) metadata.get("userInteractedItems");

                    System.out.println("模型元数据加载成功，用户数量: " +
                            (userFeatures != null ? userFeatures.size() : 0) +
                            ", 物品数量: " + (allItemIds != null ? allItemIds.size() : 0));
                }
            } catch (Exception e) {
                // 元数据加载失败，使用默认值
                System.out.println("警告: 模型元数据不存在或格式错误: " + e.getMessage());
            }
        }

        /**
         * 构建用户-物品矩阵推荐器（用协）
         *
         * @param list 用户行为数据列表
         * @return 用户-物品矩阵推荐器
         */
        public UserItemMatrixRecommender buildUserItemMatrix(List<UserBehavior> list) {
            buildUserItemMatrixInternal(list);
            return new UserItemMatrixRecommender(userItemMatrix);
        }

        /**
         * 构建物品-用户矩阵推荐器（物协）
         *
         * @param list 用户行为数据列表
         * @return 物品-用户矩阵推荐器
         */
        public ItemUserMatrixRecommender buildItemUserMatrix(List<UserBehavior> list) {
            buildItemUserMatrixInternal(list);
            return new ItemUserMatrixRecommender(itemUserMatrix);
        }

        /**
         * 使用模型进行推荐（默认用协补充）
         *
         * @param userId              用户ID
         * @param numRecommendations  推荐数量
         * @return 推荐物品列表
         */
        public List<Long> recommendItems(Long userId, int numRecommendations) {
            return recommendItems(userId, numRecommendations, true);
        }

        /**
         * 使用模型进行推荐（可选择用协或物协补充）
         *
         * @param userId              用户ID
         * @param numRecommendations  推荐数量
         * @param useUserItemMatrix   是否使用用户-物品矩阵（用协）
         * @return 推荐物品列表
         */
        public List<Long> recommendItems(Long userId, int numRecommendations, boolean useUserItemMatrix) {
            List<Long> modelRecommendations = new ArrayList<>();

            // 1. 使用模型进行推荐
            try {
                if (wekaModel != null && allItemIds != null && !allItemIds.isEmpty()) {
                    modelRecommendations = predictWithModel(userId);
                    System.out.println("模型推荐了 " + modelRecommendations.size() + " 个物品");
                }
            } catch (Exception e) {
                System.out.println("模型预测失败: " + e.getMessage());
                e.printStackTrace();
            }

            // 2. 如果模型推荐足够，直接返回（不超过指定数量）
            if (modelRecommendations.size() >= numRecommendations) {
                return modelRecommendations.subList(0, numRecommendations);
            }

            // 3. 如果模型推荐不足，补充协同过滤推荐
            List<Long> cfRecommendations = new ArrayList<>();
            try {
                // 从缓存的矩阵中获取推荐
                if (useUserItemMatrix) {
                    // 使用用户-物品矩阵（用协）
                    UserItemMatrixRecommender recommender = new UserItemMatrixRecommender(userItemMatrix);
                    cfRecommendations = recommender.recommendItems(userId, numRecommendations - modelRecommendations.size());
                    System.out.println("用协补充了 " + cfRecommendations.size() + " 个物品");
                } else {
                    // 使用物品-用户矩阵（物协）
                    ItemUserMatrixRecommender recommender = new ItemUserMatrixRecommender(itemUserMatrix);
                    cfRecommendations = recommender.recommendItems(userId, numRecommendations - modelRecommendations.size());
                    System.out.println("物协补充了 " + cfRecommendations.size() + " 个物品");
                }
            } catch (Exception e) {
                System.out.println("协同过滤推荐失败: " + e.getMessage());
            }

            // 4. 合并推荐结果（去重）
            Set<Long> finalRecommendations = new LinkedHashSet<>(modelRecommendations);
            for (Long itemId : cfRecommendations) {
                if (finalRecommendations.size() < numRecommendations) {
                    finalRecommendations.add(itemId);
                } else {
                    break;
                }
            }

            System.out.println("最终推荐了 " + finalRecommendations.size() + " 个物品");
            return new ArrayList<>(finalRecommendations);
        }

        /**
         * 使用模型进行预测（并剔除用户已交互过的物品）
         *
         * @param userId 用户ID
         * @return 预测物品列表
         */
        private List<Long> predictWithModel(Long userId) {
            if (wekaModel == null || allItemIds == null || allItemIds.isEmpty()) {
                System.out.println("模型或物品列表为空，无法进行预测");
                return new ArrayList<>();
            }

            // 获取用户已交互的物品
            Set<Long> userInteractedSet = getUserInteractedItems(userId);
            System.out.println("用户 " + userId + " 已交互的物品数量: " + userInteractedSet.size());

            Map<Long, Double> itemScores = new HashMap<>();

            try {
                // 获取用户特征
                Map<String, Object> userFeature = userFeatures != null ?
                        userFeatures.get(userId) : new HashMap<>();

                if (userFeature.isEmpty()) {
                    System.out.println("用户 " + userId + " 的特征数据为空");
                }

                int predictedCount = 0;
                // 为每个物品生成预测
                for (Long itemId : allItemIds) {
                    // 跳过用户已交互的物品
                    if (userInteractedSet.contains(itemId)) {
                        continue;
                    }

                    try {
                        // 获取物品特征
                        Map<String, Object> itemFeature = itemFeatures != null ?
                                itemFeatures.get(itemId) : new HashMap<>();

                        // 创建预测实例
                        Instance instance = createPredictionInstance(userId, itemId, userFeature, itemFeature);
                        if (instance != null) {
                            double prediction = wekaModel.classifyInstance(instance);
                            itemScores.put(itemId, prediction);
                            predictedCount++;
                        }
                    } catch (Exception e) {
                        // 单个物品预测失败，继续下一个
                        System.out.println("物品 " + itemId + " 预测失败: " + e.getMessage());
                    }
                }

                System.out.println("模型为 " + userId + " 预测了 " + predictedCount + " 个物品的评分");
            } catch (Exception e) {
                System.out.println("模型预测过程失败: " + e.getMessage());
                e.printStackTrace();
            }

            // 按预测分数排序返回
            return sortAndLimit(itemScores, allItemIds.size());
        }

        /**
         * 获取用户已交互的物品
         *
         * @param userId 用户ID
         * @return 已交互物品集合
         */
        private Set<Long> getUserInteractedItems(Long userId) {
            Set<Long> interactedItems = new HashSet<>();

            // 1. 从元数据中获取训练时的交互记录
            if (userInteractedItems != null && userInteractedItems.containsKey(userId)) {
                interactedItems.addAll(userInteractedItems.get(userId));
            }

            // 2. 从当前缓存矩阵中获取
            if (userItemMatrix.containsKey(userId)) {
                interactedItems.addAll(userItemMatrix.get(userId).keySet());
            }

            return interactedItems;
        }

        /**
         * 创建预测实例
         *
         * @param userId       用户ID
         * @param itemId       物品ID
         * @param userFeature  用户特征
         * @param itemFeature  物品特征
         * @return 预测实例
         */
        private Instance createPredictionInstance(Long userId, Long itemId,
                                                  Map<String, Object> userFeature,
                                                  Map<String, Object> itemFeature) {
            try {
                // 获取数据集结构
                Instances datasetTemplate = null;
                try {
                    // 尝试获取训练数据集结构
                    Field datasetField = wekaModel.getClass().getDeclaredField("m_Train");
                    datasetField.setAccessible(true);
                    datasetTemplate = (Instances) datasetField.get(wekaModel);
                } catch (Exception e) {
                    // 如果无法获取，使用默认方式
                    datasetTemplate = createDefaultDataset();
                }

                if (datasetTemplate == null) {
                    System.out.println("无法创建数据集模板");
                    return null;
                }

                // 创建实例
                double[] values = new double[datasetTemplate.numAttributes()];

                // 设置特征值
                for (int i = 0; i < datasetTemplate.numAttributes() - 1; i++) {
                    Attribute attr = datasetTemplate.attribute(i);
                    String attrName = attr.name();

                    if ("userId".equals(attrName)) {
                        values[i] = userId;
                    } else if ("itemId".equals(attrName)) {
                        values[i] = itemId;
                    } else if (userFeature.containsKey(attrName)) {
                        // 用户特征
                        Object value = userFeature.get(attrName);
                        setAttributeValue(attr, values, i, value);
                    } else if (itemFeature.containsKey(attrName)) {
                        // 物品特征
                        Object value = itemFeature.get(attrName);
                        setAttributeValue(attr, values, i, value);
                    } else {
                        values[i] = Utils.missingValue();
                    }
                }

                // 设置目标值为缺失值
                values[datasetTemplate.numAttributes() - 1] = Utils.missingValue();

                Instance instance = new DenseInstance(1.0, values);
                instance.setDataset(datasetTemplate);

                return instance;
            } catch (Exception e) {
                System.out.println("创建预测实例失败: " + e.getMessage());
                return null;
            }
        }

        /**
         * 设置属性值
         *
         * @param attr   属性
         * @param values 值数组
         * @param index  索引
         * @param value  值
         */
        private void setAttributeValue(Attribute attr, double[] values, int index, Object value) {
            if (value == null) {
                values[index] = Utils.missingValue();
            } else if (attr.isNumeric()) {
                if (value instanceof Number) {
                    values[index] = ((Number) value).doubleValue();
                } else {
                    try {
                        values[index] = Double.parseDouble(value.toString());
                    } catch (NumberFormatException e) {
                        values[index] = Utils.missingValue();
                    }
                }
            } else {
                // 分类属性
                String strValue = String.valueOf(value);
                int valueIndex = attr.indexOfValue(strValue);
                if (valueIndex >= 0) {
                    values[index] = valueIndex;
                } else {
                    values[index] = Utils.missingValue();
                }
            }
        }

        /**
         * 创建默认数据集结构
         *
         * @return 默认数据集
         */
        private Instances createDefaultDataset() {
            ArrayList<Attribute> attrs = new ArrayList<>();
            attrs.add(new Attribute("userId"));
            attrs.add(new Attribute("itemId"));

            // 如果有特征名，添加对应的属性
            if (featureNames != null) {
                for (String featName : featureNames) {
                    if (!featName.equals("userId") && !featName.equals("itemId")) {
                        attrs.add(new Attribute(featName));
                    }
                }
            }

            List<String> classLabels = Arrays.asList("0", "1");
            attrs.add(new Attribute("score", classLabels));

            Instances dataset = new Instances("temp", attrs, 0);
            dataset.setClassIndex(dataset.numAttributes() - 1);
            return dataset;
        }

        /**
         * 排序并限制数量
         *
         * @param itemScores 物品评分映射
         * @param limit      限制数量
         * @return 排序后的物品ID列表
         */
        private List<Long> sortAndLimit(Map<Long, Double> itemScores, int limit) {
            return itemScores.entrySet().stream()
                    .sorted((e1, e2) -> Double.compare(e2.getValue(), e1.getValue()))
                    .limit(limit)
                    .map(Map.Entry::getKey)
                    .collect(Collectors.toList());
        }
    }

    /**
     * 算法训练构建器（基于Weka实现）
     *
     * 支持训练随机森林（RandomForest）和神经网络（NCF）两种推荐模型。
     * 自动提取用户特征、物品特征和交互数据，构建训练数据集。
     */
    public static class AlgorithmTrainBuilder {
        private String algorithmType;
        private String className;

        // 训练时保存的元数据
        private List<Long> allItemIds;
        private Map<Long, Map<String, Object>> userFeatures;
        private Map<Long, Map<String, Object>> itemFeatures;
        private List<String> featureNames;
        private Map<Long, Set<Long>> userInteractedItems; // 用户交互过的物品

        public AlgorithmTrainBuilder(String algorithmType, String className) {
            this.algorithmType = algorithmType;
            this.className = className;
        }

        /**
         * 训练模型
         *
         * @param userDataList     用户数据列表
         * @param itemDataList     物品数据列表
         * @param interactionList  用户行为数据列表
         * @throws Exception 如果训练失败
         */
        public void train(List<Map<String, Object>> userDataList,
                          List<Map<String, Object>> itemDataList,
                          List<UserBehavior> interactionList) throws Exception {

            // 保存元数据
            saveMetadata(userDataList, itemDataList, interactionList);

            // 准备训练数据
            Instances trainingData = prepareTrainingData(userDataList, itemDataList, interactionList);

            Classifier model;

            if ("NCF深度学习".equalsIgnoreCase(algorithmType)) {
                model = trainNCFModel(trainingData);
            } else if ("随机森林".equalsIgnoreCase(algorithmType)) {
                model = trainRandomForestModel(trainingData);
            } else {
                throw new IllegalArgumentException("不支持的算法类型: " + algorithmType);
            }

            // 保存模型
            String modelName = className + ".model";
            ModelStorage.saveModel(modelName, model);

            // 保存元数据
            saveModelMetadata(modelName);

            System.out.println("模型训练完成并保存为: " + modelName);
        }

        /**
         * 保存元数据
         *
         * @param userDataList     用户数据列表
         * @param itemDataList     物品数据列表
         * @param interactionList  用户行为数据列表
         */
        private void saveMetadata(List<Map<String, Object>> userDataList,
                                  List<Map<String, Object>> itemDataList,
                                  List<UserBehavior> interactionList) {
            // 提取所有物品ID
            Set<Long> itemIdSet = new HashSet<>();
            for (UserBehavior behavior : interactionList) {
                itemIdSet.add(behavior.getItemId());
            }
            this.allItemIds = new ArrayList<>(itemIdSet);

            // 构建用户特征映射
            this.userFeatures = new HashMap<>();
            for (Map<String, Object> user : userDataList) {
                Long userId = ((Number) user.get("userId")).longValue();
                userFeatures.put(userId, user);
            }

            // 构建物品特征映射
            this.itemFeatures = new HashMap<>();
            for (Map<String, Object> item : itemDataList) {
                Long itemId = ((Number) item.get("itemId")).longValue();
                itemFeatures.put(itemId, item);
            }

            // 提取特征名
            Set<String> featureNameSet = new HashSet<>();
            if (!userDataList.isEmpty()) {
                featureNameSet.addAll(userDataList.get(0).keySet());
            }
            if (!itemDataList.isEmpty()) {
                featureNameSet.addAll(itemDataList.get(0).keySet());
            }
            this.featureNames = new ArrayList<>(featureNameSet);

            // 构建用户交互物品映射
            this.userInteractedItems = new HashMap<>();
            for (UserBehavior behavior : interactionList) {
                userInteractedItems
                        .computeIfAbsent(behavior.getUserId(), k -> new HashSet<>())
                        .add(behavior.getItemId());
            }

            System.out.println("元数据统计:");
            System.out.println("  用户数量: " + userFeatures.size());
            System.out.println("  物品数量: " + allItemIds.size());
            System.out.println("  特征数量: " + featureNames.size());
            System.out.println("  交互记录数: " + interactionList.size());
        }

        /**
         * 保存模型元数据
         *
         * @param modelName 模型名称
         * @throws Exception 如果保存失败
         */
        private void saveModelMetadata(String modelName) throws Exception {
            Map<String, Object> metadata = new HashMap<>();
            metadata.put("allItemIds", allItemIds);
            metadata.put("userFeatures", userFeatures);
            metadata.put("itemFeatures", itemFeatures);
            metadata.put("featureNames", featureNames);
            metadata.put("userInteractedItems", userInteractedItems);

            String metadataFile = modelName.replace(".model", "_metadata.ser");
            ModelStorage.saveModel(metadataFile, metadata);

            System.out.println("模型元数据已保存: " + metadataFile);
            System.out.println("  用户交互记录统计:");
            for (Map.Entry<Long, Set<Long>> entry : userInteractedItems.entrySet()) {
                System.out.println("    用户 " + entry.getKey() + ": " + entry.getValue().size() + " 个物品");
            }
        }

        /**
         * 准备训练数据（动态构建特征）
         *
         * @param userDataList     用户数据列表
         * @param itemDataList     物品数据列表
         * @param interactionList  用户行为数据列表
         * @return 训练数据集
         */
        private Instances prepareTrainingData(List<Map<String, Object>> userDataList,
                                              List<Map<String, Object>> itemDataList,
                                              List<UserBehavior> interactionList) {

            // 创建用户和物品的映射
            Map<Long, Map<String, Object>> userMap = new HashMap<>();
            Map<Long, Map<String, Object>> itemMap = new HashMap<>();

            userDataList.forEach(user -> {
                Long userId = ((Number) user.get("userId")).longValue();
                userMap.put(userId, user);
            });

            itemDataList.forEach(item -> {
                Long itemId = ((Number) item.get("itemId")).longValue();
                itemMap.put(itemId, item);
            });

            // 收集所有可能的特征字段
            Set<String> allFeatureNames = new HashSet<>();
            allFeatureNames.add("userId");
            allFeatureNames.add("itemId");

            // 从用户数据收集特征
            if (!userDataList.isEmpty()) {
                Map<String, Object> sampleUser = userDataList.get(0);
                for (String key : sampleUser.keySet()) {
                    if (!"userId".equals(key)) {
                        allFeatureNames.add(key);
                    }
                }
            }

            // 从物品数据收集特征
            if (!itemDataList.isEmpty()) {
                Map<String, Object> sampleItem = itemDataList.get(0);
                for (String key : sampleItem.keySet()) {
                    if (!"itemId".equals(key)) {
                        allFeatureNames.add(key);
                    }
                }
            }

            // 创建属性列表
            ArrayList<Attribute> attributes = new ArrayList<>();

            // 处理每个特征
            for (String featureName : allFeatureNames) {
                if ("userId".equals(featureName) || "itemId".equals(featureName)) {
                    // ID字段作为数值属性
                    attributes.add(new Attribute(featureName));
                } else {
                    // 判断特征类型
                    Object sampleValue = findSampleValue(featureName, userMap, itemMap);

                    if (sampleValue instanceof Number) {
                        attributes.add(new Attribute(featureName));
                    } else {
                        // 分类属性，收集所有可能的值
                        Set<String> values = collectAllValues(featureName, userMap, itemMap);
                        if (values.isEmpty()) {
                            // 如果没有值，作为数值属性
                            attributes.add(new Attribute(featureName));
                        } else {
                            ArrayList<String> nominalValues = new ArrayList<>(values);
                            attributes.add(new Attribute(featureName, nominalValues));
                        }
                    }
                }
            }

            // 添加目标属性（评分）
            attributes.add(new Attribute("score"));

            // 创建数据集
            Instances dataset = new Instances("RecommendationDataset", attributes, interactionList.size());
            dataset.setClassIndex(dataset.numAttributes() - 1);

            // 填充数据
            for (UserBehavior behavior : interactionList) {
                double[] values = new double[dataset.numAttributes()];

                Long userId = behavior.getUserId();
                Long itemId = behavior.getItemId();
                Map<String, Object> userData = userMap.get(userId);
                Map<String, Object> itemData = itemMap.get(itemId);

                int attrIndex = 0;
                for (String featureName : allFeatureNames) {
                    Attribute attr = attributes.get(attrIndex);

                    if ("userId".equals(featureName)) {
                        values[attrIndex] = userId;
                    } else if ("itemId".equals(featureName)) {
                        values[attrIndex] = itemId;
                    } else {
                        Object value = null;

                        // 从用户数据或物品数据中获取特征值
                        if (userData != null && userData.containsKey(featureName)) {
                            value = userData.get(featureName);
                        } else if (itemData != null && itemData.containsKey(featureName)) {
                            value = itemData.get(featureName);
                        }

                        if (value != null) {
                            if (attr.isNumeric()) {
                                values[attrIndex] = ((Number) value).doubleValue();
                            } else {
                                String strValue = String.valueOf(value);
                                int valueIndex = attr.indexOfValue(strValue);
                                if (valueIndex >= 0) {
                                    values[attrIndex] = valueIndex;
                                } else {
                                    values[attrIndex] = Utils.missingValue();
                                }
                            }
                        } else {
                            values[attrIndex] = Utils.missingValue();
                        }
                    }
                    attrIndex++;
                }

                // 设置目标值
                values[attrIndex] = behavior.getScore();

                dataset.add(new DenseInstance(1.0, values));
            }

            System.out.println("训练数据集创建完成: " + dataset.numInstances() + " 条记录");
            return dataset;
        }

        /**
         * 查找特征样本值
         *
         * @param featureName 特征名称
         * @param userMap     用户映射
         * @param itemMap     物品映射
         * @return 特征样本值
         */
        private Object findSampleValue(String featureName,
                                       Map<Long, Map<String, Object>> userMap,
                                       Map<Long, Map<String, Object>> itemMap) {
            // 先尝试从用户数据中查找
            for (Map<String, Object> userData : userMap.values()) {
                if (userData.containsKey(featureName)) {
                    return userData.get(featureName);
                }
            }

            // 再从物品数据中查找
            for (Map<String, Object> itemData : itemMap.values()) {
                if (itemData.containsKey(featureName)) {
                    return itemData.get(featureName);
                }
            }

            return null;
        }

        /**
         * 收集特征的所有可能值
         *
         * @param featureName 特征名称
         * @param userMap     用户映射
         * @param itemMap     物品映射
         * @return 特征值集合
         */
        private Set<String> collectAllValues(String featureName,
                                             Map<Long, Map<String, Object>> userMap,
                                             Map<Long, Map<String, Object>> itemMap) {
            Set<String> values = new HashSet<>();

            // 收集用户数据中的值
            for (Map<String, Object> userData : userMap.values()) {
                if (userData.containsKey(featureName)) {
                    Object value = userData.get(featureName);
                    if (value != null) {
                        values.add(String.valueOf(value));
                    }
                }
            }

            // 收集物品数据中的值
            for (Map<String, Object> itemData : itemMap.values()) {
                if (itemData.containsKey(featureName)) {
                    Object value = itemData.get(featureName);
                    if (value != null) {
                        values.add(String.valueOf(value));
                    }
                }
            }

            return values;
        }

        /**
         * 训练NCF模型（使用Weka的MultilayerPerceptron）
         *
         * @param trainingData 训练数据
         * @return NCF模型
         * @throws Exception 如果训练失败
         */
        private Classifier trainNCFModel(Instances trainingData) throws Exception {
            weka.classifiers.functions.MultilayerPerceptron mlp =
                    new weka.classifiers.functions.MultilayerPerceptron();

            // 配置神经网络参数
            mlp.setLearningRate(0.3);
            mlp.setMomentum(0.2);
            mlp.setTrainingTime(500);
            mlp.setHiddenLayers("50,25"); // 两层隐藏层

            // 训练模型
            mlp.buildClassifier(trainingData);

            return mlp;
        }

        /**
         * 训练随机森林模型
         *
         * @param trainingData 训练数据
         * @return 随机森林模型
         * @throws Exception 如果训练失败
         */
        private Classifier trainRandomForestModel(Instances trainingData) throws Exception {
            RandomForest rf = new RandomForest();

            // 配置参数（Weka 3.8.5兼容）
            rf.setNumIterations(100); // 树的数量
            rf.setMaxDepth(0); // 0表示无限制

            // 训练模型
            rf.buildClassifier(trainingData);

            return rf;
        }
    }

    // ======================== 静态工厂方法 ========================

    /**
     * 构建用户-物品矩阵（用协）
     *
     * @param list 用户行为数据列表
     */
    private static void buildUserItemMatrixInternal(List<UserBehavior> list) {
        userItemMatrix.clear();
        for (UserBehavior behavior : list) {
            userItemMatrix
                    .computeIfAbsent(behavior.getUserId(), k -> new HashMap<>())
                    .put(behavior.getItemId(), behavior.getScore());
        }
    }

    /**
     * 构建物品-用户矩阵（物协）
     *
     * @param list 用户行为数据列表
     */
    private static void buildItemUserMatrixInternal(List<UserBehavior> list) {
        itemUserMatrix.clear();
        for (UserBehavior behavior : list) {
            itemUserMatrix
                    .computeIfAbsent(behavior.getItemId(), k -> new HashMap<>())
                    .put(behavior.getUserId(), behavior.getScore());
        }
    }

    /**
     * 静态工厂方法
     *
     * @param list 用户行为数据列表
     * @return 用户-物品矩阵推荐器
     */
    public static UserItemMatrixRecommender buildUserItemMatrix(List<UserBehavior> list) {
        buildUserItemMatrixInternal(list);
        return new UserItemMatrixRecommender(userItemMatrix);
    }

    /**
     * 静态工厂方法
     *
     * @param list 用户行为数据列表
     * @return 物品-用户矩阵推荐器
     */
    public static ItemUserMatrixRecommender buildItemUserMatrix(List<UserBehavior> list) {
        buildItemUserMatrixInternal(list);
        return new ItemUserMatrixRecommender(itemUserMatrix);
    }

    /**
     * 静态工厂方法
     *
     * @param algorithmType 算法类型
     * @param className     类名
     * @return 算法训练构建器
     */
    public static AlgorithmTrainBuilder buildAlgorithm(String algorithmType, String className) {
        return new AlgorithmTrainBuilder(algorithmType, className);
    }

    /**
     * 静态工厂方法
     *
     * @param modelName 模型名称
     * @return 模型加载器
     * @throws Exception 如果加载失败
     */
    public static ModelLoader loadModel(String modelName) throws Exception {
        return new ModelLoader(modelName);
    }

    /**
     * 检查是否存在指定模型
     *
     * @param modelName 模型名称
     * @return 是否存在
     */
    public static boolean hasModel(String modelName) {
        return ModelStorage.hasModel(modelName);
    }

    /**
     * 获取所有可用模型
     *
     * @return 模型名称列表
     */
    public static List<String> listModels() {
        List<String> models = new ArrayList<>();
        try {
            String projectRoot = System.getProperty("user.dir");
            String modelDirPath = projectRoot + "/" + MODEL_BASE_DIR;
            File modelDir = new File(modelDirPath);

            if (modelDir.exists() && modelDir.isDirectory()) {
                File[] files = modelDir.listFiles((dir, name) ->
                        name.endsWith(".model") || name.endsWith(".ser"));

                if (files != null) {
                    for (File file : files) {
                        models.add(file.getName());
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("获取模型列表失败: " + e.getMessage());
        }

        return models;
    }

    /**
     * 清除缓存
     */
    public static void clearCache() {
        userItemMatrix.clear();
        itemUserMatrix.clear();
        MODEL_CACHE.clear();
    }

    /**
     * 用户行为实体类，记录用户对某个物品的操作及评分
     */
    @Data
    public static class UserBehavior {
        private Long userId;   // 用户ID
        private Long itemId;   // 物品ID
        private Double score;    // 用户对该物品的评分

        /**
         * 构造方法：带评分初始化
         *
         * @param userId 用户ID
         * @param itemId 物品ID
         * @param score  评分值
         */
        public UserBehavior(Long userId, Long itemId, Double score) {
            this.userId = userId;
            this.itemId = itemId;
            this.score = score;
        }

        /**
         * 构造方法：默认评分为1.0
         *
         * @param userId 用户ID
         * @param itemId 物品ID
         */
        public UserBehavior(Long userId, Long itemId) {
            this.userId = userId;
            this.itemId = itemId;
            this.score = 1.0;
        }
    }
}