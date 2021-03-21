package com.gulimall.ware.controller;

import com.gulimall.common.utils.CommonResult;
import com.gulimall.common.utils.R;
import com.gulimall.service.utils.PageUtils;
import com.gulimall.ware.entity.PurchaseDetailEntity;
import com.gulimall.ware.service.PurchaseDetailService;
import com.gulimall.ware.vo.PurchaseDetailPageVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;


/**
 * 
 *
 * @author aqiang9
 * @email 2903780002@qq.com
 * @date 2020-06-09 10:07:54
 */
@RestController
@RequestMapping("ware/purchasedetail")
public class PurchaseDetailController {
    @Autowired
    private PurchaseDetailService purchaseDetailService;

    /**
     * 列表
     */
    @GetMapping("/list")
   // @RequiresPermissions("ware:purchasedetail:list")
    public CommonResult list(PurchaseDetailPageVo params){
        PageUtils page = purchaseDetailService.queryPage(params);
        return CommonResult.ok().data(page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
//   @RequiresPermissions("ware:purchasedetail:info")
    public R info(@PathVariable("id") Long id){
		PurchaseDetailEntity purchaseDetail = purchaseDetailService.getById(id);

        return R.ok().put("purchaseDetail", purchaseDetail);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
//    @RequiresPermissions("ware:purchasedetail:save")
    public R save(@RequestBody PurchaseDetailEntity purchaseDetail){
		purchaseDetailService.save(purchaseDetail);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
//   @RequiresPermissions("ware:purchasedetail:update")
    public R update(@RequestBody PurchaseDetailEntity purchaseDetail){
		purchaseDetailService.updateById(purchaseDetail);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
//    @RequiresPermissions("ware:purchasedetail:delete")
    public R delete(@RequestBody Long[] ids){
		purchaseDetailService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
