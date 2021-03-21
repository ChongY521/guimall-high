package com.gulimall.common.exception;

/**
 * 库存异常 枚举  16: 库存
 * @author aqiang9  2020-08-08
 */
public enum  WareErrorCode implements ErrorCode {
//    SAVE_SPU_BOUND_FAIL (16001 ,"保存积分失败"),
//    SAVE_KEU_RELATION_FAIL(15001 , "保存sku优惠信息失败") ;
    NO_STATUS_UPDATE(16001 , "没有需要更新的状态")
    ;
    WareErrorCode(int code, String msg) {
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
