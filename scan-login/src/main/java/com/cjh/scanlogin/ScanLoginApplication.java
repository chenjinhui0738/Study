package com.cjh.scanlogin;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.cjh.scanlogin.mapper")
public class ScanLoginApplication {

    public static void main(String[] args) {
        SpringApplication.run(ScanLoginApplication.class, args);
    }

}
