package com.gulimall.product.vo;

import com.gulimall.product.entity.SkuImagesEntity;
import com.gulimall.product.entity.SkuInfoEntity;
import com.gulimall.product.entity.SpuInfoDescEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

/**
 * @author aqiang9  2020-08-24
 */
@Getter
@Setter
@ToString
public class SkuItemVo {
    //        1 、 sku详细信息  sku_info
    private SkuInfoEntity info;
    private boolean hasStock = true ;
    //        2、sku 图片信息  sku_img
    private List<SkuImagesEntity> images;
    //        3、spu 销售属性组合
    private List<SkuItemSaleAttrVo> saleAttr;
    //        4、spu 的介绍
    private SpuInfoDescEntity desc;

//        5、规格参数信息
    private List<SpuItemAttrGroupVo> groupAttrs;
}
