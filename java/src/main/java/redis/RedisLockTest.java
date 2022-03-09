package redis;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.util.UUID;

public class RedisLockTest {
    private final static int CYCLE_COUNT = 10;//获取锁，循环次数
    public static void main (String[] args) {
        JedisPoolConfig jcon= new JedisPoolConfig();
        JedisPool jp = new JedisPool(jcon, "127.0.0.1",6379);
        Jedis jedis = jp.getResource();
        RedisLock lock = new RedisLock(jedis);
        String requestId = UUID.randomUUID().toString();//请求标识，必须要求唯一
        try {
            boolean status = false;//状态
            //获取不到锁，进行一定次数的重试
            for (int i = 0; i < CYCLE_COUNT; i++) {
                if (lock.lock("redisLock",requestId,500L)) {
                    //加锁后需要执行的逻辑代码
                    System.out.println("执行成功");
                    status = true;
                    break;
                }else{
                    Thread.sleep(1000);
                }
            }
            if(!status){
                //获取不成功时要执行的业务
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            lock.unlock ("redisLock",requestId);
        }
    }
}
