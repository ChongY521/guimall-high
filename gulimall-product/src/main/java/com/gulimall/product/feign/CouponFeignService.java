package com.gulimall.product.feign;

import com.gulimall.common.to.SkuReductionTo;
import com.gulimall.common.to.SpuBoundTo;
import com.gulimall.common.utils.CommonResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @author aqiang9  2020-08-02
 */
@FeignClient("gulimall-coupon")
public interface CouponFeignService {
    /**
     * 1、CouponFeignService.saveSpuBounds(spuBoundTo);
     * 1）、@RequestBody将这个对象转为json。
     * 2）、找到gulimall-coupon服务，给/coupon/spubounds/save发送请求。
     * 将上一步转的json放在请求体位置，发送请求；
     * 3）、对方服务收到请求。请求体里有json数据。
     * (@RequestBody SpuBoundsEntity spuBounds)；将请求体的json转为SpuBoundsEntity；
     * 只要json数据模型是兼容的。双方服务无需使用同一个to
     */

    @PostMapping("/coupon/spubounds/save")
    CommonResult saveSpuBounds(SpuBoundTo spuBoundTo);

    @PostMapping("/coupon/skufullreduction/saveinfo")
    CommonResult saveSkuReduction(@RequestBody SkuReductionTo skuReductionTo);
}
