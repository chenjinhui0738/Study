package com.cjh.scanlogin.model;

import com.cjh.scanlogin.enums.ResponseCode;

public class ResponseResult {
    /**
     * 请求是否成功
     */
    private boolean isSuccess;

    /**
     * 请求响应码，成功时为0
     */
    private int responseCode;

    /**
     * 请求响应码对应描述
     */
    private String responseMsg;

    /**
     * 请求响应的数据对象
     */
    private Object data;

    public ResponseResult() {
    }

    public ResponseResult(boolean isSuccess, int responseCode, String responseMsg) {
        this.isSuccess = isSuccess;
        this.responseCode = responseCode;
        this.responseMsg = responseMsg;
    }

    public ResponseResult(boolean isSuccess, int responseCode, String responseMsg, Object data) {
        this.isSuccess = isSuccess;
        this.responseCode = responseCode;
        this.responseMsg = responseMsg;
        this.data = data;
    }

    public ResponseResult(ResponseCode constant, Object data) {
        this.isSuccess = constant.isSuccess();
        this.responseCode = constant.getResponseCode();
        this.responseMsg = constant.getResponseMsg();
        this.data = data;
    }

    public ResponseResult(ResponseCode constant) {
        this.isSuccess = constant.isSuccess();
        this.responseCode = constant.getResponseCode();
        this.responseMsg = constant.getResponseMsg();
    }

    public static ResponseResult success() {
        return new ResponseResult(true, ResponseCode.SUCCESS.getResponseCode(), "请求成功");
    }

    public static ResponseResult error(int responseCode, String responseMsg) {
        return new ResponseResult(false, responseCode, responseMsg);
    }

    public boolean getIsSuccess() {
        return isSuccess;
    }

    public void setIsSuccess(boolean isSuccess) {
        this.isSuccess = isSuccess;
    }

    public int getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(int responseCode) {
        this.responseCode = responseCode;
    }

    public String getResponseMsg() {
        return responseMsg;
    }

    public void setResponseMsg(String responseMsg) {
        this.responseMsg = responseMsg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

}
