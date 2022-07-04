package com.cjh.mybatisplus;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cjh.mybatisplus.mapper.UserMapper;
import com.cjh.mybatisplus.entity.User;
import org.apache.commons.lang3.StringUtils;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * BaseMapper测试类
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class MybatisPlusBaseMapperTests {

    //@Resource
    @Autowired//编译会有警告，idea认为mapper无法注入因为是接口，要在mapper上加@Repository
    private UserMapper userMapper;

    /**
     * 查询所有
     */
    @Test
    public void selectAll() {
        List<User> userList = userMapper.selectList(null);
        Assert.assertEquals(4, userList.size());
        userList.forEach(System.out::println);
    }

    /**
     * 分页查询
     */
    @Test
    public void selectPage() {
        //BaseMapper分页
        Page<User> page1 = new Page<>(1, 3);
        Page<User> userPage1 = userMapper.selectPage(page1, (Wrapper<User>) null);
        List<User> userList1 = userPage1.getRecords();
        userList1.forEach(System.out::println);
        System.out.println("当前页：" + page1.getCurrent());
        System.out.println("每页显示的条数：" + page1.getSize());
        System.out.println("总记录数：" + page1.getTotal());
        System.out.println("总页数：" + page1.getPages());
        System.out.println("是否有上一页：" + page1.hasPrevious());
        System.out.println("是否有下一页：" + page1.hasNext());
        //xml自定义分页
        Page<User> page2 = new Page<>(1, 3);
        Page<User> userPage2 = userMapper.selectWithPage(page2, 15);
        List<User> userList2 = userPage2.getRecords();
        userList2.forEach(System.out::println);
        System.out.println("当前页：" + page2.getCurrent());
        System.out.println("每页显示的条数：" + page2.getSize());
        System.out.println("总记录数：" + page2.getTotal());
        System.out.println("总页数：" + page2.getPages());
        System.out.println("是否有上一页：" + page2.hasPrevious());
        System.out.println("是否有下一页：" + page2.hasNext());
    }

    /**
     * 条件查询,根据条件是否为null添加条件
     */
    @Test
    public void select1() {
        String userName = "J";
        String email = null;
        int ageBegin = 15;
        int ageEnd = 20;
        QueryWrapper<User> query = new QueryWrapper<>();
        //SELECT id,user_name,age,email,is_deleted,create_time FROM t_user WHERE is_deleted=1 AND (user_name LIKE ? AND age BETWEEN ? AND ?)
        query.like(StringUtils.isNotBlank(userName), "user_name", userName).like(StringUtils.isNotBlank(email), "email", email)
             .between(Objects.nonNull(ageBegin) && Objects.nonNull(ageEnd), "age", ageBegin, ageEnd);
        List<User> userList = userMapper.selectList(query);
        userList.forEach(System.out::println);
    }

    /**
     * or查询和and查询
     */
    @Test
    public void select2() {
        QueryWrapper<User> query = new QueryWrapper<>();
        //SELECT id,user_name,age,email,is_deleted,create_time FROM t_user WHERE is_deleted=1 AND (user_name LIKE '%J%' AND age BETWEEN 15 AND 20 OR email IS NOT NULL) ORDER BY id ASC
        query.like("user_name", "J").between("age", 15, 20).or().isNotNull("email")
             .orderByAsc("id");
        List<User> userList1 = userMapper.selectList(query);
        userList1.forEach(System.out::println);
        query.clear();
        //SELECT id,user_name,age,email,is_deleted,create_time FROM t_user WHERE is_deleted=1 AND (user_name LIKE '%J%' AND (age BETWEEN 15 AND 20 OR email IS NOT NULL))
        query.like("user_name", "J").and(query1 -> query1.between("age", 15, 20).or().isNotNull("email"));
        List<User> userList2 = userMapper.selectList(query);
        userList2.forEach(System.out::println);
    }

    /**
     * 子查询
     */
    @Test
    public void select3() {
        QueryWrapper<User> query = new QueryWrapper<>();
        query.inSql("id", "select id from t_user where id <=3");
        List<User> userList = userMapper.selectList(query);
        userList.forEach(System.out::println);
        query.clear();
    }

    /**
     * lambda条件查询
     */
    @Test
    public void select4() {
        String userName = "J";
        String email = null;
        int ageBegin = 15;
        int ageEnd = 20;
        LambdaQueryWrapper<User> query = new LambdaQueryWrapper<User>();
        //SELECT id,user_name,age,email,is_deleted,create_time FROM t_user WHERE is_deleted=1 AND (user_name LIKE ? AND age > ? AND age < ?)
        query.like(StringUtils.isNotBlank(userName), User::getUserName, userName).like(StringUtils.isNotBlank(email), User::getEmail, email)
             .gt(Objects.nonNull(ageBegin), User::getAge, ageBegin).lt(Objects.nonNull(ageEnd), User::getAge, ageEnd);
        List<User> userList = userMapper.selectList(query);
        userList.forEach(System.out::println);
    }

    /**
     * 条件更新
     */
    @Test
    public void update() {
        UpdateWrapper<User> updateWrapper1 = new UpdateWrapper<>();
        updateWrapper1.between("age", 15, 20).set("email", "update_email1");
        int updateNum1 = userMapper.update(null, updateWrapper1);
        System.out.println(updateNum1);
        LambdaUpdateWrapper<User> updateWrapper2 = new LambdaUpdateWrapper<>();
        User user = new User();
        user.setEmail("update_email2");
        updateWrapper2.between(User::getAge, 15, 20);
        int updateNum2 = userMapper.update(user, updateWrapper2);
        System.out.println(updateNum2);
    }

    /**
     * 插入
     */
    @Test
    public void insert() {
        User user = new User();
        user.setUserName("王五");
        user.setAge(5);
        user.setEmail("email5");
        int insertNum = userMapper.insert(user);
        System.out.println(insertNum);
    }

    /**
     * 根据id查询返回map
     */
    @Test
    public void selectMapById() {
        Map<String, Object> userMap = userMapper.selectMapById(1L);
        System.out.println(userMap);
    }

    /**
     * 根据id查询返回map
     */
    @Test
    public void selectMapByUserName() {
        Map<String, Object> userMap = userMapper.selectMapByUserName("Jo");
        System.out.println(userMap);
    }

}
