package com.gulimall.common.exception;

/**
 * 15: 优惠服务
 * @author aqiang9  2020-08-02
 */
public enum  CouponErrorCode implements ErrorCode {
    SAVE_SPU_BOUND_FAIL (15001 ,"保存积分失败"),
    SAVE_KEU_RELATION_FAIL(15001 , "保存sku优惠信息失败") ;
    ;
    CouponErrorCode(int code, String msg) {
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
