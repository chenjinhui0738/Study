package com.cjh.easyexcel.vo;

public class ResultVO<V>{
    private String msg;

    public String getMsg() {
        return msg;
    }

    public ResultVO(String msg) {
        this.msg = msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public static ResultVO getSuccess(String msg) {
        return new ResultVO(msg);
    }
}
