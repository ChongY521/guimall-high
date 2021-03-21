package com.gulimall.product.feign;

import com.gulimall.common.to.SkuHasStockTo;
import com.gulimall.common.utils.CommonResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * @author aqiang9  2020-08-15
 */
@FeignClient("gulimall-ware")
public interface WareFeignService {
    @PostMapping("/ware/waresku/has/stock")
    CommonResult<List<SkuHasStockTo>> hasStock(@RequestBody List<Long> skuIds) ;
}
