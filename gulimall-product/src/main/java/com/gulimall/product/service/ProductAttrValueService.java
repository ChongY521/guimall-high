package com.gulimall.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gulimall.product.entity.ProductAttrValueEntity;
import com.gulimall.service.utils.PageUtils;

import java.util.List;
import java.util.Map;

/**
 * spu属性值
 *
 * @author aqiang9
 * @email 2903780002@qq.com
 * @date 2020-06-10 11:26:28
 */
public interface ProductAttrValueService extends IService<ProductAttrValueEntity> {

    PageUtils queryPage(Map<String, Object> params);

    /**
     * 保存 属性 值
     * @param productAttrValueEntities 属性值
     */
    void saveAttrValueInfo(List<ProductAttrValueEntity> productAttrValueEntities);

    /**
     * 通过spuId 获取属性 列表
     * @param spuId spuId
     * @return List<ProductAttrValueEntity>
     */
    List<ProductAttrValueEntity> baseAttrListForSpuId(Long spuId);

    /**
     * 通过spu id 查询 所有属性 id
     * @param spuId spuId
     * @return  List<Long>
     */
    List<Long> baseAttrIdsForSpuId(Long spuId);
}


