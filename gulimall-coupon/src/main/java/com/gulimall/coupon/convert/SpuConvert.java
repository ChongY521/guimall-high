package com.gulimall.coupon.convert;

import com.gulimall.common.to.SpuBoundTo;
import com.gulimall.coupon.entity.SpuBoundsEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @author aqiang9  2020-08-02
 */
@Mapper
public interface SpuConvert {
    SpuConvert INSTANCE = Mappers.getMapper(SpuConvert.class) ;

    SpuBoundsEntity to2entity(SpuBoundTo spuBoundTo);
}
