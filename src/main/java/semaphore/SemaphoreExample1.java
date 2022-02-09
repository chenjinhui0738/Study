package semaphore;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

/**
 *
 * @author Snailclimb
 * @date 2018年9月30日
 * @Description: 需要一次性拿一个许可的情况
 */
public class SemaphoreExample1 {
    // 请求的数量
    private static final int threadCount = 500;

    public static void main(String[] args) throws InterruptedException {
        Test1();
        //Test2();
    }

    /**
     * 每次只允许20个线程同时访问
     */
    private static void Test1() {
        // 创建一个具有固定线程数量的线程池对象（如果这里线程池的线程数量给太少的话你会发现执行的很慢）
        ExecutorService threadPool = Executors.newFixedThreadPool(300);
        // 一次只能允许执行的线程数量。
        final Semaphore semaphore = new Semaphore(20);

        for (int i = 0; i < threadCount; i++) {
            final int threadnum = i;
            threadPool.execute(() -> {// Lambda 表达式的运用
                try {
                    semaphore.acquire();// 获取一个许可
                    Thread.sleep(1000);// 模拟请求的耗时操作
                    System.out.println("threadnum:" + threadnum);
                    Thread.sleep(1000);// 模拟请求的耗时操作
                    semaphore.release();// 释放一个许可
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

            });
        }
        threadPool.shutdown();
        System.out.println("finish");
    }

    /**
     * 模拟停车场
     * 100量车进入,每次只允许5量汽车存在
     */
    private static void Test2() {
        //停车场同时容纳的车辆10
        final Semaphore semaphore=new Semaphore(5);
        //模拟100辆车进入停车场
        for(int i=0;i<100;i++){
            Thread thread=new Thread(new Runnable() {
                public void run() {
                    try {
                        System.out.println("===="+Thread.currentThread().getName()+"来到停车场");
                        if(semaphore.availablePermits()==0){
                            System.out.println("车位不足，请耐心等待");
                        }
                        semaphore.acquire(1);//获取令牌尝试进入停车场
                        System.out.println(Thread.currentThread().getName()+"成功进入停车场");
                        Thread.sleep(new Random().nextInt(10000));//模拟车辆在停车场停留的时间
                        System.out.println(Thread.currentThread().getName()+"驶出停车场");
                        semaphore.release(1);//释放令牌，腾出停车场车位
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            },i+"号车");
            thread.start();
        }
    }
}
