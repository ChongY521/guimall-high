package com.gulimall.product.convert;

import com.gulimall.common.to.ProductAttrTo;
import com.gulimall.common.vo.AttrRespVo;
import com.gulimall.common.vo.AttrVo;
import com.gulimall.common.to.es.Attrs;
import com.gulimall.product.entity.AttrAttrgroupRelationEntity;
import com.gulimall.product.entity.AttrEntity;
import com.gulimall.product.entity.AttrGroupEntity;
import com.gulimall.product.entity.ProductAttrValueEntity;
import com.gulimall.product.vo.*;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * @author aqiang9  2020-07-30
 */
@Mapper
public interface AttrConvert {
    AttrConvert INSTANCE = Mappers.getMapper(AttrConvert.class);

    //    Attr
    AttrEntity vo2entity(AttrVo vo);

    AttrVo entity2vo(AttrEntity item);

    AttrRespVo entity2respVo(AttrEntity item);

    //  attrGroup---
    AttrGroupEntity vo2entity(AttrGroupVo vo);

    AttrGroupVo entity2vo(AttrGroupEntity entity);

    List<AttrAttrgroupRelationEntity> listVo2listEntity(List<AttrGroupRelationVo> vo);


    AttrGroupWithAttrsRespVo entity2respVo(AttrGroupEntity attrGroup);

    Attrs entity2model(ProductAttrValueEntity e);

    ProductAttrTo entity2to(AttrEntity attrEntity);
}
