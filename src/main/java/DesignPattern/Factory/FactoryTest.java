package DesignPattern.Factory;

/**
 * 工厂设计模式
 *其实就是根据不同参数类型，对应的工厂类生成不同的实现类，返回类型却是为接口，对使用者来说等于屏蔽了对象实例化的过程
 */
public class FactoryTest {
    public static void main(String[] args) {
        Phone huaWei = PhoneFactory.createPhone("HuaWei");
        System.out.println(huaWei.brand());
    }
}
