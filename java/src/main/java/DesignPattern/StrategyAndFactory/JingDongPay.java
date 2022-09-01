package DesignPattern.StrategyAndFactory;

public class JingDongPay implements IPay{
    @Override
    public String pay() {
        System.out.println("使用京东支付中....");
        System.out.println("支付完成！");
        return "京东支付";
    }
}
