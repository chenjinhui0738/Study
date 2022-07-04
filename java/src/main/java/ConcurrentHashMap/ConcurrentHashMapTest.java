package ConcurrentHashMap;

import java.util.Collections;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;
import java.util.concurrent.*;

/**
 * ConcurrentHashMap是线程安全的map类,结合了hashmap和hashtale的优点
 */
public class ConcurrentHashMapTest {
    public static void main(String[] args) throws InterruptedException {
        Test1(new ConcurrentHashMap());//最快
        Test1(new Hashtable());
        Test1(Collections.synchronizedMap(new HashMap<>()));


    }

    private static void Test1(Map<String, Object> map) throws InterruptedException {
        ExecutorService service = Executors.newFixedThreadPool(5);
        long begin = System.currentTimeMillis();
        for (int i = 0; i < 5; i++) {
            service.execute(() -> {
                for (int j = 0; j < 5000000; j++) {
                    int value = ThreadLocalRandom
                            .current()
                            .nextInt(10000);
                    String key = String.valueOf(value);
                    map.put(key, value);
                    map.get(key);
                }
            });
        }
        service.shutdown();//关闭执行器
        service.awaitTermination(1, TimeUnit.MINUTES);//等待线程执行完后关闭，最多等待1分钟
        System.out.println(System.currentTimeMillis() - begin);
    }
}
