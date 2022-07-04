package Atomic;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * 由于i++和++i不具备原子性.我们可以通过 synchronized ReentrantLock将该操作变成一个原子操作，但
 * synchronized和ReentrantLock 属于重量级锁,原子类的性能通常是 synchronized和ReentrantLock的好几倍,所以引入原子类
 * 常见原子类:AtomicBoolean、AtomicInteger、AtomicLong、AtomicReference<T>
 */
public class AtomicTest {
    public static Integer a = 1;
    public static AtomicInteger b = new AtomicInteger(1);

    public static void main(String[] args) {
        Test1();
    }

    private static void Test1() {
        for (int i = 0; i < 100; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    System.out.println("a=" + a++);
                    //System.out.println("b="+b.getAndIncrement());
                }
            }).start();
        }
    }
}
