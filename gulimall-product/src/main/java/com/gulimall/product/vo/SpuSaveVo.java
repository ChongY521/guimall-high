package com.gulimall.product.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@ToString
public class SpuSaveVo {
    private String spuName;
    private String spuDescription;
    private Long categoryId;
    private Long brandId;
    private BigDecimal weight;
    private int publishStatus;
    /**
     * 描述信息
     */
    private List<String> decript;
    private List<String> images;
    private SpuSaveBounds bounds;
    private List<SpuSaveBaseAttrs> baseAttrs;
    private List<SpuSaveSku> skus;
}