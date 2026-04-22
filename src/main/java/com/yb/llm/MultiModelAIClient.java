package com.yb.llm;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.yb.utils.FileUtil;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * 多模型 AI 流式客户端工具类
 * 支持文本 + 多文件上传，支持流式/非流式输出
 */
@Slf4j
public final class MultiModelAIClient {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
    private static final ExecutorService EXECUTOR_SERVICE = Executors.newCachedThreadPool();
    private static final int DEFAULT_TIMEOUT = 60000;
    private static final long SSE_TIMEOUT = 180000L;

    private MultiModelAIClient() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }

    // ==================== 内部请求类 ====================
    @Data
    public static class ChatRequest {
        private String model;
        private String message;
        private String fileNames;
        private Boolean stream = false;
        private Double temperature = 0.7;
        private Integer maxTokens = 2000;
        private Map<String, Object> extraParams;
        private transient List<File> files;

        public List<File> getFiles() {
            if (files == null && fileNames != null && !fileNames.trim().isEmpty()) {
                files = new ArrayList<>();
                String[] nameArray = fileNames.split(",");
                for (String name : nameArray) {
                    String trimmedName = name.trim();
                    if (!trimmedName.isEmpty()) {
                        try {
                            File file = FileUtil.getFile(trimmedName);
                            if (file != null && file.exists()) {
                                files.add(file);
                            } else {
                                log.warn("文件不存在：{}", trimmedName);
                            }
                        } catch (Exception e) {
                            log.error("获取文件失败：{}", trimmedName, e);
                        }
                    }
                }
            }
            return files;
        }

        public boolean hasFiles() {
            List<File> fileList = getFiles();
            return fileList != null && !fileList.isEmpty();
        }
    }

    @Data
    @AllArgsConstructor
    public static class ChatResponse {
        private String id;
        private String model;
        private String content;
        private Boolean finished;
        private Boolean streaming;
        private Long timestamp;
        private String finishReason;
    }

    // ==================== 内部配置类 ====================
    @Data
    @AllArgsConstructor
    private static class ModelConfig {
        private String name;
        private String url;
        private String apiKey;
        private String secretKey;
    }

    // ==================== 模型配置数据 ====================
    private static final Map<String, ModelConfig> MODEL_CONFIGS = new HashMap<>();

    static {
    }

    // ==================== 适配器接口 ====================
    private interface ModelAdapter {
        String buildUrl(ModelConfig config, boolean stream);

        ObjectNode buildRequestBody(ChatRequest request) throws IOException;

        String parseStreamResponse(JsonNode json);

        String parseResponse(JsonNode json);

        void setHeaders(HttpURLConnection conn, ModelConfig config) throws Exception;
    }

    /**
     * 基础适配器，提供通用的 OpenAI 格式解析和头部设置
     */
    private abstract static class BaseAdapter implements ModelAdapter {
        @Override
        public void setHeaders(HttpURLConnection conn, ModelConfig config) {
            conn.setRequestProperty("Authorization", "Bearer " + config.getApiKey());
            conn.setRequestProperty("Content-Type", "application/json");
        }

        @Override
        public String parseStreamResponse(JsonNode json) {
            JsonNode choices = json.path("choices");
            if (choices.isArray() && choices.size() > 0) {
                return choices.get(0).path("delta").path("content").asText("");
            }
            return "";
        }

        @Override
        public String parseResponse(JsonNode json) {
            String content = "";
            String finishReason = null;
            JsonNode choices = json.path("choices");
            if (choices.isArray() && choices.size() > 0) {
                content = choices.get(0).path("message").path("content").asText("");
                finishReason = choices.get(0).path("finish_reason").asText(null);
            }
            ChatResponse response = new ChatResponse(
                    json.path("id").asText(),
                    getModelName(),
                    content,
                    true,
                    false,
                    System.currentTimeMillis(),
                    finishReason
            );
            System.out.println(response);
            return content;
        }

        protected abstract String getModelName();
    }
    // ==================== 文件内容构建工具类 ====================
    private static class FileContentBuilder {
        private static final Map<String, String> MIME_TYPES = new HashMap<>();

        static {
            MIME_TYPES.put("jpg", "image/jpeg");
            MIME_TYPES.put("jpeg", "image/jpeg");
            MIME_TYPES.put("png", "image/png");
            MIME_TYPES.put("gif", "image/gif");
            MIME_TYPES.put("pdf", "application/pdf");
            MIME_TYPES.put("txt", "text/plain");
        }

        public static String buildContentWithFiles(ChatRequest request) throws IOException {
            StringBuilder content = new StringBuilder();

            if (request.getMessage() != null && !request.getMessage().isEmpty()) {
                content.append(request.getMessage());
            }

            List<File> files = request.getFiles();
            if (files != null && !files.isEmpty()) {
                for (File file : files) {
                    try {
                        String fileName = file.getName();
                        String contentType = guessContentType(fileName);
                        byte[] fileBytes = Files.readAllBytes(file.toPath());

                        log.info("处理文件：{}, 大小：{} bytes, 类型：{}", fileName, fileBytes.length, contentType);

                        if (contentType.startsWith("image/")) {
                            String base64 = Base64.getEncoder().encodeToString(fileBytes);
                            content.append("\n\n[图片：" + fileName + "]\n");
                            content.append("![image](data:").append(contentType)
                                    .append(";base64,").append(base64).append(")");
                        } else if (contentType.equals("application/pdf")) {
                            content.append("\n\n[PDF 文件：" + fileName)
                                    .append(", 大小：").append(formatFileSize(fileBytes.length)).append("]\n");
                        } else if (contentType.startsWith("text/")) {
                            String textContent = readTextFileWithEncodingDetection(file, fileBytes);
                            content.append("\n\n[文本文件：" + fileName + " 的内容]\n");
                            content.append(textContent);
                        } else {
                            content.append("\n\n[附件：" + fileName)
                                    .append(", 类型：").append(contentType)
                                    .append(", 大小：").append(formatFileSize(fileBytes.length)).append("]\n");
                        }
                    } catch (Exception e) {
                        log.error("处理文件失败：{}", file.getName(), e);
                        content.append("\n\n[文件处理失败：" + file.getName() + " - " + e.getMessage() + "]");
                    }
                }
            }

            String result = content.toString();
            log.info("构建的消息内容长度：{}", result.length());
            return result;
        }

        private static String readTextFileWithEncodingDetection(File file, byte[] fileBytes) throws IOException {
            // 检测 BOM
            if (fileBytes.length >= 3) {
                if ((fileBytes[0] & 0xFF) == 0xEF && (fileBytes[1] & 0xFF) == 0xBB && (fileBytes[2] & 0xFF) == 0xBF) {
                    return new String(fileBytes, 3, fileBytes.length - 3, StandardCharsets.UTF_8);
                }
                if ((fileBytes[0] & 0xFF) == 0xFE && (fileBytes[1] & 0xFF) == 0xFF) {
                    return new String(fileBytes, 2, fileBytes.length - 2, StandardCharsets.UTF_16BE);
                }
                if ((fileBytes[0] & 0xFF) == 0xFF && (fileBytes[1] & 0xFF) == 0xFE) {
                    return new String(fileBytes, 2, fileBytes.length - 2, StandardCharsets.UTF_16LE);
                }
            }

            // 候选编码检测
            String[] encodings = {"UTF-8", "GBK", "GB2312", "GB18030", "ISO-8859-1"};
            String bestEncoding = detectBestEncoding(fileBytes, encodings);
            try {
                return new String(fileBytes, Charset.forName(bestEncoding));
            } catch (Exception e) {
                log.warn("使用编码 {} 读取失败，回退到 UTF-8", bestEncoding, e);
                return new String(fileBytes, StandardCharsets.UTF_8);
            }
        }

        private static String detectBestEncoding(byte[] bytes, String[] encodings) {
            int bestScore = 0;
            String bestEncoding = "UTF-8";
            for (String encoding : encodings) {
                try {
                    String decoded = new String(bytes, Charset.forName(encoding));
                    int score = scoreTextQuality(decoded, bytes);
                    if (score > bestScore) {
                        bestScore = score;
                        bestEncoding = encoding;
                    }
                } catch (Exception ignored) {
                }
            }
            return bestEncoding;
        }

        private static int scoreTextQuality(String text, byte[] originalBytes) {
            int score = 0;
            // 乱码惩罚
            if (text.contains("ï¿½") || text.contains("Ã") || text.contains("Â")) {
                score -= 100;
            }
            // 中文字符加分
            int chineseCharCount = 0;
            int totalCharCount = Math.min(text.length(), 1000);
            for (int i = 0; i < totalCharCount; i++) {
                char c = text.charAt(i);
                if (c >= '\u4E00' && c <= '\u9FFF') {
                    chineseCharCount++;
                } else if (c >= '\u3000' && c <= '\u303F') {
                    chineseCharCount++;
                }
            }
            if (chineseCharCount > 10) {
                score += chineseCharCount * 2;
            }
            // 可打印字符比例加分
            int printableCount = 0;
            for (int i = 0; i < totalCharCount; i++) {
                char c = text.charAt(i);
                if (!Character.isISOControl(c)) {
                    printableCount++;
                }
            }
            score += (printableCount * 100) / Math.max(totalCharCount, 1);
            // 字节与字符比例合理性加分
            if (originalBytes.length > 0 && text.length() > 0) {
                double bytesPerChar = (double) originalBytes.length / text.length();
                if (bytesPerChar >= 1.5 && bytesPerChar <= 4.0) {
                    score += 50;
                }
            }
            return score;
        }

        private static String guessContentType(String fileName) {
            int lastDot = fileName.lastIndexOf(".");
            if (lastDot == -1) {
                return "application/octet-stream";
            }
            String ext = fileName.substring(lastDot + 1).toLowerCase();
            return MIME_TYPES.getOrDefault(ext, "application/octet-stream");
        }

        private static String formatFileSize(long size) {
            if (size < 1024) {
                return size + " B";
            } else if (size < 1024 * 1024) {
                return String.format("%.2f KB", size / 1024.0);
            } else {
                return String.format("%.2f MB", size / (1024.0 * 1024.0));
            }
        }
    }

    // ==================== 适配器映射 ====================
    private static final Map<String, ModelAdapter> ADAPTERS = new HashMap<>();

    static {
    }

    // ==================== 公共静态 API 方法 ====================
    public static String chat(ChatRequest request) throws Exception {
        request.setStream(false);
        return executeRequest(request);
    }

    public static SseEmitter streamChat(ChatRequest request) {
        request.setStream(true);
        SseEmitter emitter = new SseEmitter(SSE_TIMEOUT);

        EXECUTOR_SERVICE.submit(() -> {
            try {
                processStreamRequest(request, emitter);
            } catch (Exception e) {
                log.error("流式处理错误", e);
                completeEmitterWithError(emitter, e);
            }
        });

        return emitter;
    }

    // ==================== 私有辅助方法 ====================
    private static String executeRequest(ChatRequest request) throws Exception {
        ModelConfig config = validateAndGetConfig(request);
        ModelAdapter adapter = ADAPTERS.get(request.getModel());

        String responseBody = executeHttpRequest(adapter, config, request, false);
        JsonNode jsonNode = OBJECT_MAPPER.readTree(responseBody);
        return adapter.parseResponse(jsonNode);
    }

    private static void processStreamRequest(ChatRequest request, SseEmitter emitter) throws Exception {
        ModelConfig config = validateAndGetConfig(request);
        ModelAdapter adapter = ADAPTERS.get(request.getModel());

        HttpURLConnection conn = null;
        try {
            conn = createConnection(adapter.buildUrl(config, true), DEFAULT_TIMEOUT);
            adapter.setHeaders(conn, config);

            String requestBody = OBJECT_MAPPER.writeValueAsString(adapter.buildRequestBody(request));
            log.info("发送给 {} 的请求体大小：{} bytes", request.getModel(), requestBody.length());

            sendRequest(conn, requestBody);

            int responseCode = conn.getResponseCode();
            if (responseCode != 200) {
                String errorBody = readErrorResponse(conn);
                throw new RuntimeException("HTTP 错误：" + responseCode + " - " + errorBody);
            }

            try (BufferedReader reader = new BufferedReader(
                    new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8))) {
                String line;
                boolean hasData = false;
                while ((line = reader.readLine()) != null) {
                    hasData = true;
                    if (!processStreamLine(line, adapter, emitter)) {
                        break;
                    }
                }
                if (!hasData) {
                    emitter.send(SseEmitter.event().data("没有收到响应数据").name("error"));
                }
            }

            emitter.send(SseEmitter.event().data("[DONE]").name("end"));
            emitter.complete();

        } catch (Exception e) {
            log.error("流式处理错误", e);
            completeEmitterWithError(emitter, e);
        } finally {
            if (conn != null) {
                conn.disconnect();
            }
        }
    }

    private static boolean processStreamLine(String line, ModelAdapter adapter, SseEmitter emitter) throws IOException {
        if (!line.startsWith("data: ")) {
            return true;
        }
        String data = line.substring(6);
        if ("[DONE]".equals(data)) {
            return false;
        }

        // 兼容千帆流式响应的多JSON块格式
        if (data.contains("}{")) {
            String[] jsonParts = data.split("(?<=})");
            for (String part : jsonParts) {
                if (part.trim().isEmpty()) continue;
                JsonNode jsonNode = OBJECT_MAPPER.readTree(part);
                String content = adapter.parseStreamResponse(jsonNode);
                if (content != null && !content.isEmpty()) {
                    emitter.send(SseEmitter.event().data(content).name("message"));
                }
            }
        } else {
            JsonNode jsonNode = OBJECT_MAPPER.readTree(data);
            String content = adapter.parseStreamResponse(jsonNode);
            if (content != null && !content.isEmpty()) {
                emitter.send(SseEmitter.event().data(content).name("message"));
            }
        }
        return true;
    }

    private static ModelConfig validateAndGetConfig(ChatRequest request) {
        ModelConfig config = MODEL_CONFIGS.get(request.getModel());
        if (config == null) {
            throw new IllegalArgumentException("不支持的模型：" + request.getModel() +
                    "，支持的模型：" + String.join(", ", MODEL_CONFIGS.keySet()));
        }
        return config;
    }

    private static void completeEmitterWithError(SseEmitter emitter, Exception e) {
        try {
            emitter.send(SseEmitter.event().data("错误：" + e.getMessage()).name("error"));
        } catch (IOException ex) {
            log.error("发送错误信息失败", ex);
        }
        emitter.complete();
    }

    /**
     * 统一执行 HTTP 请求，返回响应体字符串（非流式）
     */
    private static String executeHttpRequest(ModelAdapter adapter, ModelConfig config,
                                             ChatRequest request, boolean stream) throws Exception {
        HttpURLConnection conn = null;
        try {
            conn = createConnection(adapter.buildUrl(config, stream), DEFAULT_TIMEOUT);
            adapter.setHeaders(conn, config);

            String requestBody = OBJECT_MAPPER.writeValueAsString(adapter.buildRequestBody(request));
            sendRequest(conn, requestBody);

            int responseCode = conn.getResponseCode();
            if (responseCode != 200) {
                String errorBody = readErrorResponse(conn);
                throw new RuntimeException("HTTP 错误：" + responseCode + " - " + errorBody);
            }

            try (BufferedReader reader = new BufferedReader(
                    new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8))) {
                StringBuilder response = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
                return response.toString();
            }
        } finally {
            if (conn != null) {
                conn.disconnect();
            }
        }
    }

    private static String readErrorResponse(HttpURLConnection conn) throws IOException {
        InputStream errorStream = conn.getErrorStream();
        if (errorStream != null) {
            try (BufferedInputStream bis = new BufferedInputStream(errorStream)) {
                byte[] buffer = new byte[4096];
                int bytesRead;
                StringBuilder sb = new StringBuilder();
                while ((bytesRead = bis.read(buffer)) != -1) {
                    sb.append(new String(buffer, 0, bytesRead, StandardCharsets.UTF_8));
                }
                return sb.toString();
            }
        }
        return conn.getResponseMessage();
    }

    private static HttpURLConnection createConnection(String urlString, int timeout) throws IOException {
        URL url = new URL(urlString);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
        conn.setDoOutput(true);
        conn.setConnectTimeout(timeout);
        conn.setReadTimeout(0);
        return conn;
    }

    private static void sendRequest(HttpURLConnection conn, String body) throws IOException {
        try (OutputStream os = conn.getOutputStream()) {
            byte[] input = body.getBytes(StandardCharsets.UTF_8);
            os.write(input, 0, input.length);
            os.flush();
        }
    }

    public static Set<String> getSupportedModels() {
        return Collections.unmodifiableSet(MODEL_CONFIGS.keySet());
    }

    public static void shutdown() {
        EXECUTOR_SERVICE.shutdown();
        try {
            if (!EXECUTOR_SERVICE.awaitTermination(5, TimeUnit.SECONDS)) {
                EXECUTOR_SERVICE.shutdownNow();
            }
        } catch (InterruptedException e) {
            EXECUTOR_SERVICE.shutdownNow();
            Thread.currentThread().interrupt();
        }
    }

    public static void updateModelConfig(String modelName, String url, String apiKey, String secretKey) {
        MODEL_CONFIGS.put(modelName, new ModelConfig(modelName, url, apiKey, secretKey));
    }
}