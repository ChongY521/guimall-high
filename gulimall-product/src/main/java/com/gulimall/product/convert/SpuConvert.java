package com.gulimall.product.convert;

import com.gulimall.product.entity.SpuInfoEntity;
import com.gulimall.product.vo.SpuSaveVo;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @author aqiang9  2020-08-01
 */

@Mapper
public interface SpuConvert {
    SpuConvert INSTANCE = Mappers.getMapper(SpuConvert.class);

    SpuInfoEntity vo2entity(SpuSaveVo spuSaveVo);
}
