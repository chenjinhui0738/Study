package DesignPattern.StrategyAndFactory;

public class PayTest {
    public static void main(String[] args) {
        String pay = PayStrategyFactory.get(PayEnum.WeiXin.getCode()).pay();
        System.out.println("支付方式："+pay);
    }
}
