package com.gulimall.product.vo;

import lombok.Data;

/**
 * 保存Spu时需要的 attrvo
 * @author aqiang9  2020-08-01
 */
@Data
public class SpuSaveAttr{
    private Long attrId;
    private String attrName;
    private String attrValue;
}
