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
public class SpuItemAttrGroupVo {
    private String groupName;
    private List<SpuItemAttrVo> attrs;
}