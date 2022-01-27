package jdk8;

import java.util.*;
import java.util.function.BinaryOperator;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Test {
    public static void main(String[] args) {
        User user1 = new User(1, "hangman", 100d);
        User user2 = new User(1, "list", 200d);
        User user3 = new User(2, "wing", 200d);
        User user4 = new User(2, "zeal", 100d);
        User user5 = new User(3, "tuba", 300d);
        List<User> userList = new ArrayList<>();
        userList.add(user1);
        userList.add(user2);
        userList.add(user3);
        userList.add(user4);
        userList.add(user5);
        /*********************************collectingAndThen*****************************************************/
        Double avgSalary = userList.stream()
                                   .collect(Collectors.collectingAndThen(Collectors.averagingDouble(User::getSalary), Double::doubleValue));
        String userName = userList.stream()
                                  .collect(Collectors.collectingAndThen(Collectors.maxBy(Comparator.comparing(User::getSalary)), (Optional<User> user) -> user.get().getUserName()));
        //都是对collectingAndThen执行第一个参数方法得到的结果进行第二个参数方法

        /*********************************自定义流*****************************************************/
        //Stream.generate(new UserSupplier()).limit(2).forEach(u -> System.out.println(u.getUserId() + ", " + u.getUserName()));
        double minValue = Stream.of(-4.0, 1.0, 3.0, -2.0).reduce(Double.MAX_VALUE, Double::min);
        System.out.println(minValue);
    }
}
