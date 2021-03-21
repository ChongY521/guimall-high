package com.gulimall.product.convert;

import com.gulimall.common.to.SkuInfoTo;
import com.gulimall.common.to.SkuReductionTo;
import com.gulimall.common.to.es.SkuEsModel;
import com.gulimall.product.entity.SkuImagesEntity;
import com.gulimall.product.entity.SkuInfoEntity;
import com.gulimall.product.entity.SkuSaleAttrValueEntity;
import com.gulimall.product.vo.SpuSaveAttr;
import com.gulimall.product.vo.SpuSaveImage;
import com.gulimall.product.vo.SpuSaveSku;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

/**
 * @author aqiang9  2020-08-02
 */
@Mapper
public interface SkuConvert {
    SkuConvert INSTANCE = Mappers.getMapper(SkuConvert.class) ;

    SkuInfoEntity vo2entity(SpuSaveSku sku);

    SkuImagesEntity vo2entity(SpuSaveImage img);

    SkuSaleAttrValueEntity vo2entity(SpuSaveAttr item);

    SkuReductionTo vo2to(SpuSaveSku sku);

    SkuInfoTo entity2to(SkuInfoEntity skuInfo);

    /**
     * 配置 属性不对等 的 地方
     * @param skuInfoEntity
     * @return
     */

    @Mapping(source = "price" , target = "skuPrice")
    @Mapping(source = "skuDefaultImg" , target = "skuImg")
    SkuEsModel entity2model(SkuInfoEntity skuInfoEntity);

}
