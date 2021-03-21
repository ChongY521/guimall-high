package com.gulimall.product.vo;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author aqiang9  2020-08-15
 */
//@AllArgsConstructor
//@NoArgsConstructor
@Getter
@Setter
public class Category2Vo {
    private String id;
    private String name;
    private String category1Id;
    private List<Category3Vo> category3List ;


}
