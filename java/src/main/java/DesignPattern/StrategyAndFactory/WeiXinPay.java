package DesignPattern.StrategyAndFactory;

public class WeiXinPay implements IPay{
    @Override
    public String pay() {
        System.out.println("使用微信支付中....");
        System.out.println("支付完成！");
        return "微信支付";
    }
}
