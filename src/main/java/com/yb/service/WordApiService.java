package com.yb.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yb.entity.DancikuEntity;
import com.yb.utils.HttpUtils;
import com.yb.utils.WordBookUtil;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * 单词API服务 - 从开源词库+第三方API实时获取单词数据（不保存到数据库）
 */
@Service
public class WordApiService {

    @Autowired
    private DancikuService dancikuService;

    /**
     * 从开源词库获取单词列表，并实时调用第三方API获取详情（分页）
     * @param jibie 单词级别
     * @param page 页码
     * @param limit 每页数量
     * @return 包含列表和总数的 Map
     */
    public Map<String, Object> getWordsFromApi(String jibie, Integer page, Integer limit) throws Exception {
        Map<String, Object> result = new HashMap<>();
        
        List<String> allWords = WordBookUtil.getWordList(jibie);
        
        if (allWords.isEmpty()) {
            result.put("list", new ArrayList<>());
            result.put("totalPage", 0);
            result.put("currPage", page);
            result.put("total", 0);
            result.put("message", "该级别暂无单词数据");
            return result;
        }
        
        int total = allWords.size();
        int startIndex = (page - 1) * limit;
        int endIndex = Math.min(startIndex + limit, total);
        
        if (startIndex >= total) {
            result.put("list", new ArrayList<>());
            result.put("totalPage", (total + limit - 1) / limit);
            result.put("currPage", page);
            result.put("total", total);
            return result;
        }
        
        List<Map<String, Object>> wordList = new ArrayList<>();
        
        for (int i = startIndex; i < endIndex; i++) {
            String word = allWords.get(i);
            try {
                Map<String, Object> wordDetail = getWordDetailFromApi(word);
                if (wordDetail != null) {
                    wordDetail.put("index", i + 1);
                    wordList.add(wordDetail);
                }
            } catch (Exception e) {
                System.err.println("获取单词详情失败：" + word + "，" + e.getMessage());
            }
        }
        
        result.put("list", wordList);
        result.put("totalPage", (total + limit - 1) / limit);
        result.put("currPage", page);
        result.put("total", total);
        
        return result;
    }

    /**
     * 从第三方 API 实时获取单个单词详情
     */
    public Map<String, Object> getWordDetailFromApi(String word) throws Exception {
        String apiUrl = "https://api.dictionaryapi.dev/api/v2/entries/en/" + word;
        
        String response = HttpUtils.doGet(apiUrl);
        
        if (response == null || response.isEmpty()) {
            return null;
        }

        JSONArray wordsArray = JSONArray.parseArray(response);
        if (wordsArray == null || wordsArray.isEmpty()) {
            return null;
        }

        JSONObject wordObj = wordsArray.getJSONObject(0);
        
        Map<String, Object> wordDetail = new HashMap<>();
        wordDetail.put("danci", word);

        if (wordObj.containsKey("phonetics")) {
            JSONArray phonetics = wordObj.getJSONArray("phonetics");
            if (phonetics != null && !phonetics.isEmpty()) {
                JSONObject phonetic = phonetics.getJSONObject(0);
                if (phonetic.containsKey("text")) {
                    wordDetail.put("duyin", phonetic.getString("text"));
                }
                if (phonetic.containsKey("audio") && phonetic.getString("audio") != null && !phonetic.getString("audio").isEmpty()) {
                    wordDetail.put("audio", phonetic.getString("audio"));
                }
            }
        }

        StringBuilder shiyiBuilder = new StringBuilder();
        StringBuilder lijuBuilder = new StringBuilder();

        if (wordObj.containsKey("meanings")) {
            JSONArray meanings = wordObj.getJSONArray("meanings");
            for (int i = 0; i < meanings.size() && i < 3; i++) {
                JSONObject meaning = meanings.getJSONObject(i);
                String partOfSpeech = meaning.getString("partOfSpeech");
                
                if (meaning.containsKey("definitions")) {
                    JSONArray definitions = meaning.getJSONArray("definitions");
                    for (int j = 0; j < definitions.size() && j < 2; j++) {
                        JSONObject def = definitions.getJSONObject(j);
                        
                        if (def.containsKey("definition")) {
                            if (shiyiBuilder.length() > 0) {
                                shiyiBuilder.append("; ");
                            }
                            shiyiBuilder.append(partOfSpeech).append(": ")
                                     .append(def.getString("definition"));
                        }
                        
                        if (def.containsKey("example")) {
                            if (lijuBuilder.length() > 0) {
                                lijuBuilder.append("\n");
                            }
                            lijuBuilder.append(def.getString("example"));
                        }
                    }
                }
            }
        }

        wordDetail.put("shiyi", shiyiBuilder.toString());
        wordDetail.put("liju", lijuBuilder.toString());

        return wordDetail;
    }

    /**
     * 从免费API获取单词数据并保存到数据库（保留原有功能）
     */
    @Transactional
    public int fetchAndSaveWords(String jibie, String wordList) throws Exception {
        if (wordList == null || wordList.trim().isEmpty()) {
            throw new IllegalArgumentException("单词列表不能为空");
        }

        String[] words = wordList.split(",");
        int successCount = 0;

        for (String word : words) {
            word = word.trim();
            if (word.isEmpty()) continue;

            try {
                QueryWrapper<DancikuEntity> queryWrapper = new QueryWrapper<>();
                queryWrapper.eq("danci", word);
                DancikuEntity existWord = dancikuService.getOne(queryWrapper);

                if (existWord != null) {
                    continue;
                }

                DancikuEntity wordEntity = fetchWordFromApi(word, jibie);
                
                if (wordEntity != null) {
                    dancikuService.save(wordEntity);
                    successCount++;
                }
            } catch (Exception e) {
                System.err.println("获取单词失败：" + word + "，错误：" + e.getMessage());
            }
        }

        return successCount;
    }

    /**
     * 从第三方API获取单词详细信息（用于保存到数据库）
     */
    private DancikuEntity fetchWordFromApi(String word, String jibie) throws Exception {
        String apiUrl = "https://api.dictionaryapi.dev/api/v2/entries/en/" + word;
        
        String response = HttpUtils.doGet(apiUrl);
        
        if (response == null || response.isEmpty()) {
            return null;
        }

        JSONArray wordsArray = JSONArray.parseArray(response);
        if (wordsArray == null || wordsArray.isEmpty()) {
            return null;
        }

        JSONObject wordObj = wordsArray.getJSONObject(0);
        
        DancikuEntity entity = new DancikuEntity();
        entity.setDanci(word);
        entity.setJibie(jibie);
        entity.setAddtime(new Date());

        if (wordObj.containsKey("phonetics")) {
            JSONArray phonetics = wordObj.getJSONArray("phonetics");
            if (phonetics != null && !phonetics.isEmpty()) {
                JSONObject phonetic = phonetics.getJSONObject(0);
                if (phonetic.containsKey("text")) {
                    entity.setDuyin(phonetic.getString("text"));
                }
            }
        }

        StringBuilder shiyiBuilder = new StringBuilder();
        StringBuilder lijuBuilder = new StringBuilder();

        if (wordObj.containsKey("meanings")) {
            JSONArray meanings = wordObj.getJSONArray("meanings");
            for (int i = 0; i < meanings.size() && i < 3; i++) {
                JSONObject meaning = meanings.getJSONObject(i);
                String partOfSpeech = meaning.getString("partOfSpeech");
                
                if (meaning.containsKey("definitions")) {
                    JSONArray definitions = meaning.getJSONArray("definitions");
                    for (int j = 0; j < definitions.size() && j < 2; j++) {
                        JSONObject def = definitions.getJSONObject(j);
                        
                        if (def.containsKey("definition")) {
                            if (shiyiBuilder.length() > 0) {
                                shiyiBuilder.append("; ");
                            }
                            shiyiBuilder.append(partOfSpeech).append(": ")
                                     .append(def.getString("definition"));
                        }
                        
                        if (def.containsKey("example")) {
                            if (lijuBuilder.length() > 0) {
                                lijuBuilder.append("\n");
                            }
                            lijuBuilder.append(def.getString("example"));
                        }
                    }
                }
            }
        }

        entity.setShiyi(shiyiBuilder.toString());
        entity.setLiju(lijuBuilder.toString());

        return entity;
    }
}