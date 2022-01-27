package lock;

public class ReentrantLockTest {
    public static void main(String[] args) throws InterruptedException {
        new Thread(new ReentrantLockTemp()).start();
    }
}
class ReentrantLockTemp implements Runnable {
    private final ReentrantLock reentrantLock = new ReentrantLock();//这里也可以使用java自带的ReentrantLock可重入锁
    @Override
    public void run() {
        a();
    }

    public void a() {
        reentrantLock.lock();
        try {
            System.out.println(Thread.currentThread().getName() + " a()");
            b();
        }finally {
            reentrantLock.unlock();
        }

    }

    private void b() {
        reentrantLock.lock();
        try {
            System.out.println(Thread.currentThread().getName() + " b()");
        }finally {
            reentrantLock.unlock();
        }
    }
}