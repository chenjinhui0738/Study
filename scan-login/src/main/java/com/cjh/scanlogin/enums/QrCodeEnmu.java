package com.cjh.scanlogin.enums;

public enum QrCodeEnmu {
    scaned,        //已被APP扫码状态
    login,        //处于登录状态
    cancel,        //已取消登录状态
    notscan;        //尚未被扫码状态
}
