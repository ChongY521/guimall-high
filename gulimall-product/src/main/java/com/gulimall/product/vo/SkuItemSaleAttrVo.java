package com.gulimall.product.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

/**
 * @author aqiang9  2020-08-25
 */
@Getter
@Setter
@ToString
public class SkuItemSaleAttrVo {
    private Long attrId;
    private String attrName;
//    private String attrValues;
    private List<AttrValueWithSkuIdVo> attrValues ;
}