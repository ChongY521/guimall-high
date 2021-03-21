package com.gulimall.product.vo;

import com.gulimall.common.vo.PageVo;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @author aqiang9  2020-08-02
 */
@Data
public class SkuPageVo extends PageVo {
    private Long categoryId  ;
    private Long brandId ;
   private BigDecimal min  ;
   private BigDecimal max ;

}
