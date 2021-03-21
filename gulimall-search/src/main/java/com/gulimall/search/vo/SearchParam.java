package com.gulimall.search.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

/**
 *
 *  1、全文检索：skuTitle-》keyword
 *  2、排序：saleCount（销量）、hotScore（热度分）、skuPrice（价格）
 *  3、过滤：hasStock、skuPrice区间、brandId、catalog3Id、attrs
 *  4、聚合：attrs
 *  完整查询参数
 *   keyword=小米&sort=saleCount_desc/asc&hasStock=0/1&skuPrice=400_1900&brandId=1&catalog3Id=1
 *   &attrs=1_3G:4G:5G&attrs=2_骁龙845&attrs=4_高清屏
 *
 *
 * @author aqiang9  2020-08-19
 */
@Getter
@Setter
@ToString
public class SearchParam {
    /**
     * 搜索关键字
     */
    private String keyword;
    /**
     * 三级分类id
     */
    private Long  category3Id ;
    /**
     * sort=saleCount_asc/desc
     */
    private String sort ;
    /**
     * 是否只显示有货
     */
    private Integer hasStock ; // 0 不做筛选  1 为有货
    /**
     * 价格区间   from_to
     */
    private String skuPrice ;
    /**
     * 品牌id
     */
    private List<Long> brandId ;
    /**
     * 属性
     */
    List<String> attrs ;
    /**
     * 页码
     */
    private Integer pageNum = 1;
}
