package com.gulimall.ware.controller;

import com.gulimall.common.to.SkuHasStockTo;
import com.gulimall.common.utils.CommonResult;
import com.gulimall.service.utils.PageUtils;
import com.gulimall.ware.entity.WareSkuEntity;
import com.gulimall.ware.service.WareSkuService;
import com.gulimall.ware.vo.WareSkuPageVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;


/**
 * 商品库存
 *
 * @author aqiang9
 * @email 2903780002@qq.com
 * @date 2020-06-09 10:07:53
 */
@RestController
@RequestMapping("/ware/waresku")
public class WareSkuController {
    @Autowired
    private WareSkuService wareSkuService;

    /**
     * 查询商品是否有库存
     */
    @PostMapping("/has/stock")
    public CommonResult<List<SkuHasStockTo>> hasStock(@RequestBody List<Long> skuIds) {
        List<SkuHasStockTo> stockTos = wareSkuService.getSkuHasStock(skuIds);
        return CommonResult.ok(stockTos);
    }


    /**
     * 列表
     */
    @GetMapping("/list")
    // @RequiresPermissions("ware:waresku:list")
    public CommonResult list(WareSkuPageVo params) {
        PageUtils page = wareSkuService.queryPage(params);
        return CommonResult.ok(page);
    }

    /**
     * 信息
     */
    @GetMapping("/info/{id}")
//   @RequiresPermissions("ware:waresku:info")
    public CommonResult<WareSkuEntity> info(@PathVariable("id") Long id) {
        WareSkuEntity wareSku = wareSkuService.getById(id);

        return CommonResult.ok(wareSku);
    }

    /**
     * 保存
     */
    @PostMapping("/save")
//    @RequiresPermissions("ware:waresku:save")
    public CommonResult save(@RequestBody WareSkuEntity wareSku) {
        wareSkuService.save(wareSku);

        return CommonResult.ok();
    }

    /**
     * 修改
     */
    @PutMapping("/update")
//   @RequiresPermissions("ware:waresku:update")
    public CommonResult update(@RequestBody WareSkuEntity wareSku) {
        wareSkuService.updateById(wareSku);

        return CommonResult.ok();
    }

    /**
     * 删除
     */
    @DeleteMapping("/delete")
//    @RequiresPermissions("ware:waresku:delete")
    public CommonResult delete(@RequestBody Long[] ids) {
        wareSkuService.removeByIds(Arrays.asList(ids));
        return CommonResult.ok();
    }

}
