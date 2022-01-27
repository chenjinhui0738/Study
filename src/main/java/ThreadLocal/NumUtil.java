package ThreadLocal;

import java.util.concurrent.TimeUnit;

public class NumUtil {
    public static int addNum = 0;
    private static final ThreadLocal<Integer> threadLocal = new ThreadLocal();
    public static int add10(int num) {
        addNum = num;
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return addNum + 10;
    }
    public static int add10ThreadLocal(int num) {
        threadLocal.set(num);
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        threadLocal.set(threadLocal.get() + 10);
        return threadLocal.get();
    }
}
