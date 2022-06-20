package com.cjh.canal.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;


@Component
@Data
@ConfigurationProperties(prefix = "canal")
public class CanalProperties {
    private String host;
    private Integer port;
    private String destination;
    private String username;
    private String password;
}

