package ThreadLocal;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadLocalTest {
    private static final ThreadLocal threadLocal = new ThreadLocal();
    private static Integer integer = 0;
    public static int addNum = 0;
    public static void main(String[] args) {
        //Test1();
        //Test2();
        //Test3();
        Test4();
        //Test5();
    }

    //不使用变量副本的情况，因为各个线程的资源是共享的，所以不会按顺序打印
    private static void Test1() {
        ExecutorService service = Executors.newFixedThreadPool(20);
        for (int i = 0; i < 20; i++) {
            addNum = i;
            service.execute(()->{
                System.out.println(Thread.currentThread().getName()+":"+addNum);
            });
        }
        service.shutdown();
    }
    //使用变量副本的情况下，每个线程的变量都是独享的，所以可以按顺序打印
    private static void Test2() {
        ExecutorService service = Executors.newFixedThreadPool(20);
        for (int i = 0; i < 20; i++) {
            int finalI = i;
            service.execute(()->{
                threadLocal.set(finalI);
                System.out.println(Thread.currentThread().getName()+":"+threadLocal.get());
            });
        }
        service.shutdown();
    }
    //按顺序进行+10操作并输出
    private static void Test3() {
        ExecutorService service = Executors.newFixedThreadPool(20);
        for (int i = 0; i < 20; i++) {
            int num = i;
            service.execute(()->{
                System.out.println(num + " " +  NumUtil.add10(num));
            });
        }
        service.shutdown();
    }
    //使用线程不安全的方式进行日期格式转换会报异常
    private static void Test4() {
        ExecutorService service = Executors.newFixedThreadPool(20);
        for (int i = 0; i < 20; i++) {
            service.execute(()->{
                System.out.println(DateUtil.parse("2019-06-01 16:34:30"));
            });
        }
        service.shutdown();
    }
    //使用线程安全的方式进行日期格式转换
    private static void Test5() {
        ExecutorService service = Executors.newFixedThreadPool(20);
        for (int i = 0; i < 20; i++) {
            service.execute(()->{
                System.out.println(DateUtil.parseThreadLocal("2019-06-01 16:34:30"));
            });
        }
        service.shutdown();
    }

}
