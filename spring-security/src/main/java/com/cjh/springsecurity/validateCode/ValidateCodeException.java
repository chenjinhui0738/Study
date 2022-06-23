package com.cjh.springsecurity.validateCode;

import org.springframework.security.core.AuthenticationException;

/**
 * 验证码异常类
 */
public class ValidateCodeException extends AuthenticationException {
    public ValidateCodeException(String message) {
        super(message);
    }
}
