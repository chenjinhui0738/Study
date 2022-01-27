package lock;

import java.util.concurrent.TimeUnit;

/**
 * 自旋锁测试
 */
public class SpinLockTest {
    public static void main(String[] args) throws InterruptedException {
        SpinLock spinLock = new SpinLock();
        Thread thread1 = new Thread(new Runnable() {
            @Override
            public void run() {
                spinLock.lock();
                spinLock.unlock();//每个线程必须调用解锁否则会死锁
            }
        });
        thread1.start();
        thread1.sleep(2000);
        Thread thread2 = new Thread(new Runnable() {
            @Override
            public void run() {
                spinLock.lock();
                spinLock.unlock();
            }
        });
        thread2.start();

    }
}
