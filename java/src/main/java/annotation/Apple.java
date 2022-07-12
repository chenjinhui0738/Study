package annotation;

public class Apple {
    //2:使用注解接口
    @FruitProvider(id = 1, name = "陕西红富士集团", address = "陕西省西安市")
    private String appleProvider;

    public void setAppleProvider(String appleProvider) {
        this.appleProvider = appleProvider;
    }

    public String getAppleProvider() {
        return appleProvider;
    }
}
