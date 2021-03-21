package com.gulimall.product.convert;

import com.gulimall.product.entity.BrandEntity;
import com.gulimall.product.entity.CategoryBrandRelationEntity;
import com.gulimall.product.vo.BrandRespVo;
import com.gulimall.product.vo.BrandVo;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * @author aqiang9  2020-07-29
 */
@Mapper
public interface BrandConvert {
    BrandConvert INSTANCE = Mappers.getMapper(BrandConvert.class) ;
    BrandVo entity2vo(BrandEntity entity) ;
    BrandEntity vo2entity(BrandVo dto) ;

    List<BrandRespVo> entity2vo(List<CategoryBrandRelationEntity> brandList);
}
