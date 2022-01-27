package AQS;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * CyclicBarrier计数器和countdownlatch一样，到达某个数量时往后执行，但是可重复使用，调用reset即可
 *
 */
public class CyclicBarrierTest {
    public static void main(String[] args) {
        ExecutorService service = Executors.newCachedThreadPool();
        CyclicBarrier cyclicBarrier = new CyclicBarrier(3, new Runnable() {
            @Override
            public void run(){
                //2、这是三个人都到齐之后会执行的代码
                System.out.println("三个人都已到达会议室");
            }
        });
        for (int i = 0; i < 5; i++) {
            Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    try {
                        //4、模拟每人到会议室所需时间
                        Thread.sleep((long) (Math.random()*5000));
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println("第"+Thread.currentThread().getName()+"个人到达会议室");
                    try {
                        //5、等待其他人到会议室
                        cyclicBarrier.await();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (BrokenBarrierException e) {
                        e.printStackTrace();
                    }
                    System.out.println(Thread.currentThread().getName()+"开始开会");
                }
            };
            service.execute(runnable);
        }
    }
}
