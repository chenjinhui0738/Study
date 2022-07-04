package DesignPattern.Singleton;

/**
 * 双检锁/双重校验锁（DCL，即 double-checked locking）
 * 采用双锁机制，安全且在多线程情况下能保持高性能。
 */
public class DCLSingleton {
    private volatile static DCLSingleton singleton;

    private DCLSingleton() {
    }

    public static DCLSingleton getSingleton() {
        if (singleton == null) {
            synchronized (DCLSingleton.class) {
                if (singleton == null) {
                    singleton = new DCLSingleton();
                }
            }
        }
        return singleton;
    }
}
