package lock;

import java.util.concurrent.atomic.AtomicReference;

/**
 * 不可重入锁
 */
public class UnreentrantLock {
    private AtomicReference<Thread> owner = new AtomicReference<Thread>();

    public void lock() {
        Thread t = Thread.currentThread();
        //当 lockedThread 持有引用变量为 null 时，设置 lockedThread 持有引用为 当前线程变量
        while (!owner.compareAndSet(null, t)) {
            //自旋，空循环，等到锁被释放
        }
    }

    public void unlock() {
        //如果是本线程锁定的，可以成功释放锁
        owner.compareAndSet(Thread.currentThread(), null);
    }

}
