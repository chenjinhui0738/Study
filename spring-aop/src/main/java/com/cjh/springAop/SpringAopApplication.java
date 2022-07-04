package com.cjh.springAop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

//没有用到数据库连接，但是springboot会自动帮我们注入数据源，但是我们又没有配置数据源和驱动就会报错：启动注解中加：exclude={DataSourceAutoConfiguration.class}
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class SpringAopApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringAopApplication.class, args);
    }

}
