package com.cjh.nacosConsumer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

@RestController
@RequestMapping("consumer")
public class ConsumeController {

    @Autowired
    private LoadBalancerClient loadBalancerClient;
    @Autowired
    private RestTemplate restTemplate;

    @GetMapping("hello/{message}")
    public String hello(@PathVariable String message) {
        //通过服务提供者的名称从注册中心获取服务实例
        ServiceInstance serviceInstance = loadBalancerClient.choose("nacos-provider");
        String path = String.format("http://%s:%s/provider/%s", serviceInstance.getHost(), serviceInstance.getPort(), message);
        String result = restTemplate.getForObject(path, String.class);
        return String.format("%s from %s %s", result, serviceInstance.getHost(), serviceInstance.getPort());
    }
}
