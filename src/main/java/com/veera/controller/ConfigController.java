package com.veera.controller;

import com.veera.service.ConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping(value = "/config")
public class ConfigController {

    @Autowired
    private ConfigService configService;

    @PostMapping(value="/add")
    public String addConfig(@RequestParam("key")String key, @RequestParam("value")String value) {
        String configStatus = "Success";
        if(!configService.addConfig(key, value)) {
            configStatus = "Failure";
        }
        return configStatus;
    }

    @PostMapping(value="/addjsonvalue")
    public String addConfig(@RequestParam("key")String key, @RequestBody Map<String, String> value) {
        String configStatus = "Success";
        if(!configService.addJsonConfig(key, value)) {
            configStatus = "Failure";
        }
        return configStatus;
    }

    @GetMapping(value = "/get")
    public String getValue(@RequestParam("key")String key) {
        return configService.getValueFromRedis(key);
    }
}
