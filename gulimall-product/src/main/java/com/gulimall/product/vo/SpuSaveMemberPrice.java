package com.gulimall.product.vo;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class SpuSaveMemberPrice {

    private Long id;
    private String name;
    private BigDecimal price;

}