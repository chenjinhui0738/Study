package DesignPattern.Factory;

public class HuaWei implements Phone {
    @Override
    public String brand() {
        return "华为手机";
    }

    @Override
    public String call() {
        return "使用华为手机打电话";
    }
}
