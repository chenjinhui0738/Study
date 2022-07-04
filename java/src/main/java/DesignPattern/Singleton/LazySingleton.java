package DesignPattern.Singleton;

/**
 * 懒汉式
 * 当类加载到jvm时不会马上实例化;调用方法时才进行实例化
 * 必须加锁 synchronized 才能保证单例，但加锁会影响效率。
 */
public class LazySingleton {
    private static LazySingleton instance;

    private LazySingleton() {
    }

    public static synchronized LazySingleton getInstance() {
        if (instance == null) {
            instance = new LazySingleton();
        }
        return instance;
    }
}
