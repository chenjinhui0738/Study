package DesignPattern.Strategy;

/**
 * 策略核心类
 * 通过策略设置核心类决定使用哪种策略
 */
public class Context {
    private TravelInterface strategy;

    public TravelInterface getStrategy() {
        return strategy;
    }

    public void setStrategy(TravelInterface strategy) {
        this.strategy = strategy;
    }

    public void travel(){
        strategy.travel();
    }
}
