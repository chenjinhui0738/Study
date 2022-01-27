package BlockingQueue;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.*;

/**
 * BlockingQueue一般用于排队的列子，比如多线程的批量下载或者上传，有效控制队列高效执行，
 * queue里有数据的话，get会返回。
 *
 * queue里没数据的话，get会block住，直到有其他线程add数据进来。
 */
public class BlockingQueueTest {
    private static BlockingQueue<Integer> blockingQueue = new LinkedBlockingQueue<>(10);
    public static void main(String[] args) throws InterruptedException {
        //Test1();
        Test2();

    }

    private static void Test2() throws InterruptedException {
        new Thread(() -> {
            for(int i=0;i<10;i++){
                blockingQueue.offer(i);
            }
        }).start();
        new Thread(() -> {
            for(int i=0;i<20;i++){
                try {
                    System.out.println(blockingQueue.take());//队列为空会阻塞当前线程
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
        Thread.sleep(2000);
        new Thread(() -> {
            blockingQueue.offer(1);
            blockingQueue.offer(2);
            blockingQueue.offer(3);
            //blockingQueue.offer(20);
        }).start();
        //先向队列放入10个数，又取20个数，为空时阻塞了2s,放入3个数后又继续取，直到为空阻塞
    }

    private static void Test1() {
        ScheduledExecutorService product = Executors.newScheduledThreadPool(1);
        Random random = new Random();
        product.scheduleAtFixedRate(() -> {
            int value = random.nextInt(101);
            try{
                blockingQueue.offer(value);  //offer()方法就是网队列的尾部设置值
            }catch(Exception ex){
                ex.printStackTrace();
            }
        }, 0, 200, TimeUnit.MILLISECONDS);  //每100毫秒执行线程

        new Thread(() -> {
            while(true){
                try {
                    Thread.sleep(2000);
                    System.out.println("开始取值");
                    List<Integer> list = new LinkedList<>();
                    blockingQueue.drainTo(list);  //drainTo()将队列中的值全部从队列中移除，并赋值给对应集合
                    list.forEach(System.out::println);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
