package com.gulimall.product.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gulimall.product.dao.AttrAttrgroupRelationDao;
import com.gulimall.product.entity.AttrAttrgroupRelationEntity;
import com.gulimall.product.service.AttrAttrgroupRelationService;
import com.gulimall.service.utils.PageUtils;
import com.gulimall.service.utils.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;


@Service("attrAttrgroupRelationService")
public class AttrAttrgroupRelationServiceImpl extends ServiceImpl<AttrAttrgroupRelationDao, AttrAttrgroupRelationEntity> implements AttrAttrgroupRelationService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<AttrAttrgroupRelationEntity> page = this.page(
                new Query<AttrAttrgroupRelationEntity>().getPage(params),
                new QueryWrapper<AttrAttrgroupRelationEntity>()
        );
        return new PageUtils(page);
    }
    @Override
    public AttrAttrgroupRelationEntity queryByAttrId(Long attrId) {
        return baseMapper.selectOne(new LambdaQueryWrapper<AttrAttrgroupRelationEntity>().eq(AttrAttrgroupRelationEntity::getAttrId, attrId));
    }

    @Override
    public void updateRelationByAttrId(AttrAttrgroupRelationEntity attrAttrgroupRelationEntity) {
        this.saveOrUpdate(attrAttrgroupRelationEntity,
                new LambdaUpdateWrapper<AttrAttrgroupRelationEntity>()
                        .eq(AttrAttrgroupRelationEntity::getAttrId, attrAttrgroupRelationEntity.getAttrId()));
    }

    @Transactional
    @Override
    public void removeByAttrIds(List<Long> attrIds) {
        baseMapper.delete(
                new LambdaQueryWrapper<AttrAttrgroupRelationEntity>()
                        .in(AttrAttrgroupRelationEntity::getAttrId, attrIds)
        );
    }

    @Override
    public List<AttrAttrgroupRelationEntity> getByAttrGroupId(Long attrGroupId) {
        return baseMapper.selectList(
                new LambdaQueryWrapper<AttrAttrgroupRelationEntity>()
                .eq(AttrAttrgroupRelationEntity::getAttrGroupId, attrGroupId));
    }

    @Override
    public void removeBranchRelation(List<AttrAttrgroupRelationEntity> attrgroupRelationEntities) {
        baseMapper.removeBranchRelation(attrgroupRelationEntities) ;
    }

    @Override
    public List<AttrAttrgroupRelationEntity> getByAttrGroupIds(List<Long> allGroupIds) {
        return baseMapper.selectList(
                new LambdaQueryWrapper<AttrAttrgroupRelationEntity>()
                        .in(AttrAttrgroupRelationEntity::getAttrGroupId, allGroupIds));
    }

    @Override
    public void removeByAttrGroupIds(List<Long> attrGroupIds) {
        baseMapper.delete( new LambdaQueryWrapper<AttrAttrgroupRelationEntity>()
                .in(AttrAttrgroupRelationEntity::getAttrGroupId, attrGroupIds));
    }
}