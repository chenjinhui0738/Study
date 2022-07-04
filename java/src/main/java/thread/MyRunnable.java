package thread;

public class MyRunnable implements Runnable {
    @Override
    public void run() {
        System.out.println("通过实现Runnable方式启动");
    }
}
