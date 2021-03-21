package com.gulimall.cart.vo;

import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

/**
 * 购物项
 * @author aqiang9  2020-09-08
 */
@Setter
public class CartItemVo {
    private Long skuId ;
    private Boolean check = true ;
    private String title;
    private String image;
    private List<String > skuAttr;
    private BigDecimal price ;
    private BigDecimal totalPrice ;
    private Integer count ;

    public Long getSkuId() {
        return skuId;
    }

    public Boolean getCheck() {
        return check;
    }

    public String getTitle() {
        return title;
    }

    public String getImage() {
        return image;
    }

    public List<String> getSkuAttr() {
        return skuAttr;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public BigDecimal getTotalPrice() {
        return price.multiply(BigDecimal.valueOf(count));
    }

    public Integer getCount() {
        return count;
    }
}
