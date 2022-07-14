package com.cjh.spring.bean;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 自定义属性，从properties文件获取值
 */
@ConfigurationProperties(prefix = "cjh.properties")//设置对应属性文件中的前缀
public class MyProperties {
    private String name;
    private String title;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
