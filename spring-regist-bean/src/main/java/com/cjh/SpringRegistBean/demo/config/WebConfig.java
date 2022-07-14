package com.cjh.SpringRegistBean.demo.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan("com.cjh.SpringRegistBean.demo")//设置bean的扫描路径
public class WebConfig {
    /*@Bean(name = "myUser")//设置bean的名称
    public User user() {
        return new User("测试", 18);
    }*/
}

