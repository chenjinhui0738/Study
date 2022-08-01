package com.cjh.nacosConfig.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 1.namespace，即命名空间。默认的命名空间为public，我们可以在Nacos控制台中新建命名空间；
 * 2.dataId，即配置文件名称，dataId的拼接格式如下：
 * ${prefix} - ${spring.profiles.active} . ${file-extension}
 * prefix默认为pring.application.name的值，也可以通过配置项spring.cloud.nacos.config.prefix来配置；
 * spring.profiles.active即为当前环境对应的profile。注意，当spring.profiles.active为空时，对应的连接符-也将不存在，dataId的拼接格式变成${prefix}.${file-extension}；
 * file-extension为配置内容的数据格式，可以通过配置项spring.cloud.nacos.config.file-extension来配置。
 * 3.group，即配置分组，默认为DEFAULT_GROUP，可以通过spring.cloud.nacos.config.group配置。
 * 所以根据这些规则，上面示例中我们的应用名称spring.application.name为my-project，spring.cloud.nacos.config.file-extension的值为yaml，没有指定spring.profiles.active，于是dataId为my-project.yaml，分组为默认的DEFAULT_GROUP，命名空间为默认的public。这就是我们在Nacos控制台中新建配置时的根据。
 */
@RestController
@RefreshScope//用于刷新配置,即我们在Nacos控制台修改了相关配置点击发布后，我们的应用能够在不重启的情况下获取到最新的配置
public class NacosConfigController {
    @Value("${message:null}")
    private String message;
    @Value("${messageA:null}")
    private String messageA;
    @Value("${messageB:null}")
    private String messageB;

    @GetMapping("message")
    public String getMessage() {
        return message;
    }

    /**
     * 测试获取多配置文件
     * @return
     */
    @GetMapping("multi")
    public String multiConfig() {
        return String.format("messageA: %s messageB: %s", messageA, messageB);
    }}
