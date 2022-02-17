package DesignPattern.Strategy;

public class TravelByAir implements TravelInterface {
    @Override
    public void travel() {
        System.out.println("乘坐飞机");
    }
}
