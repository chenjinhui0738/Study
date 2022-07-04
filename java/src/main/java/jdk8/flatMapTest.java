package jdk8;

import com.google.common.collect.Lists;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 流的扁平化操作
 * 将list中的list取出形成一个新的list，其实就是将list合并操作
 */
public class flatMapTest {
    //计算用户拥有的商品总价格
    @Test
    public void Test1() {
        List<User> userList = Lists.newArrayList(new User[]{new User(1, "hangman", 100d, Lists.newArrayList(new Product[]{new Product(1, 10d)}))
                , new User(1, "list", 200d, Lists.newArrayList(new Product[]{new Product(2, 20d), new Product(3, 30d)}))});
        double sum = userList.stream().flatMap(user -> user.getProductList().stream()).mapToDouble(Product::getPrice).sum();
    }

    /**
     * 获取两个集合的乘积组合
     */
    @Test
    public void Test2() {
        Integer[] nums2 = {1, 2, 3};
        Integer[] nums3 = {3, 4};
        List<Integer> nums2List = Arrays.asList(nums2);
        List<Integer> nums3List = Arrays.asList(nums3);
        //使用2个map嵌套过滤
        List<int[]> res2 = nums2List.stream().flatMap(i -> nums3List.stream().map(j -> new int[]{i, j})).collect(Collectors.toList());
        //[1,2,3],[3,4]=>[1,3],[1,4],[2,3],[2,4],[3,3],[3,4]
    }
}
