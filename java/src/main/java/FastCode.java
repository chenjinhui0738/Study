import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import jdk8.User;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Test;
import org.springframework.cglib.beans.BeanCopier;
import org.springframework.cglib.core.Converter;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FastCode {
    @Test
    public void Test1() {
        //数组转list
        Integer[] intArr = new Integer[2];
        int[] intArr2 = new int[2];
        List<Integer> list1 = Arrays.asList(intArr);
        List<Integer> list2 = Stream.of(intArr).collect(Collectors.toList());
        List<Integer> list3 = Arrays.stream(intArr2).boxed().collect(Collectors.toList());//boxed是把int转为Integer
        List<Integer> list4 = Lists.newArrayList(intArr);
        //String数组转Integer数组
        String[] strArr = new String[]{"1", "2"};
        Integer[] intArray = (Integer[]) ConvertUtils.convert(strArr, Integer.class);
    }

    @Test
    public void Test2() {
        Integer[] intArr = new Integer[]{1, 5, 3, 2, 4};
        //数组升序排序
        Arrays.sort(intArr, (t1, t2) -> t1 - t2);
        System.out.println(JSONObject.toJSONString(intArr));
        //数组降序排序
        Arrays.sort(intArr, (t1, t2) -> t2 - t1);
        System.out.println(JSONObject.toJSONString(intArr));

        List<Integer> list = Lists.newArrayList(1, 5, 3, 2, 4);
        //数组升序排序
        Collections.sort(list, (t1, t2) -> t1 - t2);
        System.out.println(JSONObject.toJSONString(list));
    }

    @Test
    public void Test3() {
        //判断是否为null，不为null时处理
        User user1 = new User();
        Optional.ofNullable(user1).ifPresent(u -> u.setUserName("张三"));
        System.out.println(user1);
        //判断是否为null,为null时处理
        User user2 = null;
        user2 = Optional.ofNullable(user2).orElse(new User(2, "李四"));
        System.out.println(user2);
        //获取对象属性,为null时处理
        User user3 = null;
        String userName = Optional.ofNullable(user3).map(User::getUserName).orElse("王五");
        System.out.println(userName);
        //
        User user4 = null;
        Optional.ofNullable(user4).orElse(new User()).getUserName();
    }

    @Test
    public void Test4() {
        //分割list中的对象属性
        List<User> userList = Lists.newArrayList(new User(1, "张三"), new User(2, "李四"));
        String names = userList.stream().map(User::getUserName).collect(Collectors.joining(","));
        System.out.println(names);
        //分割字符串
        String[] strArr = new String[]{"张三", "李四"};
        String join = StringUtils.join(strArr, ",");
        System.out.println(join);
    }

    @Test
    public void Test5() {
        //BigDecimal大小比较
        BigDecimal b1 = new BigDecimal("1");
        BigDecimal b2 = new BigDecimal("1.0");
        System.out.println(b1.equals(b2));//不能使用equals来比较
        System.out.println(b1.compareTo(b2) == 0);

    }

    @Test
    public void Test6() {
        //使用BeanCopier工具类对Java实体类复制,不要使用beanutil效率低，且同属性不同类型无法复制
        User source = new User();
        Supplier.User target = new Supplier.User();
        BeanCopier copier = BeanCopier.create(source.getClass(), target.getClass(), true);
        copier.copy(source, target, new Converter() {
            @Override
            public Object convert(Object value, Class targetClazz, Object methodName) {
                //将string属性复制为integer
                if (value != null && value instanceof String && Integer.class.equals(targetClazz)) {
                    return Integer.parseInt(value.toString());
                }
                //将integer属性复制为string
                if (value != null && value instanceof Integer && String.class.equals(targetClazz)) {
                    return String.valueOf(value);
                }
                return value;
            }
        });

    }

    @Test
    public void Test7() {

    }

    @Test
    public void Test8() {

    }

    @Test
    public void Test9() {

    }

    @Test
    public void Test10() {

    }

    @Test
    public void Test11() {

    }

    @Test
    public void Test12() {

    }

    @Test
    public void Test13() {

    }

    @Test
    public void Test14() {

    }

    @Test
    public void Test15() {

    }

    @Test
    public void Test16() {

    }

    @Test
    public void Test17() {

    }

    @Test
    public void Test18() {

    }

    @Test
    public void Test19() {

    }

    @Test
    public void Test20() {

    }

    @Test
    public void Test21() {

    }

    @Test
    public void Test22() {

    }

    @Test
    public void Test23() {

    }

    @Test
    public void Test24() {

    }

    @Test
    public void Test25() {

    }

    @Test
    public void Test26() {

    }

    @Test
    public void Test27() {

    }

    @Test
    public void Test28() {

    }

    @Test
    public void Test29() {

    }

    @Test
    public void Test30() {

    }
}
