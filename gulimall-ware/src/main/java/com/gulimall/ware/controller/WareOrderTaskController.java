package com.gulimall.ware.controller;

import com.gulimall.common.utils.CommonResult;
import com.gulimall.service.utils.PageUtils;
import com.gulimall.ware.entity.WareOrderTaskEntity;
import com.gulimall.ware.service.WareOrderTaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Map;



/**
 * 库存工作单
 *
 * @author aqiang9
 * @email 2903780002@qq.com
 * @date 2020-06-09 10:07:54
 */
@RestController
@RequestMapping("ware/wareordertask")
public class WareOrderTaskController {
    @Autowired
    private WareOrderTaskService wareOrderTaskService;

    /**
     * 列表
     */
    @GetMapping("/list")
   // @RequiresPermissions("ware:wareordertask:list")
    public CommonResult list(@RequestParam Map<String, Object> params){
        PageUtils page = wareOrderTaskService.queryPage(params);

        return CommonResult.ok( page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
//   @RequiresPermissions("ware:wareordertask:info")
    public CommonResult<WareOrderTaskEntity> info(@PathVariable("id") Long id){
		WareOrderTaskEntity wareOrderTask = wareOrderTaskService.getById(id);
        return CommonResult.ok(wareOrderTask);
    }

    /**
     * 保存
     */
    @PostMapping("/save")
//    @RequiresPermissions("ware:wareordertask:save")
    public CommonResult save(@RequestBody WareOrderTaskEntity wareOrderTask){
		wareOrderTaskService.save(wareOrderTask);

        return CommonResult.ok();
    }

    /**
     * 修改
     */
    @PutMapping("/update")
//   @RequiresPermissions("ware:wareordertask:update")
    public CommonResult update(@RequestBody WareOrderTaskEntity wareOrderTask){
		wareOrderTaskService.updateById(wareOrderTask);
        return CommonResult.ok();
    }

    /**
     * 删除
     */
    @DeleteMapping("/delete")
//    @RequiresPermissions("ware:wareordertask:delete")
    public CommonResult delete(@RequestBody Long[] ids){
		wareOrderTaskService.removeByIds(Arrays.asList(ids));
        return CommonResult.ok();
    }
}
