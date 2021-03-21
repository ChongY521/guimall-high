package com.gulimall.search.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author aqiang9  2020-08-20
 */
@Getter
@Setter
@ToString
public class BrandVo {
    private Long brandId ;
    private String brandName ;
    private String brandImg ;

    public BrandVo() {
    }

    public BrandVo(Long brandId, String brandName, String brandImg) {
        this.brandId = brandId;
        this.brandName = brandName;
        this.brandImg = brandImg;
    }
}
