package com.gulimall.common.vo;

import lombok.Data;

import java.util.List;

/**
 * @author aqiang9  2020-07-30
 */

@Data
public class AttrRespVo extends AttrVo {
    private String attrGroupName ;
    private String categoryName ;
    /**
     * 分类路径
     */
    private List<Long> categoryPath ;
}
