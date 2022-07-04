package BitMap;

import com.google.common.hash.BloomFilter;
import com.google.common.hash.Funnels;
import org.junit.jupiter.api.Test;
import org.redisson.Redisson;
import org.redisson.api.RBloomFilter;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;

import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public class BloomFilterTest {
    private static int size = 1000000;//100万

    /**
     * 基于guava的布隆过滤器
     * 使用布隆过滤器在100万条数据中查找数据
     */
    @Test
    public void Test1() {
        //1.布隆过滤器方式查找数据
        BloomFilter<Integer> bloomFilter = BloomFilter.create(Funnels.integerFunnel(), size);
        for (int i = 0; i < size; i++) {
            bloomFilter.put(i);
        }

        long startTime1 = System.nanoTime(); // 获取开始时间
        //判断这一百万个数中是否包含29999这个数
        if (bloomFilter.mightContain(299999)) {
            System.out.println("命中了");
        }
        long endTime1 = System.nanoTime();   // 获取结束时间
        System.out.println("程序运行时间： " + (endTime1 - startTime1) + "纳秒");
        //2.普通方式查找数据
        List<Integer> list = new ArrayList<Integer>();
        for (int i = 0; i < size; i++) {
            list.add(i);
        }

        long startTime2 = System.nanoTime(); // 获取开始时间
        //判断这一百万个数中是否包含29999这个数
        if (list.contains(299999)) {
            System.out.println("命中了");
        }
        long endTime2 = System.nanoTime();   // 获取结束时间
        System.out.println("程序运行时间： " + (endTime2 - startTime2) + "纳秒");
    }

    /**
     * 布隆过滤器的误判率
     */
    @Test
    public void Test3() {
        BloomFilter<Integer> bloomFilter = BloomFilter.create(Funnels.integerFunnel(), size, 0.00000000000000001);
        for (int i = 0; i < size; i++) {
            bloomFilter.put(i);
        }
        List<Integer> list = new ArrayList<Integer>(1000);
        // 故意取10000个不在过滤器里的值，看看有多少个会被认为在过滤器里
        for (int i = size + 10000; i < size + 20000; i++) {
            if (bloomFilter.mightContain(i)) {
                list.add(i);
            }
        }
        System.out.println("误判的数量：" + list.size());
    }

    /**
     * 基于redission的布隆过滤器
     */
    @Test
    public void Test4() {
        Config config = new Config();
        config.useSingleServer().setAddress("redis://localhost:6379");
        config.useSingleServer().setPassword("123");
        //构造Redisson
        RedissonClient redisson = Redisson.create(config);

        RBloomFilter<String> bloomFilter = redisson.getBloomFilter("phoneList");
        //初始化布隆过滤器：预计元素为100000000L,误差率为3%
        bloomFilter.tryInit(100000000L, 0.03);
        //将号码10086插入到布隆过滤器中
        bloomFilter.add("10086");

        //判断下面号码是否在布隆过滤器中
        System.out.println(bloomFilter.contains("123456"));//false
        System.out.println(bloomFilter.contains("10086"));//true
    }
}
