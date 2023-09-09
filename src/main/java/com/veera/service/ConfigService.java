package com.veera.service;

import com.veera.util.AppCacheUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class ConfigService {

    @Autowired
    private RedisTemplate<String, String> redisTemplateStandard;

    @Autowired
    private RedisTemplate<String, Object> redisTemplateJson;

    public boolean addConfig(String key, String value) {
        boolean isConfigAdded = false;
        try {
            redisTemplateStandard.opsForValue().set(key, value);
            AppCacheUtil.addToInMemory(key, value);
            isConfigAdded = true;
        } catch (Exception e) {
            isConfigAdded = false;
        }
        return isConfigAdded;
    }

    public String getValueFromRedis(String key) {
        String value = "";
        String inMemoryValue = AppCacheUtil.checkIfExistInMemory(key);
        if (StringUtils.isNotBlank(inMemoryValue)) {
            value = inMemoryValue;
        } else {
            value = redisTemplateStandard.opsForValue().get(key);
            AppCacheUtil.addToInMemory(key, value);
        }
        return value;
    }

    public boolean addJsonConfig(String key, Map<String, String> value) {
        boolean isJsonAdded = false;
        try {
            redisTemplateJson.opsForValue().set(key, value);
            isJsonAdded = true;
        } catch (Exception e) {
            isJsonAdded = false;
        }
        return isJsonAdded;
    }
}
