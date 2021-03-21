package com.gulimall.ware.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gulimall.common.constant.WareConstant;
import com.gulimall.service.utils.PageUtils;
import com.gulimall.ware.entity.PurchaseDetailEntity;
import com.gulimall.ware.vo.PurchaseDetailPageVo;

import java.util.List;

/**
 * 
 *
 * @author aqiang9
 * @email 2903780002@qq.com
 * @date 2020-06-09 10:07:54
 */
public interface PurchaseDetailService extends IService<PurchaseDetailEntity> {

    PageUtils queryPage(PurchaseDetailPageVo params);

    /**
     * 更新 采购单的状态
     * @param purchaseIds purchaseIds 的id
     * @param purchaseStatus 转态 值
     * @see WareConstant
     *
     */
    void updateStatusByPurchaseIds(List<Long> purchaseIds, int purchaseStatus);
}

