package com.cjh.mybatisplus.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cjh.mybatisplus.dao.UserMapper;
import com.cjh.mybatisplus.entity.User;
import com.cjh.mybatisplus.service.UserService;
import org.springframework.stereotype.Service;

//这里继承了IService的实现类，相当于本类中拥有了所有实现方法
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
}
