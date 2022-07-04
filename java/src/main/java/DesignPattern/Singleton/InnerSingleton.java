package DesignPattern.Singleton;

/**
 * 静态内部类方式
 */
public class InnerSingleton {
    private static class SingletonHolder {
        private static final InnerSingleton INSTANCE = new InnerSingleton();
    }

    private InnerSingleton() {
    }

    public static final InnerSingleton getInstance() {
        return SingletonHolder.INSTANCE;
    }
}
