package com.gulimall.product.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gulimall.product.dao.SkuInfoDao;
import com.gulimall.product.entity.SkuImagesEntity;
import com.gulimall.product.entity.SkuInfoEntity;
import com.gulimall.product.entity.SpuInfoDescEntity;
import com.gulimall.product.service.*;
import com.gulimall.product.vo.SkuItemSaleAttrVo;
import com.gulimall.product.vo.SkuItemVo;
import com.gulimall.product.vo.SkuPageVo;
import com.gulimall.product.vo.SpuItemAttrGroupVo;
import com.gulimall.service.utils.PageUtils;
import com.gulimall.service.utils.QueryPage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ThreadPoolExecutor;


@Service("skuInfoService")
public class SkuInfoServiceImpl extends ServiceImpl<SkuInfoDao, SkuInfoEntity> implements SkuInfoService {

    @Autowired
    SkuImagesService skuImagesService;
    @Autowired
    SpuInfoDescService spuInfoDescService;
    @Autowired
    AttrGroupService attrGroupService;

    @Autowired
    SkuSaleAttrValueService skuSaleAttrValueService;

    /**
     * 线程池
     */
    @Autowired
    ThreadPoolExecutor executor;


    @Override
    public PageUtils queryPageOnCondition(SkuPageVo params) {

        LambdaQueryWrapper<SkuInfoEntity> wrapper = new LambdaQueryWrapper<>();

        // 封装 区间
        if (params.getBrandId() != null && params.getBrandId() > 0) {
            wrapper.eq(SkuInfoEntity::getBrandId, params.getBrandId());
        }
        if (params.getCategoryId() != null && params.getCategoryId() > 0) {
            wrapper.eq(SkuInfoEntity::getCategoryId, params.getCategoryId());
        }

        if (!StringUtils.isEmpty(params.getKey())) {
            wrapper.and(w -> {
                w.eq(SkuInfoEntity::getSkuId, params.getKey())
                        .or()
                        .like(SkuInfoEntity::getSkuName, params.getKey());
            });
        }
        if (params.getMax() != null && params.getMax().compareTo(BigDecimal.ZERO) > 0) {
            wrapper.le(SkuInfoEntity::getPrice, params.getMax());
        }
        if (params.getMin() != null) {
            wrapper.ge(SkuInfoEntity::getPrice, params.getMin());
        }


        IPage<SkuInfoEntity> spuInfoEntityIPage = baseMapper.selectPage(new QueryPage<SkuInfoEntity>().getPage(params), wrapper);
        return new PageUtils(spuInfoEntityIPage);

    }

    @Override
    public List<SkuInfoEntity> getSkusBySpuId(Long spuId) {
        return baseMapper.selectList(new LambdaQueryWrapper<SkuInfoEntity>().eq(SkuInfoEntity::getSpuId, spuId));
    }


    @Transactional
    @Override
    public void saveSkuInfo(SkuInfoEntity skuInfoEntity) {
        baseMapper.insert(skuInfoEntity);
    }


    @Override
    public SkuItemVo item(Long skuId) {
      return   itemSync(skuId );

//        SkuItemVo skuItemVo = new SkuItemVo();
//
//
////        1 、 sku详细信息  sku_info
//        SkuInfoEntity skuInfo = getById(skuId);
//        skuItemVo.setInfo(skuInfo);
////        2、sku 图片信息  sku_img
//
//        List<SkuImagesEntity> images = skuImagesService.getImagesBySkuId(skuId);
//        skuItemVo.setImages(images);
////        3、spu 销售属性组合
//        Long spuId = skuInfo.getSpuId();
//        List<SkuItemSaleAttrVo> saleAttr = skuSaleAttrValueService.getSaleAttrBySpuId(spuId);
//        skuItemVo.setSaleAttr(saleAttr);
//
////        4、spu 的介绍
//        SpuInfoDescEntity spuInfoDesc = spuInfoDescService.getById(spuId);
//        skuItemVo.setDesc(spuInfoDesc);
//
////        5、spu 规格参数信息
//        Long categoryId = skuInfo.getCategoryId();
//        List<SpuItemAttrGroupVo> groupAttrs = attrGroupService.getAttrGroupWithAttrsBySpuId(spuId, categoryId);
//        skuItemVo.setGroupAttrs(groupAttrs);
//
//        return skuItemVo;
    }

    private SkuItemVo itemSync(Long skuId) {
        SkuItemVo skuItemVo = new SkuItemVo();

        //1 、 sku详细信息  sku_info
        CompletableFuture<SkuInfoEntity> infoFuture = CompletableFuture.supplyAsync(() -> {
            SkuInfoEntity skuInfo = getById(skuId);
            skuItemVo.setInfo(skuInfo);
            return skuInfo;
        }, executor);

        CompletableFuture<Void> saleAttrFuture = infoFuture.thenAcceptAsync(skuInfo -> {
            //  3、spu 销售属性组合
            List<SkuItemSaleAttrVo> saleAttr = skuSaleAttrValueService.getSaleAttrBySpuId(skuInfo.getSpuId());
            skuItemVo.setSaleAttr(saleAttr);
            System.out.println(saleAttr);
        }, executor);
        CompletableFuture<Void> descFuture = infoFuture.thenAcceptAsync(skuInfo -> {
            //  4、spu 的介绍
            SpuInfoDescEntity spuInfoDesc = spuInfoDescService.getById(skuInfo.getSpuId());
            skuItemVo.setDesc(spuInfoDesc);
        }, executor);

        CompletableFuture<Void> attrGroupFuture = infoFuture.thenAcceptAsync(skuInfo -> {
//            5、spu 规格参数信息
            Long categoryId = skuInfo.getCategoryId();
            List<SpuItemAttrGroupVo> groupAttrs = attrGroupService.getAttrGroupWithAttrsBySpuId(skuInfo.getSpuId(), categoryId);
            System.out.println(groupAttrs);
            skuItemVo.setGroupAttrs(groupAttrs);
        }, executor);


        CompletableFuture<Void> imageFuture = CompletableFuture.runAsync(() -> {
            List<SkuImagesEntity> images = skuImagesService.getImagesBySkuId(skuId);
            skuItemVo.setImages(images);
        }, executor);

//        等待所有 任务完成
        try {
            CompletableFuture.allOf(saleAttrFuture , descFuture , attrGroupFuture,imageFuture ).get() ;
        } catch (InterruptedException e) {
            log.error("查询商品详情异步编排错误: ");
            log.error(e.getMessage() );
        } catch (ExecutionException e) {
            log.error(e.getMessage() );
        }
        return skuItemVo;
    }
}