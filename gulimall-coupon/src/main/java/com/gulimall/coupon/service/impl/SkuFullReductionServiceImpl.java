package com.gulimall.coupon.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gulimall.common.to.MemberPrice;
import com.gulimall.common.to.SkuReductionTo;
import com.gulimall.coupon.dao.SkuFullReductionDao;
import com.gulimall.coupon.entity.MemberPriceEntity;
import com.gulimall.coupon.entity.SkuFullReductionEntity;
import com.gulimall.coupon.entity.SkuLadderEntity;
import com.gulimall.coupon.service.MemberPriceService;
import com.gulimall.coupon.service.SkuFullReductionService;
import com.gulimall.coupon.service.SkuLadderService;
import com.gulimall.service.utils.PageUtils;
import com.gulimall.service.utils.Query;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Service("skuFullReductionService")
public class SkuFullReductionServiceImpl extends ServiceImpl<SkuFullReductionDao, SkuFullReductionEntity> implements SkuFullReductionService {
    @Autowired
    private SkuLadderService skuLadderService;

    @Autowired
    MemberPriceService memberPriceService;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<SkuFullReductionEntity> page = this.page(
                new Query<SkuFullReductionEntity>().getPage(params),
                new QueryWrapper<SkuFullReductionEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public void saveSkuReduction(SkuReductionTo skuReductionTo) {
// 保存满减打折
        SkuLadderEntity skuLadderEntity = new SkuLadderEntity();


        skuLadderEntity.setSkuId(skuReductionTo.getSkuId());
        skuLadderEntity.setFullCount(skuReductionTo.getFullCount());
        skuLadderEntity.setDiscount(skuReductionTo.getDiscount());
        skuLadderEntity.setAddOther(skuReductionTo.getPriceStatus());
        // 计算折后价
//        skuLadderEntity.setPrice();

        skuLadderService.save(skuLadderEntity);


//  满减
        if (skuReductionTo.getFullCount() > 0) {
            SkuFullReductionEntity skuFullReductionEntity = new SkuFullReductionEntity();
            BeanUtils.copyProperties(skuReductionTo, skuFullReductionEntity);
            baseMapper.insert(skuFullReductionEntity);
        }
// 折扣
        List<MemberPrice> memberPrices = skuReductionTo.getMemberPrice();
        List<MemberPriceEntity> memberPriceEntities = memberPrices.stream().filter(
                price -> price.getPrice().compareTo(BigDecimal.ZERO) > 0).map(
                (memberPrice) -> {
                    MemberPriceEntity memberPriceEntity = new MemberPriceEntity();
                    memberPriceEntity.setMemberLevelId(memberPrice.getId());
                    memberPriceEntity.setMemberLevelName(memberPrice.getName());
                    memberPriceEntity.setMemberPrice(memberPrice.getPrice());
                    memberPriceEntity.setSkuId(skuReductionTo.getSkuId());
                    return memberPriceEntity;
                }
        ).collect(Collectors.toList());
        if (memberPriceEntities.size() > 0) {
            memberPriceService.saveBatch(memberPriceEntities);
        }
    }

}