package com.gulimall.auth.exception;

import com.gulimall.common.exception.ErrorCode;

/**
 * 认证服务异常处理类
 * @author aqiang9  2020-09-02
 */
public enum AuthErrorCode implements ErrorCode {
    SMS_FREQUENCY_TOO_HIGH_ERROR(18001 ,"验证码获取频率太高,请稍后重试")
    ;
    AuthErrorCode(int code, String msg) {
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