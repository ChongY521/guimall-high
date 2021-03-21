package com.gulimall.ware.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gulimall.common.vo.PageVo;
import com.gulimall.service.utils.PageUtils;
import com.gulimall.ware.entity.PurchaseEntity;
import com.gulimall.ware.vo.PurchaseDoneVo;
import com.gulimall.ware.vo.PurchaseMergeVo;
import com.gulimall.ware.vo.PurchasePageVo;

import java.util.List;

/**
 * 采购信息
 *
 * @author aqiang9
 * @email 2903780002@qq.com
 * @date 2020-06-09 10:07:54
 */
public interface PurchaseService extends IService<PurchaseEntity> {
    PageUtils queryPage(PurchasePageVo params);

    PageUtils queryUnReceivePage(PageVo params);

    void mergePurchase(PurchaseMergeVo mergeVo);

    void receivedPurchase(List<Long> ids);

    void donePurchase(PurchaseDoneVo doneVo);
}

