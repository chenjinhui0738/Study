package com.cjh.ribbon.text;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {
    @Autowired
    HelloSevice helloService;

    @HystrixCommand(fallbackMethod = "helloError")
    @RequestMapping(value = "/hello")
    //服务消费方
    public String hello(@RequestParam String name) {
        return helloService.hiService(name);
    }

    public String helloError(String name) {
        return "hello," + name + ",sorry,error!";
    }
}
