package com.gulimall.product.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.gulimall.common.valid.AddGroup;
import com.gulimall.common.valid.ListValue;
import com.gulimall.common.valid.UpdateGroup;
import com.gulimall.common.valid.UpdateStatusGroup;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.*;
import java.util.List;

/**
 * 分类 的 页面数据
 *
 * @author aqiang9  2020-07-27
 */
@Data
@ApiModel
public class CategoryVo {
    @ApiModelProperty(value = "分类id" , notes = "添加时不能有, 修改必须指定")
    @Null(message = "菜单id不能指定", groups = AddGroup.class)
    @NotNull(message = "菜单id不能缺失", groups = {UpdateGroup.class, UpdateStatusGroup.class})
    private Long catId;


    @ApiModelProperty("分类名称")
    @NotEmpty(message = "菜单名不能缺失", groups = {AddGroup.class})
    private String name;

    @ApiModelProperty("父分类id")
    private Long parentCid;

    @ApiModelProperty(value = "层级", allowableValues = "1,2,3")
    private Integer catLevel;

    @ApiModelProperty(value = "是否显示[0-不显示，1显示]", allowableValues = "0,1")
    @NotNull(message = "转态值缺失", groups = {AddGroup.class, UpdateStatusGroup.class})
    @ListValue(value = {0, 1}, groups = {AddGroup.class, UpdateStatusGroup.class})
    private Integer showStatus;
    /**
     * 排序
     */
    @ApiModelProperty(value = "排序", allowableValues = "[0,infinity]")
    @PositiveOrZero(message = "排序字段必须大于等于0", groups = {AddGroup.class, UpdateGroup.class})
    private Integer sort;


    @ApiModelProperty(value = "图标地址" , dataType = "String")
    private String icon;
    /**
     * 计量单位
     */
    @ApiModelProperty(value = "计量单位" , dataType = "String")
    @NotBlank(message = "请指定计价单位", groups = {AddGroup.class})
    private String productUnit;
    /**
     * 商品数量
     */

    @ApiModelProperty(value = "商品数量" , allowableValues = "[0,infinity]")
    @PositiveOrZero(message = "商品数量必须 大于0 ")
    private Integer productCount;
    /**
     * 子菜单
     */
    @ApiModelProperty(value = "子菜单" , allowEmptyValue = true)
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<CategoryVo> children;
}