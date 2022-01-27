package reflection;

import com.google.common.collect.Lists;
import jdk8.Product;
import jdk8.User;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;

/**
 * jdk反射
 */
public class ClassTest {
    public static void main(String[] args) throws Exception {
        //Test1();
        Test2();
        Test3();
    }

    /**
     * 获取字段、方法、构造函数
     * @throws ClassNotFoundException
     */
    private static void Test1() throws ClassNotFoundException {
        //1:User
        Class clazz = Class.forName("jdk8.User");
        //2:获取User类的所有方法的信息
        Method[] method = clazz.getDeclaredMethods();
        for (Method m : method) {
            System.out.println("方法:"+m.toString());
        }
        //3:获取User类的所有成员的属性信息
        Field[] field = clazz.getDeclaredFields();
        for (Field f : field) {
            System.out.println("字段:"+f.toString());
        }
        //4:获取User类的所有构造方法的信息
        Constructor[] constructor = clazz.getDeclaredConstructors();
        for (Constructor c : constructor) {
            System.out.println("构造方法:"+c.toString());

        }
    }

    /**
     * 通过反射获取对象两种方式：
     * 1.使用newInstane方法创建对象
     * 2.根据构造方法创建对象并设置属性
     * @throws Exception
     */
    private static void Test2() throws Exception {
        //1.使用newInstane方法创建对象
        Class clazz =Class.forName("jdk8.User") ;
        User user1 =(User) clazz.newInstance();
        System.out.println(user1.toString());
        //2.根据构造方法创建对象并设置属性
        Constructor c = clazz.getDeclaredConstructor(Integer.class,String.class, Double.class, List.class);
        User user2 =(User) c.newInstance(1,"李四",20d, Lists.newArrayList(new Product(1,10d)));
        System.out.println(user2.toString());

    }

    /**
     * 动态调用对象方法
     */
    private static void Test3() throws Exception {
        //step 1:获取 User的class对象
        Class clz = Class.forName ( "jdk8.User");
        //step 2:获取class对象中的setName方法
        Method method = clz.getMethod ("setUserName" , String.class);
        //step 3:获取Constructor对象
        Constructor constructor = clz.getConstructor();
        //step 4:根据Constructor定义对象
        User user = (User)constructor.newInstance();
        //step 5:调用method的invoke方法，这里的method表示 setName方法.因此，相当于动态调用object对象的setName方法并传入参数
        method.invoke(user, "张三");
        System.out.println(user.toString());

    }

}
