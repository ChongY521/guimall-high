package com.gulimall.ware.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gulimall.common.constant.WareConstant;
import com.gulimall.common.vo.PageVo;
import com.gulimall.service.utils.PageUtils;
import com.gulimall.service.utils.QueryPage;
import com.gulimall.ware.dao.PurchaseDao;
import com.gulimall.ware.entity.PurchaseDetailEntity;
import com.gulimall.ware.entity.PurchaseEntity;
import com.gulimall.ware.service.PurchaseDetailService;
import com.gulimall.ware.service.PurchaseService;
import com.gulimall.ware.service.WareSkuService;
import com.gulimall.ware.vo.PurchaseDoneVo;
import com.gulimall.ware.vo.PurchaseItemDoneVo;
import com.gulimall.ware.vo.PurchaseMergeVo;
import com.gulimall.ware.vo.PurchasePageVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Service("purchaseService")
public class PurchaseServiceImpl extends ServiceImpl<PurchaseDao, PurchaseEntity> implements PurchaseService {

    @Autowired
    PurchaseDetailService purchaseDetailService;
    @Autowired
    WareSkuService wareSkuService ;


    @Override
    public PageUtils queryPage(PurchasePageVo params) {

        LambdaQueryWrapper<PurchaseEntity> wrapper = new LambdaQueryWrapper<>();

        if (params.getStatus()!=null) {
            wrapper.eq(PurchaseEntity::getStatus , params.getStatus() ) ;
        }
        if (!StringUtils.isEmpty(params.getKey())){
            wrapper.and(w->{
                w.eq(PurchaseEntity::getId , params.getKey() )
                        .or()
                        .like(PurchaseEntity::getAssigneeName , params.getKey() ) ;
            }) ;
        }
        IPage<PurchaseEntity> page = this.page(
                new QueryPage<PurchaseEntity>().getPage(params),
                wrapper
        );
        return new PageUtils(page);
    }

    @Override
    public PageUtils queryUnReceivePage(PageVo params) {
        IPage<PurchaseEntity> page = this.page(
                new QueryPage<PurchaseEntity>().getPage(params),
                new LambdaQueryWrapper<PurchaseEntity>()
                        .eq(PurchaseEntity::getStatus, 0)
                        .or()
                        .eq(PurchaseEntity::getStatus, 1)
        );
        return new PageUtils(page);
    }

    @Override
    public void mergePurchase(PurchaseMergeVo mergeVo) {
        Long purchaseId = mergeVo.getPurchaseId();
        if (purchaseId == null) {
            // ??????
            PurchaseEntity purchaseEntity = new PurchaseEntity();
            purchaseEntity.setStatus(WareConstant.PURCHASE_STATUS_CREATE);
            baseMapper.insert(purchaseEntity);
            purchaseId = purchaseEntity.getId();
        }
        // TODO ?????? ???????????????
        List<Long> items = mergeVo.getItems();
        Long finalPurchaseId = purchaseId;
        List<PurchaseDetailEntity> collect = items.stream().map(e -> {
            PurchaseDetailEntity purchaseDetailEntity = new PurchaseDetailEntity();
            purchaseDetailEntity.setPurchaseId(finalPurchaseId);
            purchaseDetailEntity.setId(e);
            purchaseDetailEntity.setStatus(WareConstant.PURCHASE_STATUS_ASSIGNED);
            return purchaseDetailEntity;
        }).collect(Collectors.toList());
        purchaseDetailService.updateBatchById(collect);

        // TODO ?????????????????????
    }

    /**
     * @param ids ????????????id
     */
    @Transactional
    @Override
    public void receivedPurchase(List<Long> ids) {
        // 1???????????? ??? ???????????????
        List<PurchaseEntity> purchaseEntities = baseMapper.selectBatchIds(ids)
                .stream()
                .filter(purchaseEntity ->
                        purchaseEntity.getStatus() == WareConstant.PURCHASE_STATUS_CREATE ||
                                purchaseEntity.getStatus() == WareConstant.PURCHASE_STATUS_ASSIGNED
                ).peek(purchaseEntity -> purchaseEntity.setStatus(WareConstant.PURCHASE_STATUS_RECEIVE))
                .collect(Collectors.toList());
        //  2 ??? ?????????????????????
        updateBatchById(purchaseEntities) ;
//       3??? ?????????????????????
        purchaseDetailService.updateStatusByPurchaseIds(ids , WareConstant.PURCHASE_STATUS_RECEIVE );
    }

    @Override
    public void donePurchase(PurchaseDoneVo doneVo) {
        // 1????????????????????????
        // 2????????????????????????
        // 3????????? ?????? ?????????


        // ????????????????????????
        boolean flag = true ;

        List<PurchaseItemDoneVo> items = doneVo.getItems();

        List<PurchaseDetailEntity> updatePurchase = new ArrayList<>(items.size() ) ;


        for (PurchaseItemDoneVo item : items) {
            PurchaseDetailEntity purchaseDetailEntity = new PurchaseDetailEntity();
            // ????????????
            if (item.getStatus() == WareConstant.PURCHASE_STATUS_ERROR) {
                purchaseDetailEntity.setStatus(WareConstant.PURCHASE_STATUS_ERROR);
                flag = false  ;
            } else {
                // ?????????
                purchaseDetailEntity.setStatus(WareConstant.PURCHASE_STATUS_FINISH);
                // ??????
                PurchaseDetailEntity entity = purchaseDetailService.getById(item.getItemId());
                //  TODO ?????????????????????  ???????????????
                wareSkuService.addStock(entity.getSkuId(), entity.getWareId(), entity.getSkuNum());
            }
            purchaseDetailEntity.setId( item.getItemId() );
            updatePurchase.add(purchaseDetailEntity) ;
        }
        purchaseDetailService.updateBatchById(updatePurchase) ;
        PurchaseEntity purchaseEntity = new PurchaseEntity();
        purchaseEntity.setId(doneVo.getId());
        purchaseEntity.setStatus(flag ? WareConstant.PURCHASE_STATUS_FINISH : WareConstant.PURCHASE_STATUS_ERROR );
        baseMapper.updateById(purchaseEntity) ;
    }
}
