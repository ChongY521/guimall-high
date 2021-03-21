package com.gulimall.product.controller;

import com.gulimall.common.constant.SwaggerParamType;
import com.gulimall.common.utils.CommonResult;
import com.gulimall.common.valid.UpdateGroup;
import com.gulimall.common.valid.UpdateStatusGroup;
import com.gulimall.common.vo.PageVo;
import com.gulimall.product.convert.BrandConvert;
import com.gulimall.product.entity.BrandEntity;
import com.gulimall.product.service.BrandService;
import com.gulimall.product.vo.BrandVo;
import com.gulimall.service.utils.PageUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * 品牌
 *
 * @author aqiang9
 * @email 2903780002@qq.com
 * @date 2020-06-10 11:26:28
 */
@Api(value = "商品品牌" , tags = "商品品牌-brand")
@RestController
@RequestMapping("product/brand")
public class BrandController {
    @Autowired
    private BrandService brandService;
    /**
     * 列表
     */
    @GetMapping("/list")
    // @RequiresPermissions("product:brand:list")
    public CommonResult list(PageVo pageParam) {
        PageUtils page = brandService.queryPage(pageParam);
        return CommonResult.ok().data(page);
    }
    /**
     * 信息
     */
    @ApiOperation("获取品牌详情")
    @ApiImplicitParam(value = "brandId" , paramType = SwaggerParamType.PATH , required = true)
    @GetMapping("/info/{brandId}")
//   @RequiresPermissions("product:brand:info")
    public CommonResult<BrandVo> info(@PathVariable("brandId") Long brandId) {
        BrandEntity brand = brandService.getById(brandId);
        BrandVo brandVo = BrandConvert.INSTANCE.entity2vo(brand);
        return CommonResult.ok(brandVo) ;
    }

    /**
     * 保存
     */
    @PostMapping("/save")
//    @RequiresPermissions("product:brand:save")
    public CommonResult save(@RequestBody BrandEntity brand) {
        brandService.save(brand);
        return CommonResult.ok("品牌添加成功");
    }

    /**
     * 品牌信息 修改
     */
    @PutMapping("/update")
    public CommonResult update(@RequestBody @Validated(value = {UpdateGroup.class}) BrandVo brandVo) {
        brandService.updateDetail(brandVo) ;

        return CommonResult.ok("品牌信息修改成功");
    }

    /**
     * 更新品牌 状态
     *
     * @param brandVo 包含 id 与 状态
     */
    @PutMapping("/update/status")
    public CommonResult updateStatus(@RequestBody @Validated(UpdateStatusGroup.class) BrandVo brandVo) {
        BrandEntity brandEntity = BrandConvert.INSTANCE.vo2entity(brandVo);
        brandService.updateById(brandEntity);



        return CommonResult.ok("状态更新成功");
    }

    /**
     * 删除
     */
    @DeleteMapping("/delete")
//    @RequiresPermissions("product:brand:delete")
    public CommonResult delete(@RequestBody List<Long> brandIds) {
        if (brandIds != null && brandIds.size() > 0)
            brandService.removeByIds(brandIds);
        return CommonResult.ok("删除成功");
    }
}
