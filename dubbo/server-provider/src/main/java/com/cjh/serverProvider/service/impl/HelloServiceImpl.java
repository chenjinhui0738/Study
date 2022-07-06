package com.cjh.serverProvider.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.dubbo.rpc.cluster.loadbalance.RoundRobinLoadBalance;
import com.cjh.commonApi.service.HelloService;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

//@Service注解为Dubbo提供的com.alibaba.dubbo.config.annotation.Service，而非Spring的那个
//提供者指定负载均衡策略和权重
@Service(interfaceClass = HelloService.class,loadbalance = RoundRobinLoadBalance.NAME, weight = 100)
@Component
public class HelloServiceImpl implements HelloService{
    /**
     * 服务超时演示，可以在admin上通过屏蔽或者容错消费者
     * 屏蔽：不调用服务方直接返回null
     * 容错：远程调用失败时，返回null,特别是在网络故障时
     * @param message
     * @return
     */
    /*@Override
    public String hello(String message) {
        try {
            System.out.println("调用 com.cjh.serverProvider.service.impl.HelloServiceImpl#hello");
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "hello," + message;
    }*/

    /**
     * 服务降级演示
     * 降级方法的方法参数和返回类型必须和原方法保持一致
     * @param message
     * @return
     */
    @HystrixCommand(fallbackMethod = "defaultError")
    @Override
    public String hello(String message) {
        System.out.println("调用 com.cjh.serverProvider.service.impl.HelloServiceImpl#hello");
        String a = null;
        a.toString();
        return "hello," + message;
    }

    public String defaultError(String message) {
        return "处理失败";
    }
}
