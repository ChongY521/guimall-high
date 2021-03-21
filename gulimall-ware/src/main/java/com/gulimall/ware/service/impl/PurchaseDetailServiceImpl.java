package com.gulimall.ware.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gulimall.common.exception.BusinessException;
import com.gulimall.common.exception.WareErrorCode;
import com.gulimall.service.utils.PageUtils;
import com.gulimall.service.utils.QueryPage;
import com.gulimall.ware.dao.PurchaseDetailDao;
import com.gulimall.ware.entity.PurchaseDetailEntity;
import com.gulimall.ware.service.PurchaseDetailService;
import com.gulimall.ware.vo.PurchaseDetailPageVo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;


@Service("purchaseDetailService")
public class PurchaseDetailServiceImpl extends ServiceImpl<PurchaseDetailDao, PurchaseDetailEntity> implements PurchaseDetailService {

    @Override
    public PageUtils queryPage(PurchaseDetailPageVo params) {
        LambdaQueryWrapper<PurchaseDetailEntity> wapper = new LambdaQueryWrapper<>();

        if (params.getWareId() != null && params.getWareId() > 0) {
            wapper.eq(PurchaseDetailEntity::getWareId, params.getWareId());
        }
        if (params.getStatus() != null) {
            wapper.eq(PurchaseDetailEntity::getStatus, params.getStatus());
        }
        String key = params.getKey();

        if (!StringUtils.isEmpty(key)) {
            wapper.and(w -> {
                w.eq(PurchaseDetailEntity::getPurchaseId, key)
                        .or()
                        .eq(PurchaseDetailEntity::getSkuId, key);
            });
        }
        IPage<PurchaseDetailEntity> page = this.page(
                new QueryPage<PurchaseDetailEntity>().getPage(params),
                wapper
        );
        return new PageUtils(page);
    }
    @Transactional
    @Override
    public void updateStatusByPurchaseIds(List<Long> ids, int purchaseStatus) throws BusinessException {
        LambdaUpdateWrapper<PurchaseDetailEntity> wrapper = new LambdaUpdateWrapper<>();
        wrapper.in(PurchaseDetailEntity::getPurchaseId, ids);
        //TODO 判断转态 ？
        PurchaseDetailEntity purchaseDetailEntity = new PurchaseDetailEntity();
        purchaseDetailEntity.setStatus(purchaseStatus);
        int update = baseMapper.update(purchaseDetailEntity, wrapper);
        // 没有需要更新的状态  出现错误
        if (update  == 0 ){
            throw new BusinessException(WareErrorCode.NO_STATUS_UPDATE ) ;
        }
    }

}