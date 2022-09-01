package DesignPattern.StrategyAndFactory;

public enum PayEnum {
    WeiXin("WeiXin","微信支付"),JingDong("JingDong","京东支付");
    private String code;
    private String name;

    PayEnum(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
