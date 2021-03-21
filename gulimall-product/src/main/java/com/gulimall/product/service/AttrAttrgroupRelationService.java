package com.gulimall.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gulimall.product.entity.AttrAttrgroupRelationEntity;
import com.gulimall.service.utils.PageUtils;

import java.util.List;
import java.util.Map;

/**
 * 属性&属性分组关联
 *
 * @author aqiang9
 * @email 2903780002@qq.com
 * @date 2020-06-10 11:26:28
 */
public interface AttrAttrgroupRelationService extends IService<AttrAttrgroupRelationEntity> {

    PageUtils queryPage(Map<String, Object> params);
    AttrAttrgroupRelationEntity queryByAttrId(Long  attrId) ;

    /**
     * 通过attrId 更新关联关系
     * @param attrAttrgroupRelationEntity 更新的数据
     */
    void updateRelationByAttrId(AttrAttrgroupRelationEntity attrAttrgroupRelationEntity);

    void removeByAttrIds(List<Long> attrIds);

    List<AttrAttrgroupRelationEntity> getByAttrGroupId(Long attrGroupId);

    /**
     * 批量删除关联关系
     * @param attrgroupRelationEntities 关联信息
     */
    void removeBranchRelation(List<AttrAttrgroupRelationEntity> attrgroupRelationEntities);

    List<AttrAttrgroupRelationEntity>  getByAttrGroupIds(List<Long> allGroupIds);

    /**
     * 通过属性分组id 删除关联关系
     * @param attrGroupIds  通过属性分组id
     */
    void removeByAttrGroupIds(List<Long> attrGroupIds);
}

