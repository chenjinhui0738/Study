package synchronize;

public class SynchronizeTest {
    String lock = new String();
    public static void main(String[] args) {
        Test4();
    }

    /**
     * 1.synchronized 作用于静态方法时，锁住的是Class实例，因为静态方法属于Class而不属于对象。
     * 在不同的实例对象中也能锁的住
     */
    private static void Test1() {
        Thread thread1 = new Thread(new Runnable() {
            @Override
            public void run() {
                print1();
            }
        });
        Thread thread2 = new Thread(new Runnable() {
            @Override
            public void run() {
                print1();
            }
        });
        thread1.start();
        thread2.start();
    }

    /**
     * 2.synchronized作用于成员变量和非静态方法时，锁住的是对象的实例，即 this对象。
     */
    private static void Test2() {
        SynchronizeTest synchronizeTest1 = new SynchronizeTest();
        SynchronizeTest synchronizeTest2 = new SynchronizeTest();
        Thread thread1 = new Thread(new Runnable() {
            @Override
            public void run() {
                synchronizeTest1.print2();//添加在成员变量和非静态方法时，锁住的是对象的实例，不同实例则出现并发问题
            }
        });
        Thread thread2 = new Thread(new Runnable() {
            @Override
            public void run() {
                synchronizeTest2.print2();//添加在成员变量和非静态方法时，锁住的是对象的实例，不同实例则出现并发问题
            }
        });
        thread1.start();
        thread2.start();
    }

    /**
     * 3.synchronized 作用 于一个代码块时，锁住的是所有代码块中配置的对象
     * (1)当锁的对象为当前实例，在不同实例直接运行则会出现并发问题
     */
    private static void Test3() {
        SynchronizeTest synchronizeTest1 = new SynchronizeTest();
        SynchronizeTest synchronizeTest2 = new SynchronizeTest();
        Thread thread1 = new Thread(new Runnable() {
            @Override
            public void run() {
                synchronizeTest1.print3();
            }
        });
        Thread thread2 = new Thread(new Runnable() {
            @Override
            public void run() {
                synchronizeTest2.print3();
            }
        });
        thread1.start();
        thread2.start();
        
    }
    /**
     * 3.synchronized 作用 于一个代码块时，锁住的是所有代码块中配置的对象
     * (2)当锁的对象为静态变量，在不同实例直接运行不会会出现并发问题
     */
    private static void Test4() {
        SynchronizeTest synchronizeTest1 = new SynchronizeTest();
        SynchronizeTest synchronizeTest2 = new SynchronizeTest();
        Thread thread1 = new Thread(new Runnable() {
            @Override
            public void run() {
                synchronizeTest1.print4();
            }
        });
        Thread thread2 = new Thread(new Runnable() {
            @Override
            public void run() {
                synchronizeTest2.print4();
            }
        });
        thread1.start();
        thread2.start();

    }

    /**
     * 加在静态方法上
     */
    private synchronized static void print1() {
        for (int i = 0; i <100 ; i++) {
            System.out.println(Thread.currentThread().getName()+"："+i);
        }
    }
    /**
     * 加在成员方法上
     */
    private synchronized void print2() {
        for (int i = 0; i <100 ; i++) {
            System.out.println(Thread.currentThread().getName()+"："+i);
        }
    }
    /**
     * 加在代码块上
     */
    private  void print3() {
        synchronized(this){//this指的就是当前实例对象
            for (int i = 0; i <100 ; i++) {
                System.out.println(Thread.currentThread().getName()+"："+i);
            }
        }
    }
    /**
     * 加在代码块上
     */
    private  void print4() {
        synchronized(lock){//需要全局变量（字节码，常量这些，或者静态变量），如果不是全局变量即使锁住了还是会出现并发问题,因为成员变量存在于对象实例中，在不同实例时就会产生并发。
            for (int i = 0; i <100 ; i++) {
                System.out.println(Thread.currentThread().getName()+"："+i);
            }
        }
    }
}
