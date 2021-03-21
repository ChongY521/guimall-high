package com.gulimall.ware.controller;

import com.gulimall.common.utils.CommonResult;
import com.gulimall.common.utils.R;
import com.gulimall.common.vo.PageVo;
import com.gulimall.service.utils.PageUtils;
import com.gulimall.ware.entity.WareInfoEntity;
import com.gulimall.ware.service.WareInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;



/**
 * 仓库信息
 *
 * @author aqiang9
 * @email 2903780002@qq.com
 * @date 2020-06-09 10:07:54
 */
@RestController
@RequestMapping("/ware/wareinfo")
public class WareInfoController {
    @Autowired
    private WareInfoService wareInfoService;

    /**
     * 列表
     */
    @RequestMapping("/list")
   // @RequiresPermissions("ware:wareinfo:list")
    public CommonResult<PageUtils> list(PageVo params){
        PageUtils page = wareInfoService.queryPage(params);
        return CommonResult.ok(page);
    }


    /**
     * 信息
     */
    @GetMapping("/info/{id}")
//   @RequiresPermissions("ware:wareinfo:info")
    public CommonResult<WareInfoEntity> info(@PathVariable("id") Long id){
		WareInfoEntity wareInfo = wareInfoService.getById(id);
        return CommonResult.ok(wareInfo);
    }

    /**
     * 保存
     */
    @PostMapping("/save")
//    @RequiresPermissions("ware:wareinfo:save")
    public CommonResult save(@RequestBody WareInfoEntity wareInfo){
		wareInfoService.save(wareInfo);
        return CommonResult.ok();
    }

    /**
     * 修改
     */
    @PutMapping("/update")
//   @RequiresPermissions("ware:wareinfo:update")
    public R update(@RequestBody WareInfoEntity wareInfo){
		wareInfoService.updateById(wareInfo);

        return R.ok();
    }

    /**
     * 删除
     */
    @DeleteMapping("/delete")
//    @RequiresPermissions("ware:wareinfo:delete")
    public R delete(@RequestBody Long[] ids){
		wareInfoService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
