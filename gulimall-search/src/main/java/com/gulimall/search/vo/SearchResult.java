package com.gulimall.search.vo;

import com.gulimall.common.to.es.SkuEsModel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

/**
 * @author aqiang9  2020-08-19
 */
@Getter
@Setter
@ToString
public class SearchResult {
    // 查询到的所有商品
    private List<SkuEsModel> products;
    /**
     * 分页信息
     */
    private Integer pageNum; // 当前页码
    private Long total; //  总记录数
    private Integer totalPages; // 总页码数

    private List<BrandVo> brands; // 当前查询到的结果，所涉及到的品牌

    private List<CategoryVo> categories; // 所有分类
    private List<AttrVo> attrs;//所有涉及到的属性
    //  ===================查询结果==================

}
