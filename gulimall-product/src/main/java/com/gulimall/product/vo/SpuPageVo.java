package com.gulimall.product.vo;

import com.gulimall.common.vo.PageVo;
import lombok.Data;

/**
 * @author aqiang9  2020-08-02
 */
@Data
public class SpuPageVo extends PageVo {
    private Long categoryId  ;
    private Long brandId ;
    private Integer status ;
}
