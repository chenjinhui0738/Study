package DesignPattern.Factory;

public class XiaoMi implements Phone {
    @Override
    public String brand() {
        return "小米手机";
    }

    @Override
    public String call() {
        return "使用小米手机打电话";
    }
}
