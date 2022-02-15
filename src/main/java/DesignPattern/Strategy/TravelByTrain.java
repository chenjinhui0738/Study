package DesignPattern.Strategy;

public class TravelByTrain implements TravelInterface {
    @Override
    public void travel() {
        System.out.println("乘坐火车");
    }
}
