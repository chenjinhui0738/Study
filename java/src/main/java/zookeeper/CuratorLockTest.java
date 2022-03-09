package zookeeper;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.apache.curator.retry.RetryNTimes;

import java.util.concurrent.TimeUnit;


/**
 * curator分布式锁测试
 */
public class CuratorLockTest implements Runnable {

    //zookeeper的地址
    private static final String ZK_ADDRESS = "127.0.0.1:2181";

    private static final String ZK_LOCK_PATH = "/zkLock";

    static CuratorFramework client = null;

    static {
        // 连接ZK，如果连接失败，设置每5000毫秒重试一次，最多重试10次
        client = CuratorFrameworkFactory.newClient(ZK_ADDRESS,
                new RetryNTimes(10, 5000));
        client.start();
    }

    private static void curatorLockTest() {

        InterProcessMutex lock = new InterProcessMutex(client, ZK_LOCK_PATH);
        try {
            if (lock.acquire(6 * 1000, TimeUnit.SECONDS)) {
                System.out.println("====== " + Thread.currentThread().getName() + " 抢到了锁 ======");
                //执行业务逻辑

                System.out.println(Thread.currentThread().getName() + "任务执行完毕");
            }
        } catch (Exception e) {
            System.out.println("业务异常");
        } finally {
            try {
                lock.release();
            } catch (Exception e) {
                System.out.println("锁释放异常");
            }
        }
    }

    public static void main(String[] args) {
        // 用两个线程，模拟两个客户端
        // 每个线程创建各自的zookeeper连接对象
        new Thread(new CuratorLockTest()).start();

        new Thread(new CuratorLockTest()).start();
    }

    @Override
    public void run() {
        curatorLockTest();
    }
}