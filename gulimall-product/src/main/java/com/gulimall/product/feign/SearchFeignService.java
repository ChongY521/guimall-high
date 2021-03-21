package com.gulimall.product.feign;

import com.gulimall.common.to.es.SkuEsModel;
import com.gulimall.common.utils.CommonResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * @author aqiang9  2020-08-15
 */
@FeignClient("gulimall-search")
public interface SearchFeignService {
    @PostMapping("/search/product/up")
    CommonResult productStatusUp(@RequestBody List<SkuEsModel> skuEsModels);

}
