package DesignPattern.Strategy;

/**
 *
 * 策略模式其实就是通过设置核心决策类决定使用哪一个策略，再调用其对应方法
 */
public class StrategyTest {
    public static void main(String[] args) {
        Context context = new Context();
        context.setStrategy(TravelFactory.getTravel("air"));//设置出行方式为飞机
        context.travel();
        context.setStrategy(TravelFactory.getTravel("train"));//设置出行方式为火车
        context.travel();
    }
}
