package com.gulimall.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gulimall.common.vo.PageVo;
import com.gulimall.product.entity.AttrGroupEntity;
import com.gulimall.product.vo.AttrGroupVo;
import com.gulimall.product.vo.AttrGroupWithAttrsRespVo;
import com.gulimall.product.vo.SpuItemAttrGroupVo;
import com.gulimall.service.utils.PageUtils;

import java.util.List;

/**
 * 属性分组
 *
 * @author aqiang9
 * @email 2903780002@qq.com
 * @date 2020-06-10 11:26:28
 */
public interface AttrGroupService extends IService<AttrGroupEntity> {


    PageUtils queryPage(PageVo pageParam, Long categoryId);


    PageUtils queryAttrRelationPage(PageVo pageParams, Long attrGroupId);

    AttrGroupVo getAttrGroupInfo(Long attrGroupId);

    /**
     * 通过分类id 查询 当前分类 下  所有分组
     *
     * @param categoryId 分类id
     * @return List<AttrGroupEntity> 分类的集合
     */
    List<AttrGroupEntity> getAllGroupByCategoryId(Long categoryId);

    /**
     *删除分组以及对应的关联关系
     */
    void removeGroup(List<Long> attrGroupIds);

    List<AttrGroupWithAttrsRespVo> getAttrGroupWithAttrByCategoryId(Long categoryId);

    /**
     * 通过spuId 获取属性分组下的所有属性
     *
     * @param spuId 商品id
     * @param categoryId 分类id
     * @return
     */
    List<SpuItemAttrGroupVo> getAttrGroupWithAttrsBySpuId(Long spuId, Long categoryId);
}

