package com.cjh.uidGenerator.controller;

import com.cjh.uidGenerator.service.IWorkerNodeService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/test")
public class WorkerNodeServiceController {
    @Resource
    private IWorkerNodeService workerNodeService;
    /**
     * 集成百度uid-generator生成id
     * @return
     */
    @GetMapping("/baidu/uid")
    public long baiduUid(){
        long uid = workerNodeService.genUid();
        return uid;
    }
}
