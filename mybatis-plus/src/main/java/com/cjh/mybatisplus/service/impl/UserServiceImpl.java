package com.cjh.mybatisplus.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cjh.mybatisplus.entity.User;
import com.cjh.mybatisplus.mapper.UserMapper;
import com.cjh.mybatisplus.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

//这里继承了IService的实现类，相当于本类中拥有了所有实现方法
@Transactional
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    @Transactional
    public void A() {
        B();

    }

    @Transactional
    public void B() {
        /*User user = new User();
        user.setUserName("测试");
        save(user);*/
        new Thread(new Runnable() {
            @Override
            public void run() {
                //Thread.sleep(3000);
                User user = new User();
                user.setUserName("测试");
                save(user);
                //int a = 1/0;

            }
        }).start();
    }
}
