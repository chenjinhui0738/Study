package com.cjh.mybatisplus.mapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cjh.mybatisplus.entity.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository
public interface UserMapper extends BaseMapper<User> {
    //自定义查询方法
   Map<String,Object> selectMapById(Long id);
    /*** 根据年龄查询用户列表，分页显示
     ** @param page 分页对象,xml中可以从里面进行取值,传递参数 Page 即自动分页,必须放在第一位
     ** @param age 年龄
     ** @return
     **/
   Page<User> selectWithPage(@Param("page") Page<User> page,@Param("age") Integer age);
}
