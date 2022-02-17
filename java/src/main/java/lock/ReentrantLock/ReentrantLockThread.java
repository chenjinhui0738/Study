package lock.ReentrantLock;

class ReentrantLockThread implements Runnable {
    private final MyReentrantLock reentrantLock = new MyReentrantLock();//这里也可以使用java自带的ReentrantLock可重入锁
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