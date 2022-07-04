package com.cjh.apollo;

import com.ctrip.framework.apollo.Config;
import com.ctrip.framework.apollo.ConfigService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
class ApolloApplicationTests {
    /**
     * 读取默认namespace（application）下的配置信息
     * 启动配置： -Dapp.id=apollo -Denv=DEV -Ddev_meta=http://localhost:8080
     */
    @Test
    public void Test1() {
        Config config = ConfigService.getAppConfig();
        String someKey = "sms.enable";
        String value = config.getProperty(someKey, null);
        System.out.println("sms.enable: " + value);
    }

    /**
     * 读取指定namespace下的配置信息
     * 启动配置： -Dapp.id=account-service -Denv=DEV -Ddev_meta=http://localhost:8080
     */
    @Test
    public void Test2() {
        Config config = ConfigService.getConfig("rocketmq");
        String someKey = "rocketmq.name-server";
        String value = config.getProperty(someKey, null);
        System.out.println("rocketmq.name-server= " + value);
    }

    /**
     * 读取公共namespace下的配置信息
     * 启动配置： -Dapp.id=account-service -Denv=DEV -Ddev_meta=http://localhost:8080
     */
    @Test
    public void Test3() {
        Config config = ConfigService.getConfig("micro_service.springboot-http");
        String someKey = "server.servlet.context-path";
        String value = config.getProperty(someKey, null);
        System.out.println("server.servlet.context-path= " + value);
    }

}
