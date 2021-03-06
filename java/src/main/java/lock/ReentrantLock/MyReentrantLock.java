package lock.ReentrantLock;

import java.util.concurrent.atomic.AtomicReference;

public class MyReentrantLock {
    private AtomicReference<Thread> owner = new AtomicReference<Thread>();
    private int state = 0;

    public void lock() {
        Thread current = Thread.currentThread();
        if (current == owner.get()) {
            state++;
            return;
        }
        //这句是很经典的“自旋”式语法，AtomicInteger中也有
        for (; ; ) {
            if (!owner.compareAndSet(null, current)) {
                return;
            }
        }
    }

    public void unlock() {
        Thread current = Thread.currentThread();
        if (current == owner.get()) {
            if (state != 0) {
                state--;
            } else {
                owner.compareAndSet(current, null);
            }
        }
    }
}
