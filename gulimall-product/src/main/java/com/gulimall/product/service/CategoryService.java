package com.gulimall.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gulimall.product.entity.CategoryEntity;
import com.gulimall.product.vo.Category2Vo;
import com.gulimall.product.vo.CategoryVo;
import com.gulimall.service.utils.PageUtils;

import java.util.List;
import java.util.Map;

/**
 * 商品三级分类
 *
 * @author aqiang9
 * @email 2903780002@qq.com
 * @date 2020-06-10 11:26:28
 */
public interface CategoryService extends IService<CategoryEntity> {
    PageUtils queryPage(Map<String, Object> params);

    /**
     * 查询所有的分类， 并组装成父子结构
     *
     * @return
     */
    List<CategoryVo> listCategoryWithTree();

    /**
     * 通过菜单id 批量删除 菜单
     *
     * @param catIds
     */
    void removeMenuByIds(List<Long> catIds);

    /**
     * 获取 此三级 分类的 完整路径
     *
     * @param categoryId 三级分 类的 id
     * @return List<Long> 完整路径
     */
    List<Long> findCategoryPath(Long categoryId);

    /**
     * 修改 详细 信息 并 级联 更新
     * @param categoryVo 更新的信息
     */
    void updateCategoryDetail(CategoryVo categoryVo);

    /**
     * 获取一级分类
     * @return  List<CategoryEntity>
     */
    List<CategoryEntity> getCategoryLevel1();


    /**
     * 获取所有  三级 分类 并 封装成一个 map
     * @return   Map<String, Object>
     */
    Map<String,List<Category2Vo>> getCategoryJson();


}

