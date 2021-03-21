package com.gulimall.common.constant;

/**
 * 库存系统常量
 * @author aqiang9  2020-08-08
 */
public interface WareConstant {
    /**
     * 采购状态 - 新建
     */
    int PURCHASE_STATUS_CREATE = 0 ;
    /**
     * 采购状态 - 已分配
     */
    int PURCHASE_STATUS_ASSIGNED = 1 ;
    /**
     * 采购状态 - 已领取
     */
    int PURCHASE_STATUS_RECEIVE = 2;
    /**
     * 采购状态 - 已完成
     */
    int PURCHASE_STATUS_FINISH = 3;
    /**
     * 采购状态 - 有异常
     */
    int PURCHASE_STATUS_ERROR = 4 ;
}
