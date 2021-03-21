package com.gulimall.product.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gulimall.common.vo.PageVo;
import com.gulimall.product.dao.CategoryBrandRelationDao;
import com.gulimall.product.entity.BrandEntity;
import com.gulimall.product.entity.CategoryBrandRelationEntity;
import com.gulimall.product.entity.CategoryEntity;
import com.gulimall.product.service.BrandService;
import com.gulimall.product.service.CategoryBrandRelationService;
import com.gulimall.product.service.CategoryService;
import com.gulimall.product.vo.BrandRespVo;
import com.gulimall.service.utils.PageUtils;
import com.gulimall.service.utils.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service("categoryBrandRelationService")
public class CategoryBrandRelationServiceImpl extends ServiceImpl<CategoryBrandRelationDao, CategoryBrandRelationEntity> implements CategoryBrandRelationService {

    @Autowired
    BrandService brandService;
    @Autowired
    CategoryService categoryService;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<CategoryBrandRelationEntity> page = this.page(
                new Query<CategoryBrandRelationEntity>().getPage(params),
                new QueryWrapper<CategoryBrandRelationEntity>()
        );
        return new PageUtils(page);
    }

    @Override
    public List<CategoryBrandRelationEntity> categoryBrandList(PageVo pageParams, Long brandId) {
        //TODO 处理分页

        LambdaQueryWrapper<CategoryBrandRelationEntity> wrapper = new LambdaQueryWrapper<>();

        wrapper.eq(CategoryBrandRelationEntity::getBrandId, brandId);
        List<CategoryBrandRelationEntity> categoryBrandRelationList = baseMapper.selectList(wrapper);
        return categoryBrandRelationList;
    }

    @Override
    public void saveRelationDetail(CategoryBrandRelationEntity categoryBrandRelation) {
        // 获取品牌 名 ， 菜单名
        BrandEntity brandEntity = brandService.getById(categoryBrandRelation.getBrandId());
        CategoryEntity categoryEntity = categoryService.getById(categoryBrandRelation.getCategoryId());

        categoryBrandRelation.setBrandName(brandEntity.getName());
        categoryBrandRelation.setCategoryName(categoryEntity.getName());

        // 保存
        baseMapper.insert(categoryBrandRelation);
    }
    @Override
    public void updateBrandName(Long branId, String brandName) {
        CategoryBrandRelationEntity relationEntity = new CategoryBrandRelationEntity();
        relationEntity.setBrandId(branId);
        relationEntity.setBrandName(brandName);
        LambdaUpdateWrapper<CategoryBrandRelationEntity> wrapper = new LambdaUpdateWrapper<>();
        wrapper.eq(CategoryBrandRelationEntity::getBrandId, branId);
        baseMapper.update(relationEntity, wrapper);
    }
    @Override
    public void updateCategoryName(Long categoryId, String categoryName) {
        CategoryBrandRelationEntity relationEntity = new CategoryBrandRelationEntity();
        relationEntity.setCategoryId(categoryId);
        relationEntity.setCategoryName(categoryName);
        LambdaUpdateWrapper<CategoryBrandRelationEntity> wrapper = new LambdaUpdateWrapper<>();
        wrapper.eq(CategoryBrandRelationEntity::getCategoryId, categoryId);
        baseMapper.update(relationEntity, wrapper);
    }

    @Override
    public List<CategoryBrandRelationEntity> getBrandListByCategoryId(Long categoryId) {

        return baseMapper.selectList(new LambdaQueryWrapper<CategoryBrandRelationEntity>()
                .eq(CategoryBrandRelationEntity::getCategoryId, categoryId));




    }
}