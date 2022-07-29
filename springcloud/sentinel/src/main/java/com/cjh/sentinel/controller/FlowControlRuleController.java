package com.cjh.sentinel.controller;

import com.cjh.sentinel.service.SentinelDashBoardService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Type;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * 流控规则
 *
 */
@RestController
@RequestMapping(value = "/sentinel")
public class FlowControlRuleController {
    private Logger log = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private SentinelDashBoardService sentinelDashBoardService;
    /************************************************QPS直接流控模式************************************************************/
    /**
     * 到达qps阈值后进行限流，停止访问
     * @return
     */
    @GetMapping("qpsTest")
    public String qpsTest(){
        return "qpsTest";
    }
    /************************************************线程直接流控模式************************************************************/
    /**
     * 到达线程阈值后进行限流，停止访问
     * @return
     * @throws InterruptedException
     */
    @GetMapping("threadTest")
    public String threadTest() throws InterruptedException {
        TimeUnit.SECONDS.sleep(1);
        return "threadTest";
    }

    /************************************************关联流控模式************************************************************/
    /**
     *
     * @return
     */
    @GetMapping("relatedTest1")
    public String relatedTest1(){
        return "relatedTest1";
    }

    /**
     * 当访问/relatedTest2到达阈值时限流relatedTest1
     * @return
     */
    @GetMapping("relatedTest2")
    public String relatedTest2(){
        return "relatedTest2";
    }
    /************************************************链路流控模式************************************************************/
    /**
     *通过/linkTest1访问hello达到阈值的时候，进行限流，linkTest2不影响
     * @return
     */
    @GetMapping("linkTest1")
    public String linkTest1(@RequestParam(required = false)Integer type){
        return "linkTest1"+sentinelDashBoardService.hello(type);
    }

    @GetMapping("linkTest2")
    public String linkTest2(@RequestParam(required = false)Integer type){
        return "linkTest2"+sentinelDashBoardService.hello(type);
    }


    /**
     * 预热
     * sentinel客户端的默认冷加载因子coldFactor为3，即请求QPS从 threshold / 3 开始，经预热时长逐渐升至设定的QPS阈值
     * 一开始的阈值为阈值/3,经过预热时长后变为正常阈值
     * @return
     */
    @GetMapping("warmUpTest")
    public String warmUpTest(){
        return "warmUpTest";
    }
    /**
     * 排队等待
     * 请求只能到达阈值，超过的请求排队等待，超过设置的等待时间则超时
     * @return
     */
    @GetMapping("waitTest")
    public String waitTest(){
        log.info("waitTest");
        return "waitTest";
    }





}
