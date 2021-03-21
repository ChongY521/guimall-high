package com.gulimall.product.convert;

import com.gulimall.product.entity.SpuCommentEntity;
import com.gulimall.product.vo.SpuCommentVo;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @author aqiang9  2020-07-29
 */
@Mapper
public interface SpuCommentConvert {
    SpuCommentConvert INSTANCE = Mappers.getMapper(SpuCommentConvert.class);

    SpuCommentVo entity2vo(SpuCommentEntity entity);

    SpuCommentEntity vo2entity(SpuCommentVo dto);
}
