package com.cjh.sentinel.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 热点规则
 */
@RestController
@RequestMapping(value = "/sentinel")
public class HotRuleController {
    /**
     * 可以设置参数索引下标，当对应的参数为对应的值时，触发对应的参数阈值限制，否则为普通的单机阈值限制
     * @param name
     * @param age
     * @return
     */
    @GetMapping("hot")
    @SentinelResource(value = "hot")
    public String hot(String name, Integer age) {
        return name+age+"岁";
    }
}
