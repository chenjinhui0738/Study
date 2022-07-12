package annotation;

public class User {
    @UserName("张三")
    private String userName;
    @UserSex(userSex = UserSex.Sex.MAN)
    private String sex;
    @UserAccount(id = 1, account = "zhangsan", password = "123456")
    private String accountInfo;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getAccountInfo() {
        return accountInfo;
    }

    public void setAccountInfo(String accountInfo) {
        this.accountInfo = accountInfo;
    }
}
