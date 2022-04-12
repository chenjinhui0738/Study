package CompletableFuture;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static java.util.stream.Collectors.toList;

/**
 * 如果你的任务是计算密集型的，并且没有I/O操作的话，那么推荐你选择Stream的并行流，实现简单并行效率也是最高的
 *
 * 如果你的任务是有频繁的I/O或者网络连接等操作，那么推荐使用CompletableFuture，采用自定义线程池的方式，根据服务器的情况设置线程池的大小，尽可能的让CPU忙碌起来
 *
 * thenApply、thenApplyAsync: 假如任务执行完成后，还需要后续的操作，比如返回结果的解析等等；可以通过这两个方法来完成
 *
 * thenCompose、thenComposeAsync: 允许你对两个异步操作进行流水线的操作，当第一个操作完成后，将其结果传入到第二个操作中
 *
 * thenCombine、thenCombineAsync：允许你把两个异步的操作整合；比如把第一个和第二个操作返回的结果做字符串的连接操作
 */
public class CompletableFutureTest {
    //1.CompletableFuture异步调用
    @Test
    public static void Test1(){
        long start = System.currentTimeMillis();
        List<RemoteLoader> remoteLoaders = Arrays.asList(
                new CustomerInfoService(),
                new LearnRecordService(),
                new LabelService(),
                new OrderService(),
                new WatchRecordService());
        //定义一个容量为50的线程池
        ExecutorService executorService = Executors.newFixedThreadPool(Math.min(remoteLoaders.size(), 50));
        //模拟异步调用并获取返回结果
        List<CompletableFuture<String>> completableFutures = remoteLoaders
                .stream()
                .map(loader -> CompletableFuture.supplyAsync(loader::load, executorService).thenApplyAsync(s -> s+"测试"))
                .collect(toList());
        //将返回结果拼接为想要的list格式
        List<String> customerDetail = completableFutures
                .stream()
                .map(CompletableFuture::join)
                .collect(toList());
        System.out.println(customerDetail);
        long end = System.currentTimeMillis();
        System.out.println("总共花费时间:" + (end - start));
    }
    //2.parallelStream方式
    @Test
    public static void Test2(){
        long start = System.currentTimeMillis();
        List<RemoteLoader> remoteLoaders = Arrays.asList(
                new CustomerInfoService(),
                new LearnRecordService(),
                new LabelService(),
                new OrderService(),
                new WatchRecordService());
        List<String> customerDetail = remoteLoaders.parallelStream().map(RemoteLoader::load).collect(toList());
        System.out.println(customerDetail);
        long end = System.currentTimeMillis();
        System.out.println("总共花费时间:" + (end - start));
    }
    //3.
    @Test
    public static void Test3() throws ExecutionException, InterruptedException {
        CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> new String("123"));
        future.whenCompleteAsync((s, throwable) -> System.out.println("complete"));//whenCompleteAsync无结果返回值
        CompletableFuture<String> handleFuture = future.handleAsync((s, throwable) -> "handle");//handleAsync比complete多了可以处理返回结果，async表示可以由其他线程进行异步处理，可以多一个参数指定线程池
        System.out.println(handleFuture.get());
    }
    //4.
    @Test
    public static void Test4() throws ExecutionException, InterruptedException {
        CompletableFuture<Integer> future = CompletableFuture.supplyAsync(() -> {
            int i= 10/0;
            return new Random().nextInt(10);
        }).handleAsync((param, throwable) -> {
            int result = -1;
            if(throwable==null){
                result = param * 2;
            }else{
                System.out.println(throwable.getMessage());
            }
            return result;
        });
        System.out.println(future.get());
    }

    public static class CustomerInfoService implements RemoteLoader {
        public String load() {
            this.delay();
            return "基本信息";
        }
    }

    public static class LearnRecordService implements RemoteLoader {
        public String load() {
            this.delay();
            return "学习信息";
        }
    }
    public static class WatchRecordService implements RemoteLoader {
        @Override
        public String load() {
            this.delay();
            return "观看记录";
        }
    }

    public static class OrderService implements RemoteLoader {
        @Override
        public String load() {
            this.delay();
            return "订单信息";
        }
    }

    public static class LabelService implements RemoteLoader {
        @Override
        public String load() {
            this.delay();
            return "标签信息";
        }
    }
}
