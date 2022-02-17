package thread;

public class MyThread extends Thread {
    @Override
    public void run() {
        System.out.println("通过继承Thread方式启动");
    }
}
