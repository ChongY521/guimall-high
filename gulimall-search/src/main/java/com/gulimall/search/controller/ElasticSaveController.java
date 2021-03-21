package com.gulimall.search.controller;

import com.gulimall.common.to.es.SkuEsModel;
import com.gulimall.common.utils.CommonResult;
import com.gulimall.search.exception.SearchErrorCode;
import com.gulimall.search.service.ProductSaveService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

/**
 * @author aqiang9  2020-08-15
 */
@RestController
@RequestMapping("/search")
@Slf4j
public class ElasticSaveController {
    @Autowired
    ProductSaveService productSaveService ;

    /**
     * 商品上架
     * @return
     */
    @PostMapping("/product/up")
    public CommonResult productStatusUp(@RequestBody List<SkuEsModel> skuEsModels) throws IOException {
        Boolean result = productSaveService.productStatusUp(skuEsModels);
        if (result){
           return CommonResult.ok("商品es上架成功") ;
        }
        return CommonResult.fail(SearchErrorCode.PRODUCT_UP_ERROR) ;
    }


}
