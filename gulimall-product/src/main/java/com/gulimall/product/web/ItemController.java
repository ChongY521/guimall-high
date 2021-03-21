package com.gulimall.product.web;

import com.gulimall.product.service.SkuInfoService;
import com.gulimall.product.vo.SkuItemVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author aqiang9  2020-08-23
 */
@Controller
public class ItemController {

    @Autowired
    SkuInfoService skuInfoService;


    @GetMapping("/item/{skuId}.html")
    public String item(@PathVariable Long skuId, ModelMap modelMap) {
        SkuItemVo skuItem = skuInfoService.item(skuId);
        modelMap.put("item", skuItem);
        return "item";
    }


}
