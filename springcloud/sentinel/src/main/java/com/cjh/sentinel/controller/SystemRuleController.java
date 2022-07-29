package com.cjh.sentinel.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 系统规则
 * 1.Load 自适应（仅对 Linux/Unix-like 机器生效）：系统的 load1 作为启发指标，进行自适应系统保护。当系统 load1 超过设定的启发值，且系统当前的并发线程数超过估算的系统容量时才会触发系统保护（BBR 阶段）。系统容量由系统的 maxQps minRt 估算得出。设定参考值一般是 CPU cores 2.5。
 * 2.CPU usage（1.5.0+ 版本）：当系统 CPU 使用率超过阈值即触发系统保护（取值范围 0.0-1.0），比较灵敏。
 * 3.平均 RT：当单台机器上所有入口流量的平均 RT 达到阈值即触发系统保护，单位是毫秒。
 * 4.并发线程数：当单台机器上所有入口流量的并发线程数达到阈值即触发系统保护。
 * 5.入口 QPS：当单台机器上所有入口流量的 QPS 达到阈值即触发系统保护。
 */
@RestController
@RequestMapping(value = "/sentinel")
public class SystemRuleController {
}
