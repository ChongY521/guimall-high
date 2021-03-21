package com.gulimall.product.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * item 切换 使用到的vo
 * @author aqiang9  2020-09-01
 */
@Getter
@Setter
@ToString
public class AttrValueWithSkuIdVo {
    private String attrValue ;
    private String skuIds ;
}
