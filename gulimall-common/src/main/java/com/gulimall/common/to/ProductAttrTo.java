package com.gulimall.common.to;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author aqiang9  2020-08-22
 */
@Getter
@Setter
@ToString
@ApiModel("product-attr")
public class ProductAttrTo {
    @ApiModelProperty("属性id")
    private Long attrId;
    @ApiModelProperty("属性名")
    private String attrName;
//   属性值 没有



}
