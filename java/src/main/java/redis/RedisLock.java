package redis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;

import java.util.Collections;

/**
 * redis的分布式事务锁，当部署两个节点时，如果发生并发会导致数据不一致。例如先查库存再进行库存删减的功能，那么
 * 有可能发生请求到第一个节点上时查询库存到删减完，第二个节点请求查询库存发现>0，因为再执行这一步的时候第一个请求还未删减，再
 * 删减就发生超卖现象。传统的syconize和rock只能锁到单个jvm进程，当有多台机器时就无法生效，无法对多个机器内的程序进行同步执行，
 * 这个时候就需要分布式锁
 * <p>
 * Redis 实现的分布式锁以 Redis setnx 为中心实现,setnx 是 Red 写入操作命令,具体语法为
 * setnx(key val)在且仅在 key不存在时,则插入一个(key val) 字符串,返回1
 * key 存在,则什么都不做，返回0.
 */
public class RedisLock {
    private final static Logger logger = LoggerFactory.getLogger(RedisLock.class);
    private static final String LOCK_SUCCESS = "OK";
    private static final Long RELEASE_SUCCESS = 1L;
    private Jedis jedis;

    public RedisLock(Jedis jedis) {
        this.jedis = jedis;
    }

    /**
     * 获取锁
     *
     * @param key     锁名称
     * @param value   客户端的请求标识，必须全局唯一，可以使用requestId
     * @param timeOut 设置锁的过期时间，防止死锁
     * @return
     */
    public synchronized boolean lock(String key, String value, Long timeOut) {
        //设置锁,key不存在时插入，并设置过期时间，存在则不插入
        String result = jedis.set(key, value, "NX", "PX", timeOut);
        if (LOCK_SUCCESS.equals(result)) {
            return true;
        }
        return false;
    }

    /**
     * 释放锁
     *
     * @param key   锁名称
     * @param value 客户端的请求标识，必须全局唯一，可以使用requestId
     * @return
     */
    public synchronized boolean unlock(String key, String value) {
        String luaScript = "if redis.call('get',KEYS[1]) == ARGV[1] then return redis.call('del',KEYS[1]) else  return 0 end";

        Object var2 = jedis.eval(luaScript, Collections.singletonList(key), Collections.singletonList(value));
        if (RELEASE_SUCCESS == var2) {
            return true;
        }
        return false;
    }
}
