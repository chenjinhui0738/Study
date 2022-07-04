package com.cjh.scanlogin.model;

public class LoginDTO {
    private String userName;
    private String password;
    private String verifyCodeKey;
    private String verifyCode;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getVerifyCodeKey() {
        return verifyCodeKey;
    }

    public void setVerifyCodeKey(String verifyCodeKey) {
        this.verifyCodeKey = verifyCodeKey;
    }

    public String getVerifyCode() {
        return verifyCode;
    }

    public void setVerifyCode(String verifyCode) {
        this.verifyCode = verifyCode;
    }
}
