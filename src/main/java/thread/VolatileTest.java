package thread;

public class VolatileTest extends Thread {

    public volatile static int stop = 1;//任务是否停止,普通变量

    public static void main(String[] args) throws Exception {
        Thread thread1 = new Thread(() -> {
            System.out.println(stop);
            while (stop==1) { //stop=false，不满足停止条件，继续执行
                //do someting
            }
            System.out.println(stop);
        });
        thread1.start();
        Thread.sleep(100);//保证主线程修改stop=true，在子线程启动后执行。
        stop = 2; //true
    }
}