package com.gulimall.ware.feign;

import com.gulimall.common.to.SkuInfoTo;
import com.gulimall.common.utils.CommonResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient("gulimall-product")
public interface ProductFeignService {

    @GetMapping("/product/skuinfo/info/{skuId}")
    CommonResult<SkuInfoTo> info(@PathVariable("skuId") Long skuId);
}