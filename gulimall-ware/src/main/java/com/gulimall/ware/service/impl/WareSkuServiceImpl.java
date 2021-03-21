package com.gulimall.ware.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gulimall.common.to.SkuHasStockTo;
import com.gulimall.common.to.SkuInfoTo;
import com.gulimall.common.utils.CommonResult;
import com.gulimall.service.utils.PageUtils;
import com.gulimall.service.utils.QueryPage;
import com.gulimall.ware.dao.WareSkuDao;
import com.gulimall.ware.entity.WareSkuEntity;
import com.gulimall.ware.feign.ProductFeignService;
import com.gulimall.ware.service.WareSkuService;
import com.gulimall.ware.vo.WareSkuPageVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Slf4j
@Service("wareSkuService")
public class WareSkuServiceImpl extends ServiceImpl<WareSkuDao, WareSkuEntity> implements WareSkuService {

    @Autowired
    ProductFeignService productFeignService;

    @Override
    public PageUtils queryPage(WareSkuPageVo params) {
        LambdaQueryWrapper<WareSkuEntity> wrapper = new LambdaQueryWrapper<>();

        if (params.getWareId() != null && params.getWareId() > 0) {
            wrapper.eq(WareSkuEntity::getWareId, params.getWareId());
        }
        if (params.getSkuId() != null && params.getSkuId() > 0) {
            wrapper.eq(WareSkuEntity::getSkuId, params.getSkuId());
        }

        IPage<WareSkuEntity> page = this.page(
                new QueryPage<WareSkuEntity>().getPage(params),
                wrapper
        );
        return new PageUtils(page);
    }

    @Override
    public void addStock(Long skuId, Long wareId, Integer skuNum) {
//1、判断如果还没有这个库存记录新增
        List<WareSkuEntity> entities = baseMapper.selectList(new LambdaQueryWrapper<WareSkuEntity>().eq(WareSkuEntity::getSkuId, skuId).eq(WareSkuEntity::getWareId, wareId));

        if (entities == null || entities.size() == 0) {
            WareSkuEntity skuEntity = new WareSkuEntity();
            skuEntity.setSkuId(skuId);
            skuEntity.setStock(skuNum);
            skuEntity.setWareId(wareId);
            skuEntity.setStockLocked(0);
            //TODO 远程查询sku的名字，如果失败，整个事务无需回滚
            //1、自己catch异常
            //TODO 还可以用什么办法让异常出现以后不回滚？高级
            try {
                CommonResult<SkuInfoTo> resp = productFeignService.info(skuId);
                if (resp.getCode() == 0 && resp.getData() != null) {
                    SkuInfoTo data = resp.getData();
                    skuEntity.setSkuName(data.getSkuName());
                }
            } catch (Exception e) {
                log.error("远程调用异常，{}", e.getMessage());
            }
            baseMapper.insert(skuEntity);
        } else {
            baseMapper.addStock(skuId, wareId, skuNum);
        }
    }

    @Override
    public List<SkuHasStockTo> getSkuHasStock(List<Long> skuIds) {
        List<SkuHasStockTo> collect = skuIds.stream().map(skuId -> {
            SkuHasStockTo skuHasStockTo = new SkuHasStockTo();

            Long count = baseMapper.selectStockCount(skuId) ;
            skuHasStockTo.setSkuId(skuId);
            skuHasStockTo.setHasStock(count != null && count > 0);

            return skuHasStockTo;
        }).collect(Collectors.toList());

        return collect ;
    }
}