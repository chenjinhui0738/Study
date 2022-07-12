package Consumer;

import com.google.common.base.Function;
import com.google.common.collect.Lists;
import jdk8.User;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

/**
 * 1、对入参要进行一系列操作，就可以用Consumer封装这些操作，供来其他人使用
 * <p>
 * 2、一个需要维护数据对象的类（如ArrayList底层维护的是一个数组：Object[] elementData），在封装的逻辑代码中需要使用到所维护的数据对象时，即可考虑使用Consumer为入参。
 */
public class ConsumerTest {

    /**
     * 1.对一组数据+2并求和
     * 其实就是一种优雅的数据处理方式，methodA先进行一次数据处理，然后通过回调接受回来的数据再进行处理，其实写在一个方法里也是完全可以的
     */
    @Test
    public void Test1() {
        AtomicInteger sum = new AtomicInteger();
        //定义处理操作
        Consumer<List<Integer>> consumer = (list) -> {
            for (Integer integer : list) {
                sum.addAndGet(integer+2);
            }

        };
        List<Integer> list = Arrays.asList(new Integer[]{1, 2, 3});
        consumer.accept(list);
        System.out.println(sum);
    }

    /**
     * 2.求两数组最大数之和
     */
    @Test
    public void Test2() {
        AtomicReference<Integer> sum = new AtomicReference<>(0);
        BiConsumer<List<Integer>, List<Integer>> biConsumer = (list1, list2) -> sum.set(Collections.max(list1) + Collections.max(list2));
        biConsumer.accept(Arrays.asList(new Integer[]{1, 2, 3}), Arrays.asList(new Integer[]{4, 5, 6}));
        System.out.println(sum.get());//只能通过属性赋值方式获取

    }

    /**
     * 将工资变为2倍
     */
    public void Test3() {
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
