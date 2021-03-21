package com.gulimall.product.controller;

import com.gulimall.common.utils.CommonResult;
import com.gulimall.common.utils.R;
import com.gulimall.common.valid.AddGroup;
import com.gulimall.common.vo.PageVo;
import com.gulimall.product.convert.BrandConvert;
import com.gulimall.product.convert.CategoryConvert;
import com.gulimall.product.entity.CategoryBrandRelationEntity;
import com.gulimall.product.service.CategoryBrandRelationService;
import com.gulimall.product.vo.BrandRespVo;
import com.gulimall.product.vo.CategoryBrandRelationVo;
import com.gulimall.service.utils.PageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Map;


/**
 * 品牌 & 分类关联
 *
 * @author aqiang9
 * @email 2903780002@qq.com
 * @date 2020-06-10 11:26:28
 */
@RestController
@RequestMapping("product/categorybrandrelation")
public class CategoryBrandRelationController {
    @Autowired
    private CategoryBrandRelationService categoryBrandRelationService;


    /**
     * 列表
     */
    @RequestMapping("/list")
    // @RequiresPermissions("product:categorybrandrelation:list")
    public R list(@RequestParam Map<String, Object> params) {
        PageUtils page = categoryBrandRelationService.queryPage(params);

        return R.ok().put("page", page);
    }

    @GetMapping("/category/list")
    // @RequiresPermissions("product:categorybrandrelation:list")
    public CommonResult categoryBrandList(PageVo pageParams, @RequestParam("brandId") Long brandId) {
        // 通过 品牌id 获取 关联的分类
        List<CategoryBrandRelationEntity> categoryBrandRelationEntityList = categoryBrandRelationService.categoryBrandList(pageParams, brandId);
        return CommonResult.ok().data(categoryBrandRelationEntityList);
    }

    /**
     * 获取分类关联的品牌
     */
    @GetMapping("/brands/list")
    public CommonResult<List<BrandRespVo>> relationBrandsList(@RequestParam("categoryId") Long categoryId) {
        List<CategoryBrandRelationEntity> brandList = categoryBrandRelationService.getBrandListByCategoryId(categoryId);
        List<BrandRespVo> brandRespVos = BrandConvert.INSTANCE.entity2vo(brandList);
        return CommonResult.ok(brandRespVos);

    }

    /**
     * 保存
     */
    @PostMapping("/save")
//    @RequiresPermissions("product:categorybrandrelation:save")
    public CommonResult save(@RequestBody @Validated(AddGroup.class) CategoryBrandRelationVo categoryBrandRelationDto) {
        CategoryBrandRelationEntity categoryBrandRelationEntity = CategoryConvert.INSTANCE.vo2entity(categoryBrandRelationDto);
        categoryBrandRelationService.saveRelationDetail(categoryBrandRelationEntity);
        return CommonResult.ok();
    }

    /**
     * 删除
     */
    @DeleteMapping("/delete")
//    @RequiresPermissions("product:categorybrandrelation:delete")
    public R delete(@RequestBody Long[] ids) {
        categoryBrandRelationService.removeByIds(Arrays.asList(ids));
        return R.ok();
    }

}
