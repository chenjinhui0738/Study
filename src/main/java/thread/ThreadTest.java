package thread;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import static java.lang.Thread.sleep;

public class ThreadTest {
    public static int count = 0;
    public Object object = new Object();


    /**
     * 接口的三种运行方式
     * 1.继承Thread
     * 2.实现Runnable接口
     * 3.线程池
     *
     * 运行线程必须使用start(),不能使用run();直接运行run方法相当在主线程运行run方法体，而不是新开一个线程运行
     *
     * 当run方法（线程体）运行完后线程自动结束
     */
    @Test
    public void Test1() throws Exception {
        //1.继承Thread
        Thread thread1 = new MyThread();
        thread1.start();
        //2.实现Runnable接口
        MyRunnable myRunnable = new MyRunnable();
        Thread thread2 = new Thread(myRunnable);
        thread2.start();
        //3.线程池
        ExecutorService executorService = Executors.newFixedThreadPool(5);
        //无返回值启动
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                System.out.println("通过线程池方式启动");
            }
        });
        //有返回值启动
        Future<Integer> future = executorService.submit(new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                return new Random().nextInt();
            }
        });
        System.out.println("通过线程池方式启动,带返回值:"+future.get());
        executorService.shutdown();
    }

    /**
     * 线程中断： interrupt 方法
     */
    @Test
    public void Test2() throws InterruptedException {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                //1.线程未终止则正常执行业务
                if(!Thread.currentThread().isInterrupted()){
                    try {
                        System.out.println("执行业务中");
                        sleep(500);
                    } catch (Exception e) {
                        Thread.currentThread().interrupt( );//执行业务异常则终止线程
                        e.printStackTrace();
                    }

                }
                //线程终止，处理线程结束前必要的一些资源释放和清理工作，比如释放锁、 存储数据到持久化层、发出异常通知等，用于实现线程的安全退出
                if(Thread.currentThread().isInterrupted()){
                    System.out.println("释放资源/数据持久化中");
                }
            }
        });
        thread.start();//先让线程模拟正常运行
        sleep(100);//运行100ms后执行中断，这个值设置太小会导致马上执行中断看不到运行中被中断的效果
        thread.interrupt();//执行中断
    }

    /**
     * 线程加入:join方法
     *
     * 可以让线程按照先后顺序运行
     */
    @Test
    public void Test3() throws Exception {
        Thread thread1 = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("第一个线程运行中");
            }
        });
        Thread thread2 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    thread1.join();//等线程1执行完才会运行本线程
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("第二个线程运行中");
            }
        });
        Thread thread3 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    thread2.join();//等线程2运行完才会运行本线程
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("第三个线程运行中");
            }
        });
        thread3.start();
        thread2.start();
        thread1.start();

    }
    /**
     * 线程等待：wait方法；wait是会释放锁资源的，而sleep不会
     *
     * 线程唤醒：notify方法
     */
    @Test
    public void Test4() throws InterruptedException {
        Thread thread1 = new Thread(() -> {
            synchronized (object){
                System.out.println("线程1开始等待");
                try {
                    object.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("线程1等待结束");
            }

        });
        Thread thread2 = new Thread(() -> {
            synchronized (object){
                System.out.println("线程2开始等待");
                try {
                    object.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("线程2等待结束");
            }
        });
        Thread thread3 = new Thread(() -> {
            synchronized (object){
                System.out.println("线程3开始等待");
                try {
                    object.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("线程3等待结束");
            }
        });
        Thread notifyThread = new Thread(() -> {
            synchronized (object){
                System.out.println("通知唤醒开始");
                object.notifyAll();
                System.out.println("通知唤醒结束");
            }
        });
        thread1.start();
        thread2.start();
        thread3.start();
        sleep(100);//先等所有线程启动完后再进行唤醒通知，不然有些线程还没启动是无法唤醒的
        notifyThread.start();//notify是随机唤醒一个线程，notifyAll是唤醒所有线程
    }

    /**
     * 后台守护线程： setDaemon 方法
     *
     * 当用户线程都结束，只剩守护线程时，jvm则会退出
     *
     */
    @Test
    public void Test5() throws InterruptedException {
        Thread thread = new Thread(() -> {
            for(int i=0;;i++){
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException ex) {   }
                System.out.println(i);
            }
        });
        thread.setDaemon(true);
        thread.start();
        try {
            System.in.read();   // 接受输入，使程序在此停顿，一旦接收到用户输入，main线程结束，守护线程自动结束
        } catch (IOException ex) {

        }
    }

    /**
     * 并发演示
     */
    @Test
    public void Test6() {
        for (int i=0;i<=100;i++){
            new Thread(new Runnable() {
                @Override
                public void run() {
                    count ++;
                }
            }).start();
        }
        System.out.println(count);
    }
}
