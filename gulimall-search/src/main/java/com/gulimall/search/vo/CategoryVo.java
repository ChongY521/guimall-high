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
public class CategoryVo {
    private Long categoryId ;
    private String categoryName ;

    public CategoryVo() {
    }

    public CategoryVo(Long categoryId, String categoryName) {
        this.categoryId = categoryId;
        this.categoryName = categoryName;
    }
}
