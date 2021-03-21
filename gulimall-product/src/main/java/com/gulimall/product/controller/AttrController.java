package com.gulimall.product.controller;

import com.gulimall.common.constant.SwaggerParamType;
import com.gulimall.common.utils.CommonResult;
import com.gulimall.common.vo.PageVo;
import com.gulimall.product.service.AttrService;
import com.gulimall.common.vo.AttrRespVo;
import com.gulimall.common.vo.AttrVo;
import com.gulimall.service.utils.PageUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 商品属性
 *
 * @author aqiang9
 * @email 2903780002@qq.com
 * @date 2020-06-10 11:26:28
 */
@RestController
@RequestMapping("product/attr")
@Api(value = "attrController", tags = {"商品属性"})
public class AttrController {
    @Autowired
    private AttrService attrService;

    /**
     * 获取 列表
     *
     * @param pageParams 分页参数
     * @param attrType   属性类型  0 为 基本    1 为销售
     * @param categoryId 分类 id
     */
    @ApiOperation("获取属性列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "attrType", value = "属性类型", allowableValues = "0,1", paramType = SwaggerParamType.PATH, dataType = "int", required = true),
            @ApiImplicitParam(name = "categoryId", value = "分类id", paramType = SwaggerParamType.PATH, dataType = "long", required = true)})
    @GetMapping("/{attrType}/list/{categoryId}")
    public CommonResult<PageUtils> listAttr(PageVo pageParams, @PathVariable("attrType") int attrType, @PathVariable(value = "categoryId", required = false) Long categoryId) {
        PageUtils data = attrService.queryList(pageParams, attrType, categoryId);
        return CommonResult.ok(data);
    }

    /**
     * 信息
     */
    @GetMapping("/info/{attrId}")
//   @RequiresPermissions("product:attr:info")
    public CommonResult<AttrRespVo> info(@PathVariable("attrId") Long attrId) {
        AttrRespVo attrRespVo = attrService.getAttrInfo(attrId);
        return CommonResult.ok(attrRespVo);
    }

    /**
     * 保存 属性
     */
    @PostMapping("/save")
//    @RequiresPermissions("product:attr:save")
    public CommonResult<Object> save(@RequestBody AttrVo attrVo) {
        attrService.saveAttrInfo(attrVo);
        return CommonResult.ok();
    }

    /**
     * 修改  更新相关信息
     */
    @PutMapping("/update")
//   @RequiresPermissions("product:attr:update")
    public CommonResult<Object>  update(@RequestBody AttrVo attrVo) {
        attrService.updateAttrInfo(attrVo);
        return CommonResult.ok();
    }

    /**
     * 删除
     */
    @DeleteMapping("/delete/{attrType}")
//    @RequiresPermissions("product:attr:delete")
    public CommonResult<Object> delete(@RequestBody List<Long> attrIds, @PathVariable Long attrType) {
        if (attrIds != null || attrIds.size() > 0) {
            attrService.removeAttrInfo(attrIds, attrType);
        }
        return CommonResult.ok();
    }


//    ============================================ 远程调用的方法   ====

    /**
     * 主要是去获取  属性名
     * @param attrId
     * @return
     */
    @PostMapping("/info/{attrId}")
    public CommonResult<AttrRespVo> attrInfo(@PathVariable("attrId") Long attrId){
        AttrRespVo attrInfo = attrService.getAttrInfo(attrId);
        return CommonResult.ok(attrInfo) ;
    }

}
