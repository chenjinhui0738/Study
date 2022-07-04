package jdk8;

import com.google.common.collect.Lists;

import java.util.List;

public class ReduceTest {
    private static final List<User> userList = Lists.newArrayList(new User[]{new User(1, "hangman", 100d),new User(1, "list", 200d),new User(2, "wing", 200d),
            new User(2, "zeal", 100d),new User(3, "tuba", 300d)});
    public static void main(String[] args) {
        Test1();
    }

    private static void Test1() {
        //1.没有初始值的累加值
        double sum = userList.stream().mapToDouble(User::getSalary).reduce(0, (a, b) -> a + b);
        //2.有初始值的累加值
        double sum2 = userList.stream().mapToDouble(User::getSalary).reduce(100, (a, b) -> a + b);
        //3.没有初始值的累加值（并行流）
        double sumParallel = userList.parallelStream().mapToDouble(User::getSalary).reduce(0, (a, b) -> a + b);
        //4.有初始值的累加值（并行流），会导致每个流的初始值都计算进去
        double sumParallel2 = userList.parallelStream().mapToDouble(User::getSalary).reduce(100, (a, b) -> a + b);
        //reduce其实就是执行指定方法操作，可以拿到每次操作的结果,常用于累加操作
        //System.out.println(sum);//900.0
        //System.out.println(sum2);//1000.0 初始值为100
        //System.out.println(sumParallel);//900.0
        //System.out.println(sumParallel2);//1400.0 启动了五个流线程，每个线程初始化100，5*100+900
        //5.求乘积之和，第二个参数x代表当前累计的值，y代表当前对象，第三个参数为每次的操作
        double sumParallel3 = userList.parallelStream().reduce(0.0, (x, y) -> x + (y.getUserId() * y.getSalary()), Double::sum);
    }

    public void Test2() {
        //        List list = null;
//        List or = Optional.fromNullable(list).or(Lists.newArrayList(new int[]{1, 2}));
        List<User> userList = Lists.newArrayList(new User[]{new User(1, "张三", 1.0d), new User(2, "李四", 2.0d)});
        Double reduce = userList.stream().reduce(0.0, (x, y) -> x + (y.getUserId() * y.getSalary()), Double::sum);
        Double reduce2 = userList.stream().reduce(0.0, (aDouble, user) -> aDouble + user.getUserId() * user.getSalary(), (aDouble, aDouble2) -> aDouble + aDouble2);
    }

}
