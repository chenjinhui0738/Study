package DesignPattern.AbstractFactory;

import DesignPattern.Factory.Phone;

public class AbstractFactoryTest {
    public static void main(String[] args) {
        AbstractFactory phoneFactory =new PhoneFactory();
        Phone XiaoMi = phoneFactory.createPhone ( "XiaoMi");
        Phone HuaWei = phoneFactory.createPhone( "HuaWei");
        System.out.println(XiaoMi.call());
        System.out.println(HuaWei.call());
        AbstractFactory computerFactory =new ComputerFactory();
        Computer LianXiang = computerFactory.createComputer ("LianXiang");
        Computer HuiPu = computerFactory.createComputer ("HuiPu");
        System.out.println(LianXiang.internet());
        System.out.println(HuiPu.internet());

    }
}
