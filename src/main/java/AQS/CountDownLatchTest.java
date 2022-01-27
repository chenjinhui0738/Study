package AQS;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


/**
 * CountDownLatch能够使一个线程在等待另外一些线程完成各自工作之后，
 * 再继续执行。使用一个计数器进行实现。计数器初始值为线程的数量。当每一个线程完成自己任务后，计数器的值就会减一。
 * 当计数器的值为0时，表示所有的线程都已经完成一些任务，然后在CountDownLatch上等待的线程就可以恢复执行接下来的任务。
 *
 * CountDownLatch典型用法：1、某一线程在开始运行前等待n个线程执行完毕。
 * 将CountDownLatch的计数器初始化为new CountDownLatch(n)，
 * 每当一个任务线程执行完毕，就将计数器减1 countdownLatch.countDown()，当计数器的值变为0时，
 * 在CountDownLatch上await()的线程就会被唤醒。一个典型应用场景就是启动一个服务时，主线程需要等待多个组件加载完毕，之后再继续执行。
 *
 * CountDownLatch典型用法：2、实现多个线程开始执行任务的最大并行性。注意是并行性，不是并发，
 * 强调的是多个线程在某一时刻同时开始执行。类似于赛跑，将多个线程放到起点，等待发令枪响，然后同时开跑。
 * 做法是初始化一个共享的CountDownLatch(1)，将其计算器初始化为1，多个线程在开始执行任务前首先countdownlatch.await()，
 * 当主线程调用countDown()时，计数器变为0，多个线程同时被唤醒。
 */
public class CountDownLatchTest {
    public static void main(String[] args) throws InterruptedException {
        //Test1();
        Test2();
    }
    public static void Test1() throws InterruptedException{
        ExecutorService service = Executors.newCachedThreadPool();
        CountDownLatch latch = new CountDownLatch(4);//计数器，等于0时调用await可以放行
        for (int i = 0; i < 4; i++) {
            Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    try {
                        System.out.println(Thread.currentThread().getName() + "开始执行");
                        Thread.sleep(2000);
                        System.out.println(Thread.currentThread().getName() + "执行完成");
                        latch.countDown();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            };
            service.execute(runnable);
        }
        latch.await();
        System.out.println("主线程执行。。。");
    }
    public static void Test2() throws InterruptedException{
        ExecutorService service = Executors.newCachedThreadPool();
        final CountDownLatch cdOrder = new CountDownLatch(1);
        final CountDownLatch cdAnswer = new CountDownLatch(4);
        for (int i = 0; i < 4; i++) {
            Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    try {
                        System.out.println("选手" + Thread.currentThread().getName() + "正在等待裁判发布口令");
                        cdOrder.await();
                        System.out.println("选手" + Thread.currentThread().getName() + "已接受裁判口令");
                        Thread.sleep((long) (Math.random() * 10000));
                        System.out.println("选手" + Thread.currentThread().getName() + "到达终点");
                        cdAnswer.countDown();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            };
            service.execute(runnable);
        }
        try {
            Thread.sleep((long) (Math.random() * 10000));
            System.out.println("裁判"+Thread.currentThread().getName()+"即将发布口令");
            cdOrder.countDown();
            System.out.println("裁判"+Thread.currentThread().getName()+"已发送口令，正在等待所有选手到达终点");
            cdAnswer.await();
            System.out.println("所有选手都到达终点");
            System.out.println("裁判"+Thread.currentThread().getName()+"汇总成绩排名");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        service.shutdown();
//        选手pool-1-thread-2正在等待裁判发布口令
//        选手pool-1-thread-3正在等待裁判发布口令
//        选手pool-1-thread-4正在等待裁判发布口令
//        选手pool-1-thread-1正在等待裁判发布口令
//        裁判main即将发布口令
//        裁判main已发送口令，正在等待所有选手到达终点
//        选手pool-1-thread-2已接受裁判口令
//        选手pool-1-thread-4已接受裁判口令
//        选手pool-1-thread-3已接受裁判口令
//        选手pool-1-thread-1已接受裁判口令
//        选手pool-1-thread-1到达终点
//        选手pool-1-thread-3到达终点
//        选手pool-1-thread-2到达终点
//        选手pool-1-thread-4到达终点
//        所有选手都到达终点
//        裁判main汇总成绩排名
    }
}
