package com.cjh.spring.service;

import com.alibaba.fastjson.JSONObject;
import com.cjh.spring.bean.Person;
import com.cjh.spring.bean.User;
import com.cjh.spring.dao.UserDao1;
import com.cjh.spring.dao.UserDao2;
import com.cjh.spring.dao.UserDao3;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 *
 */
@Service
public class UserService {
    /**
     * 普通类的3种注入方式
     */
    @Autowired
    private UserDao1 userDao1;//1.通过属性注入

    private UserDao2 userDao2;
    @Autowired //2.通过构造器注入
    public UserService(UserDao2 userDao2) {
        this.userDao2 = userDao2;
    }

    private UserDao3 userDao3;
    @Autowired  //3.通过setter方法实现注入
    public void setUserDao3(UserDao3 userDao3) {
        this.userDao3 = userDao3;
    }

    /**
     *接口注入
     */

    //1.通过配置文件和 @ConditionalOnProperty 注解实现
    @Autowired
    private PersonInterface personInterface;
    public Person getPerson(){
        Person person = personInterface.getPerson();
        System.out.println(JSONObject.toJSONString(person));
        return person;
    }
    //2.通过 @Resource 注解动态获取
    @Resource(name = "userImpl1",type = UserImpl1.class)//当接口有多个实现类，可以通过名称或者类型指定
    private UserInterface userInterface1;
    public User getUser2(){
        User user = userInterface1.getUser();
        System.out.println(JSONObject.toJSONString(user));
        return user;
    }
    //3.通过集合注入
    //如果使用的是 List 集合，那么我们可以取出来再通过 instanceof 关键字来判定类型；而通过 Map 集合注入的话，
    //Spring 会将 Bean 的名称（默认类名首字母小写）作为 key 来存储，这样我们就可以在需要的时候动态获取自己想要的实现类。
    @Autowired
    List<UserInterface> list;
    @Autowired
    private Map<String,UserInterface> map;
    public List<User> getUserList(){
        User user1 = null;
        for (UserInterface inter : list) {
            if(inter instanceof UserImpl1){
                user1 = inter.getUser();
            }
        }
        UserInterface userImpl2 = map.get("userImpl2");
        User user2 = userImpl2.getUser();
        List<User> list = new ArrayList();
        list.add(user1);
        list.add(user2);
        System.out.println(JSONObject.toJSONString(list));
        return list;
    }

    //4.@Primary 注解实现默认注入
    @Autowired
    private UserInterface userInterface2;
    public User getUser3(){
        User user = userInterface2.getUser();
        System.out.println(JSONObject.toJSONString(user));
        return user;
    }

    /**
     * @Qualifier("name")
     * @Autowired
     * 相当于
     * @Resource(name = "name",type = Bean.class)
     *
     * Qualifier有个好处可以指定入参类型,而resource不能用于参数
     */
    @Bean//这个注解表明返回的类
    public User getUser(@Qualifier("userImpl2") UserInterface inter){
        User user = inter.getUser();
        System.out.println(JSONObject.toJSONString(user));
        return user;
    }
}
