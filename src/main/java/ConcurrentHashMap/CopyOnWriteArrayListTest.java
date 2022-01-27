package ConcurrentHashMap;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import java.util.concurrent.*;

/*
 *线程安全的 List，在读多写少的场合性能非常好，远远好于 Vector。
 */
public class CopyOnWriteArrayListTest {
    public static void main(String[] args) throws InterruptedException {
        List copyOnWriteArrayList = write(new CopyOnWriteArrayList());//写很慢
        List vector = write(new Vector());//写比较快
        System.out.println(copyOnWriteArrayList.size());
        System.out.println(vector.size());//500000
        read(copyOnWriteArrayList);//读比较快
        read(vector);//读比较慢
//        write:60048
//        write:292
//        read:14
//        read:77
    }

    private static void read(List list) throws InterruptedException {
        ExecutorService service = Executors.newFixedThreadPool(5);
        long begin = System.currentTimeMillis();
        for(int i=0;i <5;i++){
            service.execute(() -> {
                for(int j=0;j <500000;j++){
                    list.get(j);
                }
            });
        }
        service.shutdown();//关闭执行器
        service.awaitTermination(1, TimeUnit.MINUTES);//等待线程执行完后关闭，最多等待1分钟
        System.out.println("read:"+(System.currentTimeMillis()-begin));
    }
    private static List write(List list) throws InterruptedException {
        ExecutorService service = Executors.newFixedThreadPool(10);
        long begin = System.currentTimeMillis();
        for(int i=0;i <10;i++){
            service.execute(() -> {
                for(int j=0;j <500000;j++){
                    list.add(j);
                }
            });
        }
        service.shutdown();//关闭执行器
        service.awaitTermination(1, TimeUnit.MINUTES);//等待线程执行完后关闭，最多等待1分钟
        System.out.println("write:"+(System.currentTimeMillis()-begin));
        return list;
    }
}
