package com.gulimall.common.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * @author aqiang9  2020-07-30
 */
@Getter
@Setter
@ApiModel("属性信息")
public class AttrVo {
    /**
     * 属性id
     */
    @ApiModelProperty("属性id")
    private Long attrId;
    /**
     * 属性名
     */
    @ApiModelProperty("属性名")
    private String attrName;
    /**
     * 是否需要检索[0-不需要，1-需要]
     */
    @ApiModelProperty(value = "是否需要检索[0-不需要，1-需要]", allowableValues = "0,1")
    private Integer searchType;
    /**
     * 属性图标
     */
    @ApiModelProperty("属性图标")
    private String icon;
    /**
     * 可选值列表[用逗号分隔]
     */
    @ApiModelProperty("可选值列表[用逗号分隔]")
    private String valueSelect;
    /**
     * 属性类型[0-销售属性，1-基本属性，2-既是销售属性又是基本属性]
     */

    @ApiModelProperty(value = "属性类型[0-销售属性，1-基本属性，2-既是销售属性又是基本属性]", allowableValues = "0,1,2")
    private Integer attrType;
    /**
     * 启用状态[0 - 禁用，1 - 启用]
     */

    @ApiModelProperty(value = "状态[0-禁用，1-启用]", allowableValues = "0,1")
    private Long enable;
    /**
     * 所属分类
     */

    @ApiModelProperty(value = "所属分类Id")
    private Long categoryId;
    /**
     * 快速展示【是否展示在介绍上；0-否 1-是】，在sku中仍然可以调整
     */
    @ApiModelProperty(value = "快速展示[0-否 1-是]" ,allowableValues = "0,1")
    private Integer showDesc;

    /**
     * 属性分组 id
     */
    @ApiModelProperty(value = "属性分组id")
    private Long attrGroupId;
    /**
     * 值类型 0 单选 1 多选
     */
    @ApiModelProperty(value = "值类型[0-单选,1-多选]", allowableValues = "0,1")
    private int valueType;

}
