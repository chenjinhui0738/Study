package DesignPattern.AbstractFactory;

import DesignPattern.Factory.HuaWei;
import DesignPattern.Factory.Phone;
import DesignPattern.Factory.XiaoMi;

public class PhoneFactory extends AbstractFactory {
    @Override
    public Phone createPhone(String brand) {
        if ("HuaWei".equals(brand)) {
            return new HuaWei();
        } else if ("XiaoMi".equals(brand)) {
            return new XiaoMi();
        } else {
            return null;
        }
    }

    @Override
    public Computer createComputer(String brand) {
        return null;
    }
}
