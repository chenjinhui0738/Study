package Supplier;

import jdk8.UserSupplier;
import org.junit.Test;
import org.springframework.util.ObjectUtils;

import java.util.*;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.stream.Stream.*;

/**
 * Supplier 接口是一个供给型的接口，其实，说白了就是一个容器，可以用来存储数据，然后可以供其他方法使用的这么一个接口
 */
public class SupplierTest {

    /**
     * 1.求年龄和
     */
    @Test
    public void Test1() {
        List<User> list = new ArrayList<>();
        list.add(new User("张三", 15));
        list.add(new User("李四", 16));
        //作为接收器
        Supplier<Integer> supplier = () -> {
            int sum = list.stream().mapToInt(User::getAge).sum();
            return sum;
        };
        System.out.println("年龄总和为" + supplier.get());
    }

    /**
     * 2.求最大值
     */
    @Test
    public void Test2() {
        int[] arr = {1, 8, 3, 7, 5};
        Supplier<Integer> supplier = () -> {
            int maxValue = arr[0];
            for (int i = 1; i < arr.length; i++) {
                if (arr[i] > maxValue) {
                    maxValue = arr[i];
                }
            }
            return maxValue;
        };
        System.out.println("最大值为" + supplier.get());
    }

    /**
     * 3.通过实现supplier函数式声明接口，可以构造自定义实体，相当于有参构造
     */
    @Test
    public void Test3() {
        //构造一个id自增，名称自定义的user
        UserSupplier userSupplier = new UserSupplier();
        System.out.println(userSupplier.get());
        System.out.println(userSupplier.get());
    }
}
