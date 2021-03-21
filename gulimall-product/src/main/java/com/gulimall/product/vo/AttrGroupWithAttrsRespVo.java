package com.gulimall.product.vo;

import com.gulimall.product.entity.AttrEntity;
import lombok.Data;

import java.util.List;

/**
 * 属性分组级 分组下的所有属性
 * @author aqiang9  2020-08-01
 */
@Data
public class AttrGroupWithAttrsRespVo {
    /**
     * 分组id
     */
    private Long attrGroupId;
    /**
     * 组名
     */
    private String attrGroupName;
    /**
     * 排序
     */
    private Integer sort;
    /**
     * 描述
     */
    private String descript;
    /**
     * 组图标
     */
    private String icon;
    /**
     * 所属分类id
     */
    private Long categoryId;


    private List<AttrEntity> attrs ;
}
