package com.yb.utils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.springframework.core.io.ClassPathResource;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 单词库工具类 - 从本地 JSON 文件读取单词列表
 */
public class WordBookUtil {

    private static final Map<String, List<String>> WORD_BOOKS = new HashMap<>();

    static {
        try {
            loadWordBooks();
        } catch (Exception e) {
            System.err.println("加载单词库失败：" + e.getMessage());
        }
    }

    /**
     * 加载所有单词书
     */
    private static void loadWordBooks() throws Exception {
        String[] bookFiles = {
            "wordbooks/cet4.json",
            "wordbooks/cet6.json",
            "wordbooks/kaoyan.json",
            "wordbooks/primary.json",
            "wordbooks/middle.json",
            "wordbooks/high.json"
        };

        for (String file : bookFiles) {
            try {
                ClassPathResource resource = new ClassPathResource(file);
                BufferedReader reader = new BufferedReader(
                    new InputStreamReader(resource.getInputStream(), StandardCharsets.UTF_8)
                );

                StringBuilder jsonStr = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    jsonStr.append(line);
                }
                reader.close();

                JSONArray wordArray = JSONArray.parseArray(jsonStr.toString());
                List<String> wordList = new ArrayList<>();

                for (int i = 0; i < wordArray.size(); i++) {
                    JSONObject wordObj = wordArray.getJSONObject(i);
                    if (wordObj.containsKey("word")) {
                        wordList.add(wordObj.getString("word"));
                    }
                }

                String bookName = file.substring(file.lastIndexOf("/") + 1).replace(".json", "");
                WORD_BOOKS.put(bookName, wordList);

                System.out.println("成功加载单词书：" + bookName + "，共 " + wordList.size() + " 个单词");

            } catch (Exception e) {
                System.err.println("加载单词书失败：" + file + "，" + e.getMessage());
            }
        }
    }

    /**
     * 根据级别获取单词列表
     */
    public static List<String> getWordList(String jibie) {
        return WORD_BOOKS.getOrDefault(jibie, new ArrayList<>());
    }

    /**
     * 获取所有单词书名称
     */
    public static List<String> getAllBookNames() {
        return new ArrayList<>(WORD_BOOKS.keySet());
    }

    /**
     * 获取某本书的单词总数
     */
    public static int getWordCount(String jibie) {
        List<String> words = WORD_BOOKS.get(jibie);
        return words == null ? 0 : words.size();
    }
}