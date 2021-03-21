package com.gulimall.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gulimall.product.entity.SkuInfoEntity;
import com.gulimall.product.vo.SkuItemVo;
import com.gulimall.product.vo.SkuPageVo;
import com.gulimall.service.utils.PageUtils;

import java.util.List;

/**
 * sku信息
 *
 * @author aqiang9
 * @email 2903780002@qq.com
 * @date 2020-06-10 11:26:28
 */
public interface SkuInfoService extends IService<SkuInfoEntity> {


    void saveSkuInfo(SkuInfoEntity skuInfoEntity);

    PageUtils queryPageOnCondition(SkuPageVo params);

    List<SkuInfoEntity> getSkusBySpuId(Long spuId);

    /**
     * 查询商品详细信息
     * @param skuId skuId
     * @return 商品详细信息
     */
    SkuItemVo item(Long skuId);
}

