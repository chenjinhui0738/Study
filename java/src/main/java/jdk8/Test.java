package jdk8;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.Comparator.comparing;

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
        userList.forEach(o -> o.setUserId(2));
        List<User> collect1 = userList.stream().peek(o -> o.setUserId(2)).collect(Collectors.toList());
        List<String> collect2 = userList.stream().map(User::getUserName).collect(Collectors.toList());
        //第1个参数:key,第2个参数：值，这里是整个实体,第3个参数:key重复时的处理,这里选后一个值
        Map<Integer, User> collect = userList.stream().collect(Collectors.toMap(User::getUserId, o -> o, (a, b) -> b));
        /*********************************collectingAndThen*****************************************************/
        Double avgSalary = userList.stream().collect(Collectors.collectingAndThen(Collectors.averagingDouble(User::getSalary), Double::doubleValue));
        //peek是对流的中间操作，没有使用末端操作的话是不会执行的
        userList.stream().peek(user -> user.setUserId(0)).collect(Collectors.toList());
        System.out.println(userList.toString());
        System.out.println(avgSalary);
        String userName = userList.stream().collect(Collectors.collectingAndThen(Collectors.maxBy(comparing(User::getSalary)), (Optional<User> user) -> user.get().getUserName()));
        System.out.println(userName);
        //都是对collectingAndThen执行第一个参数方法得到的结果进行第二个参数方法

        /*********************************自定义流*****************************************************/
        Stream.generate(new UserSupplier()).limit(2).forEach(u -> System.out.println(u.getUserId() + ", " + u.getUserName()));
        //求最小值，reduce第一个参数为初始值
        double minValue = Stream.of(-4.0, 1.0, 3.0, -2.0).reduce(Double.MAX_VALUE, Double::min);
        System.out.println(minValue);
    }
}
