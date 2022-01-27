import java.lang.reflect.Array;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class Test {
    /*public static void main(String[] args) throws ExecutionException, InterruptedException {
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
    }*/
    /*public static void main(String[] args) throws ExecutionException, InterruptedException {
        CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> new String("123"));
        future.whenCompleteAsync((s, throwable) -> System.out.println("complete"));//whenCompleteAsync无结果返回值
        CompletableFuture<String> handleFuture = future.handleAsync((s, throwable) -> "handle");//handleAsync比complete多了可以处理返回结果，async表示可以由其他线程进行异步处理，可以多一个参数指定线程池
        System.out.println(handleFuture.get());
    }*/
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        /*List<?> list = new ArrayList<String>();
        list.remove(0);
        CompletableFuture f = new CompletableFuture();*/
    }
    /*public static int aa(){
        try {
            int a =10/0;
            return 1;
        } catch (Exception e) {
            e.printStackTrace();
            //System.out.println(1);
        }
        return 3;
    }*/
}
