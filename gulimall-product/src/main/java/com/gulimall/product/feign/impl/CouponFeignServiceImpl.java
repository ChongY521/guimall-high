package com.gulimall.product.feign.impl;

import com.gulimall.common.to.SkuReductionTo;
import com.gulimall.common.to.SpuBoundTo;
import com.gulimall.common.utils.CommonResult;
import com.gulimall.product.feign.CouponFeignService;

/**
 *  接口调用错误异常类
 * @author aqiang9  2020-08-02
 */
public class CouponFeignServiceImpl implements CouponFeignService {
    @Override
    public CommonResult<String> saveSpuBounds(SpuBoundTo spuBoundTo) {
        return CommonResult.fail("失败")
                ;
    }

    @Override
    public CommonResult saveSkuReduction(SkuReductionTo skuReductionTo) {
        return null;
    }
}
