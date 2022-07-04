package DesignPattern.AbstractFactory;

import DesignPattern.Factory.Phone;

public abstract class AbstractFactory {
    //生成手机
    public abstract Phone createPhone(String brand);

    //生成电脑
    public abstract Computer createComputer(String brand);
}
