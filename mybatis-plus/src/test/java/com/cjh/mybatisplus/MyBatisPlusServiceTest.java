package com.cjh.mybatisplus;

import com.cjh.mybatisplus.entity.User;
import com.cjh.mybatisplus.service.UserService;
import com.google.common.collect.Lists;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * IService测试类
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class MyBatisPlusServiceTest {
    @Resource
    private UserService userService;

    @Test
    public void count() {
        int count = userService.count();
        System.out.println(count);
    }

    /**
     * 批量添加
     * 单个添加循环执行，效率低
     */
    @Test
    public void saveBatch() {
        List<User> userList = Lists.newArrayList(new User(null,"张三",3,"email3",1,new Date()),
                new User(null,"李四",4,"email4",1,new Date()));
        boolean b = userService.saveBatch(userList);
        System.out.println(b);
    }
}
