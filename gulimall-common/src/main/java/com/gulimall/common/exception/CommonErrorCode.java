package com.gulimall.common.exception;

/**
 * 通用异常枚举
 *  以 10 开头
 * @author aqiang9  2020-07-28
 */
public enum CommonErrorCode implements ErrorCode {
    SYSTEM_UNKNOWN_EXCEPTION( "系统未知异常",10000),
    VALID_FAIL_EXCEPTION("参数格式校验失败",10001);

    CommonErrorCode(String msg,int code) {
        this.code = code;
        this.msg = msg;
    }

    private final int code;
    private final String msg;

    @Override
    public int getCode() {
        return code;
    }
    @Override
    public String getMsg() {
        return msg;
    }
}
