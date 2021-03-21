package com.gulimall.common.to.es;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author aqiang9  2020-08-15
 */
@Getter
@Setter
public class SkuEsModel {
    private Long skuId;
    private Long spuId;
    private String skuTitle;
    private BigDecimal skuPrice;
    private String skuImg;
    private Long saleCount;
    private Boolean hasStock;
    private Long hotScore;
    private Long categoryId;

    private Long brandId ;
    private String brandName;
    private String brandImg;
    private String categoryName;
    private List<Attrs> attrs;
}
