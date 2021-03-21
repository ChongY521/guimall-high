package com.gulimall.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gulimall.common.vo.PageVo;
import com.gulimall.product.entity.CategoryBrandRelationEntity;
import com.gulimall.service.utils.PageUtils;

import java.util.List;
import java.util.Map;

/**
 * 品牌分类关联
 *
 * @author aqiang9
 * @email 2903780002@qq.com
 * @date 2020-06-10 11:26:28
 */
public interface CategoryBrandRelationService extends IService<CategoryBrandRelationEntity> {

    PageUtils queryPage(Map<String, Object> params);

    /**
     * 品牌id 获取 分类
     * @param pageParams 分页参数
     * @param brandId 品牌 id
     */
    List<CategoryBrandRelationEntity> categoryBrandList(PageVo pageParams, Long brandId);

    void saveRelationDetail(CategoryBrandRelationEntity categoryBrandRelation);

    /**
     * 更新 品牌名
     * @param branId 品牌id
     * @param brandName 品牌名
     */
    void updateBrandName(Long branId, String brandName);

    /**
     * 更新 分类名
     * @param categoryId 分类id
     * @param categoryName 分类名
     */
    void updateCategoryName(Long categoryId, String categoryName);

    List<CategoryBrandRelationEntity> getBrandListByCategoryId(Long categoryId);
}

