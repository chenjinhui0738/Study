package lock.ReadWriteLock;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 读写锁
 *
 *
 */


public class ReadWriteLockTest {
    static Map<String, Object> cache = new HashMap<String, Object>();
    ReentrantReadWriteLock rwlock = new ReentrantReadWriteLock();
    Lock readLock = rwlock.readLock();
    Lock writeLock = rwlock.writeLock();
    public static void main(String[] args) {
        Test1();
    }

    private static void Test1() {
        for (int i = 0; i <100 ; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    cache.put("1",5);
                }
            }).start();
        }
    }

    //读数据时加锁
    public Object get(String key) {
        readLock.lock();
        try {
            return cache.get (key);
        }finally {
            readLock.unlock();
        }
    }
    //写数据时加锁
    public Object put (String key,Object value){
        writeLock.lock();
        try {
            return cache.put (key,value);
        }finally{
            writeLock.unlock();
        }
    }

}
