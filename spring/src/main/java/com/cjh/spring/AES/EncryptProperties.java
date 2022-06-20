package com.cjh.spring.AES;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "spring.encrypt")
public class EncryptProperties {
    private final static String DEFAULT_KEY = "default";
    private String key = DEFAULT_KEY;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
