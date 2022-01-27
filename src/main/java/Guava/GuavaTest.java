package Guava;


import com.google.common.collect.Lists;
import com.sun.net.httpserver.HttpServer;
import jdk8.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.BinaryOperator;

public class GuavaTest {
    public static void main(String[] args) {
//        List list = null;
//        List or = Optional.fromNullable(list).or(Lists.newArrayList(new int[]{1, 2}));
        List<User> userList = Lists.newArrayList(new User[]{new User(1, "张三", 1.0d), new User(2, "李四", 2.0d)});
        Double reduce = userList.stream().reduce(0.0, (x, y) -> x + (y.getUserId() * y.getSalary()), Double::sum);
        Double reduce2 = userList.stream().reduce(0.0, (aDouble, user) -> aDouble + user.getUserId() * user.getSalary(), (aDouble, aDouble2) -> aDouble + aDouble2);
    }
}
