package com.gulimall.product.convert;

import com.gulimall.product.entity.CategoryBrandRelationEntity;
import com.gulimall.product.entity.CategoryEntity;
import com.gulimall.product.vo.CategoryBrandRelationVo;
import com.gulimall.product.vo.CategoryVo;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @author aqiang9  2020-07-28
 */
@Mapper
public interface CategoryConvert {
    CategoryConvert INSTANCE = Mappers.getMapper(CategoryConvert.class);
    // category

    CategoryVo entity2vo(CategoryEntity entity);

    CategoryEntity vo2entity(CategoryVo dto);

    //  categoryBrand
    CategoryBrandRelationEntity vo2entity(CategoryBrandRelationVo dto);

}
