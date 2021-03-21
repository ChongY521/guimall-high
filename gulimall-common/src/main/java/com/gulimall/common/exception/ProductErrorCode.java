package com.gulimall.common.exception;

/**
 * product 枚举类
 *
 * @author aqiang9  2020-07-28
 */
public enum ProductErrorCode implements ErrorCode {

    ;
    ProductErrorCode(int code, String msg) {
        this.code = code ;
        this.msg = msg ;
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
