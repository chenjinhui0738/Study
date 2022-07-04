package Consumer;

import com.google.common.base.Function;
import com.google.common.collect.Lists;
import jdk8.User;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

/**
 * 1、对入参要进行一系列操作，就可以用Consumer封装这些操作，供来其他人使用
 * <p>
 * 2、一个需要维护数据对象的类（如ArrayList底层维护的是一个数组：Object[] elementData），在封装的逻辑代码中需要使用到所维护的数据对象时，即可考虑使用Consumer为入参。
 */
public class ConsumerTest {
    public static void main(String[] args) {
        //Test1();
        //Test2();
        Test3();

    }

    //1.对一组数据+2并求和
    // 其实就是一种优雅的数据处理方式，methodA先进行一次数据处理，然后通过回调接受回来的数据再进行处理，其实写在一个方法里也是完全可以的
    private static void Test1() {
        AtomicInteger sum = new AtomicInteger();
        MethodA(o -> {
            sum.addAndGet(o);
        });
        System.out.println(sum);
    }

    private static void MethodA(Consumer<Integer> consumer) {
        //这里可以做一些数据处理，然后回调回去再做其他处理
        List<Integer> list = Arrays.asList(new Integer[]{1, 2, 3});
        for (Integer integer : list) {
            consumer.accept(integer + 2);
        }
    }

    //2.求两数组最大数之和
    private static void Test2() {
        User user = new User();
        MethodB(new BiConsumer<Integer, Integer>() {
            @Override
            public void accept(Integer integer1, Integer integer2) {
                user.setUserId(integer1 + integer2);
            }
        });
        System.out.println(user.getUserId());//只能通过属性赋值方式获取

    }

    private static void MethodB(BiConsumer<Integer, Integer> consumer) {
        Integer max1 = Collections.max(Arrays.asList(new Integer[]{1, 2, 3}));
        Integer max2 = Collections.max(Arrays.asList(new Integer[]{4, 5, 6}));
        consumer.accept(max1, max2);
    }

    /**
     * 将工资变为2倍
     */
    private static void Test3() {
        List<User> list = Lists.newArrayList(new User(1, "张三", 1d), new User(2, "张三", 2d));
        Function<User, Double> getSalary = User::getSalary;
        BiConsumer<User, Double> setSalary = User::setSalary;
        for (User user : list) {
            Double salary = getSalary.apply(user);
            setSalary.accept(user, 2 * salary);
        }
        System.out.println(list.toString());
    }
}
