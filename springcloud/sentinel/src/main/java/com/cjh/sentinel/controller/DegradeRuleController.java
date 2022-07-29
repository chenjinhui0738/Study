package com.cjh.sentinel.controller;

import com.alibaba.csp.sentinel.slots.block.degrade.DegradeRule;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

/**
 * 熔断规则
 */
@RestController
@RequestMapping(value = "/sentinel")
public class DegradeRuleController {

    /**
     * 1.慢调用比例
     * 最大RT:需要设置的阈值，超过该阈值则为慢调用
     * 比例阈值:慢调用占所有调用的比例，范围[0~1]
     * 熔断时长:在熔断时间内拒绝所有请求
     * 最小请求数:允许通过的最小请求数，在数量范围内不会发生熔断
     *
     * 执行逻辑:请求数大于最小请求数并且慢调用（指耗时大于阈值RT的请求称为慢调用）的比率大于比例阈值则发生熔断，熔断时长为用户自定义设置。
     */
    @GetMapping("slowCallRatio")
    public String slowCallRatio() throws InterruptedException {
        // 休眠1秒
        TimeUnit.SECONDS.sleep(1);
        return "slowCallRatio";
    }
    /**
     * 2.异常比例
     * 比例阈值:异常请求的比例阈值
     * 熔断时长:在熔断时间内拒绝所有请求
     * 最小请求数:允许通过的最小请求数，在数量范围内不会发生熔断
     *
     * 执行逻辑:当请求数大于最小请求并且异常比例大于设置的阈值时触发熔断，熔断时长由用户设置。
     */
    @GetMapping("exceptionRatio")
    public String exceptionRatio(){
        // 每次请求都抛出异常
        throw new RuntimeException("服务异常");
    }
    /**
     * 3.异常数
     * 异常数:异常请求的数量
     * 熔断时长:在熔断时间内拒绝所有请求
     * 最小请求数:允许通过的最小请求数，在数量范围内不会发生熔断
     *
     * 执行逻辑:当请求数大于最小请求并且异常数量大于设置的阈值时触发熔断，熔断时长由用户设置。
     */
    @GetMapping("exceptionNum")
    public String exceptionNum() throws InterruptedException {
        // 每次请求都抛出异常
        throw new RuntimeException("服务异常");
    }

}
