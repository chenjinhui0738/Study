package ThreadLocal;

import org.junit.jupiter.api.Test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * ThreadLocal其实就是每个新线程访问共享变量时，保存一份自己的线程副本。每个线程都是使用自己的值。
 * ThreadLocal相当于给每个线程都开辟了一个独立的存储空间，各个线程的ThreadLocal关联的实例互不干扰。
 *在多线程环境中，通常在使用不安全的类时使用ThreadLocal；按照各自线程的值进行操作某些操作
 */
public class ThreadLocalTest {
    public final ThreadLocal threadLocal = new ThreadLocal();
    public Integer integer = 0;
    public static int addNum = 0;

    /**
     * 不使用ThreadLocal按顺序打印1-20，结果为乱序
     * 不使用变量副本的情况，因为各个线程的资源是共享的，所以不会按顺序打印
     */
    @Test
    public void Test1() {
        ExecutorService service = Executors.newFixedThreadPool(20);
        for (int i = 0; i < 20; i++) {
            addNum = i;
            service.execute(()->{
                System.out.println(Thread.currentThread().getName()+":"+addNum);
            });
        }
        service.shutdown();
    }

    /**
     * 使用ThreadLocal按顺序打印1-20，结果为正序
     * 使用变量副本的情况下，每个线程的变量都是独享的，所以可以按顺序打印
     */
    @Test
    public void Test2() {
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
    @Test
    public void Test3() {
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
    @Test
    public void Test4() {
        ExecutorService service = Executors.newFixedThreadPool(20);
        for (int i = 0; i < 20; i++) {
            service.execute(()->{
                System.out.println(DateUtil.parse("2019-06-01 16:34:30"));
            });
        }
        service.shutdown();
    }
    //使用线程安全的方式进行日期格式转换
    @Test
    public void Test5() {
        ExecutorService service = Executors.newFixedThreadPool(20);
        for (int i = 0; i < 20; i++) {
            service.execute(()->{
                System.out.println(DateUtil.parseThreadLocal("2019-06-01 16:34:30"));
            });
        }
        service.shutdown();
    }

}
