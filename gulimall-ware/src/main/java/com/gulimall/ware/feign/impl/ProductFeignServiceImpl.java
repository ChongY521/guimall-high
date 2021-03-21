package com.gulimall.ware.feign.impl;

import com.gulimall.common.utils.CommonResult;
import com.gulimall.ware.feign.ProductFeignService;

/**
 * 远程调用失败的 兜底
 * @author aqiang9  2020-08-09
 */
public class ProductFeignServiceImpl implements ProductFeignService {
    @Override
    public CommonResult info(Long skuId) {
        return CommonResult.fail("远程调用失败");
    }
}
