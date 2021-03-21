package com.gulimall.coupon.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gulimall.common.to.SkuReductionTo;
import com.gulimall.coupon.entity.SkuFullReductionEntity;
import com.gulimall.service.utils.PageUtils;

import java.util.Map;

/**
 * 商品满减信息
 *
 * @author aqiang9
 * @email 2903780002@qq.com
 * @date 2020-06-09 09:23:34
 */
public interface SkuFullReductionService extends IService<SkuFullReductionEntity> {

    PageUtils queryPage(Map<String, Object> params);

    void saveSkuReduction(SkuReductionTo skuReductionTo);

}

