package com.gulimall.common.to;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @author aqiang9  2020-08-02
 */
@Data
public class SpuBoundTo {
    private Long spuId ;

    private BigDecimal buyBounds;
    private BigDecimal growBounds;
}
