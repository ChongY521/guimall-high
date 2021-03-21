package com.gulimall.product.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gulimall.product.entity.AttrGroupEntity;
import com.gulimall.product.vo.SpuItemAttrGroupVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 属性分组
 * 
 * @author aqiang9
 * @email 2903780002@qq.com
 * @date 2020-06-10 11:26:28
 */
@Mapper
public interface AttrGroupDao extends BaseMapper<AttrGroupEntity> {

    List<SpuItemAttrGroupVo> selectAttrGroupWithAttrsBySpuId(@Param("spuId") Long spuId, @Param("categoryId") Long categoryId);
}
