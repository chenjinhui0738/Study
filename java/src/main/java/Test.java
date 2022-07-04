import com.google.common.collect.Lists;
import jdk8.User;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public class Test {
    public static void main(String[] args) {
        ArrayList<Integer> integers = Lists.newArrayList(1, 2, 5, 3);
        List<Integer> list = integers;
        Integer max = list.stream().max(Integer::compareTo).get();
        //List<Integer> maxIndex = IntStream.range(0, list.size()).mapToObj(x -> list.get(x)).collect(Collectors.toList());
        int[] maxIndex = IntStream.range(0, list.size()).filter(o -> max.equals(o)).toArray();
        System.out.println(max);
        System.out.println(Lists.newArrayList(maxIndex));
        /*Integer[] inputArray = new Integer[]{1, 3, 5, 7, 9};
        Integer[] out = new Integer[inputArray.length-1];
        //方法一 index就是自增索引
        AtomicInteger index=new AtomicInteger(0);

        List<Integer> collect1 = Arrays.stream(inputArray).map(x -> x + index.getAndIncrement()).collect(Collectors.toList());

        //方法二
        List<Integer> collect = IntStream.range(0, inputArray.length).filter().collect(Collectors.toList());
        System.out.println(collect1);
        System.out.println(collect);*/
        System.out.println(ObjectUtils.nullSafeClassName(Lists.newArrayList()));
        System.out.println(new Integer[]{1, 2, 2}.toString());
        Integer[] objects = (Integer[]) ObjectUtils.toObjectArray(new int[]{1, 2, 2});
        System.out.println(ObjectUtils.nullSafeClassName(Lists.newArrayList()));
        List<User> list1 = Lists.newArrayList();
        Integer[] a = null;
        System.out.println(ObjectUtils.nullSafeToString(a));
        System.out.println(new Integer[]{}.toString());
        List<Integer> list2 = null;
    }

}
