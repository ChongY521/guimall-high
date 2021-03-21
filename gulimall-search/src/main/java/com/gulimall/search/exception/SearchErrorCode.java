package com.gulimall.search.exception;

import com.gulimall.common.exception.ErrorCode;

/**
 * 17: 搜索
 * @author aqiang9  2020-08-15
 */
public enum SearchErrorCode implements ErrorCode {
    PRODUCT_UP_ERROR(17001 ,"商品上架错误")
    ;

    SearchErrorCode(int code, String msg) {
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
