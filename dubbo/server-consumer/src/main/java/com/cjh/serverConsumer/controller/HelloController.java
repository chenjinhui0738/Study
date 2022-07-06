package com.cjh.serverConsumer.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.rpc.cluster.loadbalance.RoundRobinLoadBalance;
import com.cjh.commonApi.service.HelloService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    //@Reference(url = "http://127.0.0.1:8080")//通过直连方式不走注册中心
    //指定负载均衡策略
    //1.RandomLoadBalance是基于权重的负载均衡机制
    //2.LeastActiveLoadBalance:最小活跃数负载均衡
    //3.ConsistentHashLoadBalance:一致性hash负载均衡机制,相同参数的请求总是发到同一提供者
    //4.RoundRobinLoadBalance:基于权重的轮询负载均衡机制
    @Reference(loadbalance = RoundRobinLoadBalance.NAME,timeout = 1000)//设置调用超时
    private HelloService helloService;

    @GetMapping("/hello/{message}")
    public String hello(@PathVariable String message) {
        return this.helloService.hello(message);
    }
}
