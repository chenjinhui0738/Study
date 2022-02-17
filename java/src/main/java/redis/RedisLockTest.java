package redis;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.util.UUID;

public class RedisLockTest {
    public static void main (String[] args) {
        JedisPoolConfig jcon= new JedisPoolConfig();
        JedisPool jp = new JedisPool(jcon, "127.0.0.1",6379);
        Jedis jedis = jp.getResource();
        RedisLock lock = new RedisLock(jedis);
        String requestId = UUID.randomUUID().toString();//请求标识，必须要求唯一
        try {
            if (lock.lock("redisLock",requestId,500L)) {
                //加锁后需要执行的逻辑代码
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            lock.unlock ("redisLock",requestId);
        }
    }
}
