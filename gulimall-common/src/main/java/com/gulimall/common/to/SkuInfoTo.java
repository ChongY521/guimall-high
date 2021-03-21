package com.gulimall.common.to;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;

/**
 * @author aqiang9  2020-08-09
 */
@Getter
@Setter
@ToString
@ApiModel(value = "SkuInfoTo" , description = "sku传输对象")
public class SkuInfoTo {

    @ApiModelProperty(name = "skuId", value = "sku编号")
    private Long skuId;
    /**
     * spuId
     */
    @ApiModelProperty(name = "spuId", value = "spu编号")
    private Long spuId;
    /**
     * sku名称
     */
    @ApiModelProperty(name = "skuName", value = "sku名称")
    private String skuName;
    /**
     * sku介绍描述
     */
    @ApiModelProperty(name = "skuDesc", value = "sku介绍描述")
    private String skuDesc;
    /**
     * 所属分类id
     */
    @ApiModelProperty(name = "categoryId", value = "所属分类id")
    private Long categoryId;
    /**
     * 品牌id
     */
    @ApiModelProperty(name = "brandId", value = "品牌id")
    private Long brandId;
    /**
     * 默认图片
     */
    @ApiModelProperty(name = "skuDefaultImg", value = "默认图片")
    private String skuDefaultImg;
    /**
     * 标题
     */
    @ApiModelProperty(name = "skuTitle", value = "标题")
    private String skuTitle;
    /**
     * 副标题
     */
    @ApiModelProperty("副标题")
    private String skuSubtitle;
    /**
     * 价格
     */
    @ApiModelProperty("价格")
    private BigDecimal price;
    /**
     * 销量
     */
    @ApiModelProperty(value = "销量")
    private Long saleCount;
}
