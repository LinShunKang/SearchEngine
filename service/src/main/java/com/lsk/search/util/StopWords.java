package com.lsk.search.util;

import com.lsk.log.LoggerManager;

import java.io.*;
import java.net.URL;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by LinShunkang on 6/19/16.
 */
public class StopWords {

    public static Set<String> getStopWordSet() {
        String encoding = "UTF-8";
        File file = new File(getStopWordFilePath());
        if (!file.isFile() && !file.exists()) {
            return null;
        }

        InputStreamReader read = null;
        BufferedReader bufferedReader = null;
        try {
            read = new InputStreamReader(new FileInputStream(file), encoding);
            bufferedReader = new BufferedReader(read);
            Set<String> result = new HashSet<String>(1894);
            String lineTxt;
            while ((lineTxt = bufferedReader.readLine()) != null) {
               result.add(lineTxt);
            }
            return result;
        } catch (Exception e) {
            LoggerManager.error("StopWords.getStopWordSet()", e);
        } finally {
            closeQuietly(bufferedReader);
        }
        return null;
    }

    private static void closeQuietly(Reader reader) {
        try {
            if (reader != null) {
                reader.close();
            }
        } catch (Exception e) {
        }
    }

    private static String getStopWordFilePath() {
        URL url = StopWords.class.getClassLoader().getResource("stopWords.txt");
        if (url != null) {
            return url.getPath();
        } else {
            return "";
        }
    }

    public static void main(String[] args) {
        Set<String> set = getStopWordSet();
        System.out.println(set);
        LoggerManager.info("aaaa", StopWords.class);
        LoggerManager.info("{}-{}-{}", 1, 2, 3);
        LoggerManager.warn("{}-{}-{}", 1, 2, 3);
        LoggerManager.error("{}-{}-{}", 1, 2, 3);

    }
}
