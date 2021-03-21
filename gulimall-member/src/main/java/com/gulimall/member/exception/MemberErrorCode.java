package com.gulimall.member.exception;

import com.gulimall.common.exception.ErrorCode;

/**
 *  19: 会员服务
 * @author aqiang9  2020-09-02
 */
public enum  MemberErrorCode  implements ErrorCode {
    PHONE_EXIST_EXCEPTION (19001 ,"手机号已存在"),
    USERNAME_EXIST_EXCEPTION(19002 , "用户名已存在") ,
    LOGIN_PASSWORD_INVALID_EXCEPTION(19003 , "账号或密码错误")
    ;
    MemberErrorCode(int code, String msg) {
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
