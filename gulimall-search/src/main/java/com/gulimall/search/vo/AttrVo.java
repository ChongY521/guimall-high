package com.gulimall.search.vo;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author aqiang9  2020-08-22
 */
@Getter
@Setter
public class AttrVo {
    private Long  attrId ;
    private String attrName;
    private List<String> attrValue;

}
