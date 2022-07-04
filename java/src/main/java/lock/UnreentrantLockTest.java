package lock;

/**
 * 不可重入锁测试
 */
public class UnreentrantLockTest {
    public static void main(String[] args) throws InterruptedException {
        new Thread(new UnreentrantLockTemp()).start();
    }
}

class UnreentrantLockTemp implements Runnable {
    private final UnreentrantLock unreentrantLock = new UnreentrantLock();

    @Override
    public void run() {
        a();
    }

    public void a() {
        unreentrantLock.lock();
        try {
            System.out.println(Thread.currentThread().getName() + " a()");
            b();
        } finally {
            unreentrantLock.unlock();
        }

    }

    private void b() {
        unreentrantLock.lock();
        try {
            System.out.println(Thread.currentThread().getName() + " b()");
        } finally {
            unreentrantLock.unlock();
        }
    }
}
