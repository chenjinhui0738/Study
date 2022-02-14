package DesignPattern.AbstractFactory;

import DesignPattern.Factory.Phone;

public class ComputerFactory extends AbstractFactory{

    @Override
    public Phone createPhone(String brand) {
        return null;
    }

    @Override
    public Computer createComputer(String brand) {
        if("LianXiang".equals(brand)){
            return new LianXiang();
        }else if("HuiPu".equals(brand)){
            return new HuiPu();
        }else{
            return null;
        }
    }
}
