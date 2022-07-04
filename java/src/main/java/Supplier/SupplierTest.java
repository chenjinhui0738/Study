package Supplier;

import jdk8.UserSupplier;

import java.util.*;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.stream.Stream.*;

public class SupplierTest {
    public static void main(String[] args) {
        Integer a = null;
        if (a > 0) System.out.println(2);
        //Test1();
        //Test2();
    }

    //1.求年龄和
    private static void Test1() {
        List<User> list = new ArrayList<>();
        list.add(new User("张三", 15));
        list.add(new User("李四", 16));
        //作为接收器
        Integer sum = getAgeSum(new Supplier<Integer>() {
            @Override
            public Integer get() {
                int sum = list.stream().mapToInt(User::getAge).sum();
                return sum;
            }
        });
        System.out.println("年龄总和为" + sum);
    }

    private static Integer getAgeSum(Supplier<Integer> supplier) {
        User user = new User("王五", 17);
        return supplier.get() + user.getAge();
    }

    //2.求最大值
    private static void Test2() {
        int[] arr = {1, 8, 3, 7, 5};
        int max = getMax(new Supplier<Integer>() {
            @Override
            public Integer get() {
                int maxValue = arr[0];
                for (int i = 1; i < arr.length; i++) {
                    if (arr[i] > maxValue) {
                        maxValue = arr[i];
                    }
                }
                return maxValue;
            }
        });
        System.out.println("最大值为" + max);
    }

    private static int getMax(Supplier<Integer> supplier) {
        return supplier.get();
    }

    //3.通过实现supplier函数式声明接口，可以构造自定义实体，相当于有参构造
    private static void Test3() {
        //构造一个id自增，名称自定义的user
        UserSupplier userSupplier = new UserSupplier();
    }
}
