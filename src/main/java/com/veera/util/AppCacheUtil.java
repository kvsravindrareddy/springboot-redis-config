package com.veera.util;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class AppCacheUtil {
    private static Map<String, String> inMemoryCache = new ConcurrentHashMap<>();


    public static void addToInMemory(String key, String value) {
        inMemoryCache.put(key, value);
    }

    public static String checkIfExistInMemory(String key) {
        return inMemoryCache.get(key);
    }
}
