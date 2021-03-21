package com.gulimall.product.controller;

import com.gulimall.common.utils.CommonResult;
import com.gulimall.common.valid.AddGroup;
import com.gulimall.common.valid.UpdateGroup;
import com.gulimall.common.vo.PageVo;
import com.gulimall.product.convert.AttrConvert;
import com.gulimall.product.entity.AttrAttrgroupRelationEntity;
import com.gulimall.product.entity.AttrEntity;
import com.gulimall.product.entity.AttrGroupEntity;
import com.gulimall.product.service.AttrAttrgroupRelationService;
import com.gulimall.product.service.AttrGroupService;
import com.gulimall.product.service.AttrService;
import com.gulimall.product.vo.AttrGroupRelationVo;
import com.gulimall.product.vo.AttrGroupVo;
import com.gulimall.product.vo.AttrGroupWithAttrsRespVo;
import com.gulimall.service.utils.PageUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 属性分组
 *
 * @author aqiang9
 * @email 2903780002@qq.com
 * @date 2020-06-10 11:26:28
 */
@RestController
@RequestMapping("product/attrgroup")
@Api(value = "" , tags = {"属性分组"})
public class AttrGroupController {
    @Autowired
    private AttrGroupService attrGroupService;

    @Autowired
    private AttrService attrService;
    @Autowired
    private AttrAttrgroupRelationService attrAttrgroupRelationService;


    /**
     * 获取分类下所有分组&关联属性
     * @param categoryId 三级分类id
     * @return
     */
    @ApiOperation("获取分类下所有分组&关联属性")
    @GetMapping("{categoryId}/withattr")
    public CommonResult categoryWithAttr(@PathVariable Long categoryId ){
        // 当前属性下的所有分组
        // 查出当前分组  下的 所有属性
        List<AttrGroupWithAttrsRespVo>  data = attrGroupService.getAttrGroupWithAttrByCategoryId(categoryId)  ;
      return   CommonResult.ok().data(data);
    }

    /**
     * 列表
     */
    @ApiOperation("获取分类下所有分组")
    @GetMapping("/list/{categoryId}")
    // @RequiresPermissions("product:attrgroup:list")
    public CommonResult list(PageVo pageVo, @PathVariable Long categoryId) {
        PageUtils page = attrGroupService.queryPage(pageVo, categoryId);
        return CommonResult.ok().data(page);
    }
    /**
     * 获取属性分组的关联的所有属性
     *
     * @param pageParams  分页参数
     * @param attrGroupId 分组id
     */
    @GetMapping("/{attrGroupId}/attr/relation")
    public CommonResult attrRelationList(PageVo pageParams, @PathVariable(name = "attrGroupId") Long attrGroupId) {
//        PageUtils pageData =   attrGroupService.queryAttrRelationPage(pageParams , attrGroupId );
        List<AttrEntity> attrEntities = attrService.getAttrRelationByAttrGroupId(attrGroupId);
        return CommonResult.ok().data(attrEntities);
    }

    /**
     * 获取属性分组没有关联的其他属性
     */
    @GetMapping("/{attrGroupId}/noattr/relation")
    public CommonResult noAttrRelation(PageVo pageParams, @PathVariable(name = "attrGroupId") Long attrGroupId) {
        // 本分类 没有关联的属性
        PageUtils pageData = attrService.getNoRelationAttr(pageParams, attrGroupId);
        return CommonResult.ok().data(pageData);
    }

    /**
     * 信息
     */
    @GetMapping("/info/{attrGroupId}")
//   @RequiresPermissions("product:attrgroup:info")
    public CommonResult info(@PathVariable("attrGroupId") Long attrGroupId) {
        AttrGroupVo attrGroupVo = attrGroupService.getAttrGroupInfo(attrGroupId);
        return CommonResult.ok().data(attrGroupVo);
    }

    /**
     * 添加属性与分组关联关系
     */
    @PostMapping("/attr/relation")
    public CommonResult saveAttrRelation(@RequestBody List<AttrGroupRelationVo> relationVo) {
        List<AttrAttrgroupRelationEntity> attrgroupRelationEntities = AttrConvert.INSTANCE.listVo2listEntity(relationVo);
        attrAttrgroupRelationService.saveBatch(attrgroupRelationEntities);
        return CommonResult.ok();
    }

    /**
     * 保存
     */
    @PostMapping("/save")
//    @RequiresPermissions("product:attrgroup:save")
    public CommonResult save(@RequestBody @Validated(AddGroup.class) AttrGroupVo attrGroupVo) {
        AttrGroupEntity attrGroupEntity = AttrConvert.INSTANCE.vo2entity(attrGroupVo);
        attrGroupService.save(attrGroupEntity);
        return CommonResult.ok();
    }

    /**
     * 修改
     */
    @PutMapping("/update")
//   @RequiresPermissions("product:attrgroup:update")
    public CommonResult update(@RequestBody @Validated(UpdateGroup.class) AttrGroupVo attrGroupVo) {
        AttrGroupEntity attrGroupEntity = AttrConvert.INSTANCE.vo2entity(attrGroupVo);
        attrGroupService.updateById(attrGroupEntity);
        return CommonResult.ok();
    }

    /**
     * 删除 分组与属性 关联关系
     */
    @DeleteMapping("/attr/relation/delete")
    public CommonResult deleteRelation(@RequestBody List<AttrGroupRelationVo> relationVo) {
        attrService.removeRelation(relationVo);
        return CommonResult.ok();
    }

    /**
     * 删除分组以及对应的关联关系
     */
    @DeleteMapping("/delete")
    public CommonResult delete(@RequestBody List<Long> attrGroupIds) {
        attrGroupService.removeGroup(attrGroupIds);
        return CommonResult.ok();
    }
}