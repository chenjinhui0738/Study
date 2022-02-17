package DesignPattern.Strategy;

/**
 * 设置出行方式工厂类
 */
public class TravelFactory {
    public static TravelInterface getTravel(String type){
        if(TravelEnum.air.getType().equals(type)){
            return new TravelByAir();
        }else if(TravelEnum.train.getType().equals(type)){
            return new TravelByTrain();
        }else{
            return null;
        }
    }
}
