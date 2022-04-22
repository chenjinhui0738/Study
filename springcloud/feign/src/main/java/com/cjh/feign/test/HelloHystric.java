package com.cjh.feign.test;

import org.springframework.stereotype.Component;

@Component
public class HelloHystric implements FeignService {
    @Override
    public String sayHiFromClientOne(String name) {
        return "sorry " + name;
    }
}
