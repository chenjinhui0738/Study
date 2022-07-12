package com.cjh.spring.Async;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.ThreadPoolExecutor;

/**
 * 默认情况下的异步线程池配置使得线程不能被重用，每次调用异步方法都会新建一个线程，我们可以自己定义异步线程池来优化。
 */
@Configuration
public class AsyncPoolConfig {

    @Bean
    public ThreadPoolTaskExecutor asyncThreadPoolTaskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(20);//线程池核心线程的数量，默认值为1（这就是默认情况下的异步线程池配置使得线程不能被重用的原因）
        executor.setMaxPoolSize(200);//线程池维护的线程的最大数量，只有当核心线程都被用完并且缓冲队列满后，才会开始申超过请核心线程数的线程，默认值为Integer.MAX_VALUE。
        executor.setQueueCapacity(25);//缓冲队列。
        executor.setKeepAliveSeconds(200);//超出核心线程数外的线程在空闲时候的最大存活时间，默认为60秒。
        executor.setThreadNamePrefix("asyncThread");//线程名前缀。
        executor.setWaitForTasksToCompleteOnShutdown(true);//是否等待所有线程执行完毕才关闭线程池，默认值为false。
        executor.setAwaitTerminationSeconds(60);//waitForTasksToCompleteOnShutdown的等待的时长，默认值为0，即不等待。
        //当没有线程可以被使用时的处理策略（拒绝任务），默认策略为abortPolicy，包含下面四种策略：
        //1.callerRunsPolicy：用于被拒绝任务的处理程序，它直接在 execute 方法的调用线程中运行被拒绝的任务；如果执行程序已关闭，则会丢弃该任务。
        //2.abortPolicy：直接抛出java.util.concurrent.RejectedExecutionException异常。
        //3.discardOldestPolicy：当线程池中的数量等于最大线程数时、抛弃线程池中最后一个要执行的任务，并执行新传入的任务。
        //4.discardPolicy：当线程池中的数量等于最大线程数时，不做任何动作。
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());

        executor.initialize();
        return executor;
    }
}
