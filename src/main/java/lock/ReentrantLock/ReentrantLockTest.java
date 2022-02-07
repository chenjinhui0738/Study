package lock.ReentrantLock;

import java.util.concurrent.locks.ReentrantLock;

public class ReentrantLockTest {
    public static ReentrantLock lock = new ReentrantLock() ;
    public static int i = 0;
    public static void main(String[] args) throws InterruptedException {
        //Test1();
        Test2();
    }

    /**
     * 自定义可重入锁
     */
    private static void Test1() {
        new Thread(new ReentrantLockThread()).start();
    }
    /**
     *
     */
    private static void Test2() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int j = 0; j <100 ; j++) {
                    //lock.lock();//可以对同一把锁多次加锁或者解锁
                    i++;
                    System.out.println(i);
                   // lock.unlock();
                }
            }
        }).start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int j = 0; j <100 ; j++) {
                   // lock.lock();//可以对同一把锁多次加锁或者解锁
                    i++;
                    System.out.println(i);
                    //lock.unlock();
                }
            }
        }).start();
    }
}
