package lock;

import java.util.concurrent.atomic.AtomicReference;

/**
 * 自旋锁
 */
public class SpinLock {
    private AtomicReference<Thread> cas = new AtomicReference<Thread>();
    public void lock() {
        Thread current = Thread.currentThread();
        // 利用CAS
        //第一个线程进来判断为空不进入循环并设置线程副本；第二个线程进来判断不为空进入循环并且为死循环，所以个每个线程调用lock完必须调用unlock
        while (!cas.compareAndSet(null, current)) {
            // DO nothing
        }
        System.out.println(current.getName()+"=》"+"上锁了");
    }
    public void unlock() {
        Thread current = Thread.currentThread();
        cas.compareAndSet(current, null);
        System.out.println(current.getName()+"=》"+"解锁了");
    }
}
