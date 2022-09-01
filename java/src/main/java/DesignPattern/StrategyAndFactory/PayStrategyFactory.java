package DesignPattern.StrategyAndFactory;

import com.google.common.collect.Maps;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * 支付策略工厂
 */
public class PayStrategyFactory {
    private static Map<String, IPay> PAY_REGISTERS = new HashMap<>();

    static{
        PAY_REGISTERS.put(PayEnum.WeiXin.getCode(),new WeiXinPay());
        PAY_REGISTERS.put(PayEnum.JingDong.getCode(),new JingDongPay());
    }
    public static IPay get(String code){
        return PAY_REGISTERS.get(code);
    }
}
