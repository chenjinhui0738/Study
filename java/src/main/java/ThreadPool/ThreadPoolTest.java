package ThreadPool;


import org.junit.Test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * 线程池运行原理：
 * 1.如果此时线程池中的数量小于corePoolSize，即使线程池中的线程都处于空闲状态，也要创建新的线程来处理被添加的任务。
 * 2.如果此时线程池中的数量等于corePoolSize，但是缓冲队列workQueue未满，那么任务被放入缓冲队列。
 * 3.如果此时线程池中的数量大于等于corePoolSize，缓冲队列workQueue满，并且线程池中的数量小于maximumPoolSize，建新的线程来处理被添加的任务。
 * 4.如果此时线程池中的数量大于corePoolSize，缓冲队列workQueue满，并且线程池中的数量等于maximumPoolSize，那么通过 handler所指定的策略来处理此任务。
 * 5.当线程池中的线程数量大于 corePoolSize时，如果某线程空闲时间超过keepAliveTime，线程将被终止。这样，线程池可以动态的调整池中的线程数。
 * 总结即：处理任务判断的优先级为 核心线程corePoolSize、任务队列workQueue、最大线程maximumPoolSize，如果三者都满了，使用handler处理被拒绝的任务。
 * <p>
 * 线程池参数解释：
 * ThreadPoolExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue, ThreadFactory threadFactory, RejectedExecutionHandler handler)
 * corePoolSize	核心线程数量，线程池维护线程的最少数量
 * maximumPoolSize	线程池维护线程的最大数量
 * keepAliveTime	线程池除核心线程外的其他线程的最长空闲时间，超过该时间的空闲线程会被销毁
 * unit	keepAliveTime的单位，TimeUnit中的几个静态属性：NANOSECONDS、MICROSECONDS、MILLISECONDS、SECONDS
 * workQueue	线程池所使用的任务缓冲队列
 * threadFactory	线程工厂，用于创建线程，一般用默认的即可
 * handler	线程池对拒绝任务的处理策略
 * <p>
 * 拒绝策略：
 * 当线程池任务处理不过来的时候（什么时候认为处理不过来后面描述），可以通过handler指定的策略进行处理，ThreadPoolExecutor提供了四种策略：
 * 1.ThreadPoolExecutor.AbortPolicy:丢弃任务并抛出RejectedExecutionException异常；也是默认的处理方式。
 * 2.ThreadPoolExecutor.DiscardPolicy：丢弃任务，但是不抛出异常。
 * 3.ThreadPoolExecutor.DiscardOldestPolicy：丢弃队列最前面的任务，然后重新尝试执行任务（重复此过程）
 * 4.ThreadPoolExecutor.CallerRunsPolicy：由调用线程处理该任务
 * 可以通过实现RejectedExecutionHandler接口自定义处理方式。
 * <p>
 * 线程使用队列：
 * 1.ArrayBlockingQueue： 这是一个由数组实现的容量固定的有界阻塞队列.
 * 2.SynchronousQueue： 没有容量，不能缓存数据；每个put必须等待一个take; offer()的时候如果没有另一个线程在poll()或者take()的话返回false。
 * 3.LinkedBlockingQueue： 这是一个由单链表实现的默认无界的阻塞队列。LinkedBlockingQueue提供了一个可选有界的构造函数，而在未指明容量时，容量默认为Integer.MAX_VALUE。
 */
public class ThreadPoolTest {

    /**
     * 4种线程池
     */
    @Test
    public void Test1() {
        //1.创建一个定长线程池，可控制线程最大并发数，超出的线程会在队列中等待。
        //内部实现：new ThreadPoolExecutor(nThreads, nThreads,0L,TimeUnit.MILLISECONDS,new LinkedBlockingQueue());
        ExecutorService fixedThreadPool = Executors.newFixedThreadPool(5);

        //2.创建一个可缓存线程池，如果线程池长度超过处理需要，可灵活回收空闲线程，若无可回收，则新建线程
        //内部实现：new ThreadPoolExecutor(0,Integer.MAX_VALUE,60L, TimeUnit.SECONDS,new SynchronousQueue());
        ExecutorService cachedThreadPool = Executors.newCachedThreadPool();

        //3.创建一个单线程化的线程池，它只会用唯一的工作线程来执行任务，保证所有任务按照顺序执行。
        //内部实现：new ThreadPoolExecutor(1,1,0L,TimeUnit.MILLISECONDS,new LinkedBlockingQueue())
        ExecutorService singleThreadPool = Executors.newSingleThreadExecutor();

        //4.创建一个定长线程池，支持定时及周期性任务执行。
        //内部实现：new ScheduledThreadPoolExecutor(corePoolSize)
        ScheduledExecutorService scheduledThreadPool = Executors.newScheduledThreadPool(5);
        //延迟3秒执行
        scheduledThreadPool.schedule(new Runnable() {
            @Override
            public void run() {
                System.out.println("定时/周期线程池:延迟3秒执行");
            }
        }, 3, TimeUnit.SECONDS);
        //延迟3秒。每隔1秒执行
        scheduledThreadPool.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                System.out.println("定时/周期线程池:延迟3秒,每隔1秒执行");
            }
        }, 3, 1, TimeUnit.SECONDS);

    }

    @Test
    public void Test2() {
    }
}
