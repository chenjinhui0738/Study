package com.cjh.SpringSecurityOauth2.validateCode.smsCode;

import org.apache.commons.lang3.RandomStringUtils;

public class SmsCodeUtil {
    public static SmsCode createSMSCode() {
        String code = RandomStringUtils.randomNumeric(6);
        return new SmsCode(code, 60);
    }
}
