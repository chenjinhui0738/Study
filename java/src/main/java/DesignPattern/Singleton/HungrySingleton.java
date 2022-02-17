package DesignPattern.Singleton;

/**
 * 饿汉式
 * 类加载时就初始化，浪费内存
 */
public class HungrySingleton {
    private static HungrySingleton instance = new HungrySingleton ();
    private HungrySingleton(){}
    public static HungrySingleton getInstance() {
        return instance;
    }
}
