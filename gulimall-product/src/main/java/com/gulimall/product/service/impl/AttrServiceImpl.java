package com.gulimall.product.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gulimall.common.constant.ProductConstant;
import com.gulimall.common.vo.PageVo;
import com.gulimall.product.convert.AttrConvert;
import com.gulimall.product.dao.AttrDao;
import com.gulimall.product.entity.AttrAttrgroupRelationEntity;
import com.gulimall.product.entity.AttrEntity;
import com.gulimall.product.entity.AttrGroupEntity;
import com.gulimall.product.entity.CategoryEntity;
import com.gulimall.product.service.AttrAttrgroupRelationService;
import com.gulimall.product.service.AttrGroupService;
import com.gulimall.product.service.AttrService;
import com.gulimall.product.service.CategoryService;
import com.gulimall.product.vo.AttrGroupRelationVo;
import com.gulimall.common.vo.AttrRespVo;
import com.gulimall.common.vo.AttrVo;
import com.gulimall.service.utils.PageUtils;
import com.gulimall.service.utils.QueryPage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.stream.Collectors;


@Service("attrService")
@CacheConfig(cacheNames = "product:attr")
public class AttrServiceImpl extends ServiceImpl<AttrDao, AttrEntity> implements AttrService {

    @Autowired
    AttrAttrgroupRelationService attrAttrgroupRelationService;

    @Autowired
    AttrGroupService attrGroupService;

    @Autowired
    CategoryService categoryService;

    @Override
    public PageUtils<AttrVo> queryList(PageVo pageParams, int attrType, Long categoryId) {
        LambdaQueryWrapper<AttrEntity> wrapper = new LambdaQueryWrapper<>();
        if (categoryId != null && categoryId != 0) {
            wrapper.eq(AttrEntity::getCategoryId, categoryId);
        }
        if (attrType != 2) {
            wrapper.eq(AttrEntity::getAttrType, attrType);
        }
        if (!StringUtils.isEmpty(pageParams.getKey())) {
            wrapper.and(w -> {
                w.eq(AttrEntity::getAttrId, pageParams.getKey())
                        .or()
                        .likeRight(AttrEntity::getAttrName, pageParams.getKey());
            });
        }
        IPage<AttrEntity> attrEntities = baseMapper.selectPage(new QueryPage<AttrEntity>().getPage(pageParams), wrapper);
        PageUtils pageData = new PageUtils(attrEntities);

        // 还要继续封装   分组名 分类名   等相关信息
        List<AttrEntity> records = attrEntities.getRecords();

        final List<AttrVo> collect = records.stream().map(item -> {
            AttrRespVo attrVo = AttrConvert.INSTANCE.entity2respVo(item);
            // 获取  分组 名
            AttrAttrgroupRelationEntity attrAttrgroupRelationEntity = attrAttrgroupRelationService.queryByAttrId(item.getAttrId());
            if (attrAttrgroupRelationEntity != null && attrAttrgroupRelationEntity.getAttrGroupId() != null) {
                AttrGroupEntity attrGroupEntity = attrGroupService.getById(attrAttrgroupRelationEntity.getAttrGroupId());
                if (attrGroupEntity != null) {
                    attrVo.setAttrGroupName(attrGroupEntity.getAttrGroupName());
                }
            }
            // 获取 分类名
            if (item.getCategoryId() != null) {
                CategoryEntity categoryEntity = categoryService.getById(item.getCategoryId());
                if (categoryEntity != null) {
                    attrVo.setCategoryName(categoryEntity.getName());
                }
            }
            return attrVo;
        }).collect(Collectors.toList());
        // 替换
        pageData.setList(collect);
        return pageData;
    }

    @Transactional
    @Override
    public void saveAttrInfo(AttrVo attrVo) {
        AttrEntity attrEntity = AttrConvert.INSTANCE.vo2entity(attrVo);
        baseMapper.insert(attrEntity);

        // 保存关联关系
        if (attrVo.getAttrType() == ProductConstant.BASE_ATTR_TYPE && attrVo.getAttrGroupId() != null) {
            AttrAttrgroupRelationEntity attrAttrgroupRelationEntity = new AttrAttrgroupRelationEntity();
            attrAttrgroupRelationEntity.setAttrId(attrEntity.getAttrId());
            attrAttrgroupRelationEntity.setAttrGroupId(attrVo.getAttrGroupId());
            attrAttrgroupRelationService.save(attrAttrgroupRelationEntity);
        }
    }

    @Cacheable(key = "'info:'+#attrId" , sync = true)
    @Override
    public AttrRespVo getAttrInfo(Long attrId) {
        AttrEntity attrEntity = baseMapper.selectById(attrId);
        AttrRespVo attrRespVo = AttrConvert.INSTANCE.entity2respVo(attrEntity);
        // 查询分组信息
        AttrAttrgroupRelationEntity attrAttrgroupRelation = attrAttrgroupRelationService.queryByAttrId(attrId);
        // 分组只有 基本属性
        if (attrAttrgroupRelation != null && attrEntity.getAttrType() == ProductConstant.BASE_ATTR_TYPE) {
            attrRespVo.setAttrGroupId(attrAttrgroupRelation.getAttrGroupId());
            AttrGroupEntity attrGroup = attrGroupService.getById(attrAttrgroupRelation.getAttrGroupId());
            if (attrGroup != null) {
                attrRespVo.setAttrGroupName(attrGroup.getAttrGroupName());
            }
        }
        // 查询分类信息
        CategoryEntity categoryEntity = categoryService.getById(attrEntity.getCategoryId());
        if (categoryEntity != null) {
            attrRespVo.setCategoryName(categoryEntity.getName());
        }
        // 获取完整路径
        List<Long> categoryPath = categoryService.findCategoryPath(attrEntity.getCategoryId());
        attrRespVo.setCategoryPath(categoryPath);
        return attrRespVo;
    }

    @Transactional
    @Override
    public void updateAttrInfo(AttrVo attrVo) {
        // 修改自身数据
        AttrEntity attrEntity = AttrConvert.INSTANCE.vo2entity(attrVo);
        baseMapper.updateById(attrEntity);
        // 修改关联数据
        // 修改分组关联   基本属性 才有分组信息
        if (attrVo.getAttrType() == ProductConstant.BASE_ATTR_TYPE) {
            AttrAttrgroupRelationEntity attrAttrgroupRelationEntity = new AttrAttrgroupRelationEntity();
            attrAttrgroupRelationEntity.setAttrId(attrVo.getAttrId());
            attrAttrgroupRelationEntity.setAttrGroupId(attrVo.getAttrGroupId());
            attrAttrgroupRelationService.updateRelationByAttrId(attrAttrgroupRelationEntity);
        }
    }
    @Override
    public void removeAttrInfo(List<Long> attrIds, Long attrType) {
        //
        baseMapper.deleteBatchIds(attrIds);
        // 删除与分组的关联
        // 通过attrType  来判断是否进行删除关联关系
        if (attrType == ProductConstant.BASE_ATTR_TYPE) {
            attrAttrgroupRelationService.removeByAttrIds(attrIds);
        }
    }

    @Override
    public List<AttrEntity> getAttrRelationByAttrGroupId(Long attrGroupId) {
        // 通过关联表过去到 此分类
//        AttrAttrgroupRelationEntity byId = attrAttrgroupRelationService.getById(attrGroupId);
        List<AttrAttrgroupRelationEntity> attrgroupRelationEntities = attrAttrgroupRelationService.getByAttrGroupId(attrGroupId);
        if (attrgroupRelationEntities != null) {
            List<Long> attrIds = attrgroupRelationEntities.stream().map(AttrAttrgroupRelationEntity::getAttrId).collect(Collectors.toList());
            if (attrIds.size() > 0) {
                return baseMapper.selectBatchIds(attrIds);
            }
        }
        return null;
    }

    @Override
    public void removeRelation(List<AttrGroupRelationVo> relationVo) {

        List<AttrAttrgroupRelationEntity> attrgroupRelationEntities = AttrConvert.INSTANCE.listVo2listEntity(relationVo);

        attrAttrgroupRelationService.removeBranchRelation(attrgroupRelationEntities);
    }

    @Override
    public PageUtils getNoRelationAttr(PageVo pageParams, Long attrGroupId) {
        // 本分类 没有关联的属性

        // 1 、只能是当前 分类 的属性
        AttrGroupEntity attrGroupEntity = attrGroupService.getById(attrGroupId);
        Long categoryId = attrGroupEntity.getCategoryId();

        // 查到所有 分组
        // 2、当前分组只能关联别的分组没有引用的 属性
        // 2.1 通过分类id 查询当前分类 所有分组
        List<AttrGroupEntity> allGroupByCategory = attrGroupService.getAllGroupByCategoryId(categoryId);
        List<Long> allGroupIds = allGroupByCategory.stream().map(AttrGroupEntity::getAttrGroupId).collect(Collectors.toList());
//        2.2 这些分组的所有属性
        List<AttrAttrgroupRelationEntity> attrgroupRelation = attrAttrgroupRelationService.getByAttrGroupIds(allGroupIds);
        List<Long> attrIds = attrgroupRelation.stream().map(AttrAttrgroupRelationEntity::getAttrId).collect(Collectors.toList());

//        2。3 从当前分类的所有属性中移除这些属性
        LambdaQueryWrapper<AttrEntity> wrapper = new LambdaQueryWrapper<AttrEntity>()
                .eq(AttrEntity::getCategoryId, categoryId)
                .eq(AttrEntity::getAttrType, ProductConstant.BASE_ATTR_TYPE);
        if (attrIds.size() > 0) {
            wrapper.notIn(AttrEntity::getAttrId, attrIds);
        }
        // 拼装检索信息
        if (!StringUtils.isEmpty(pageParams.getKey())) {
            wrapper.and(w -> {
                w.eq(AttrEntity::getAttrId, pageParams.getKey())
                        .or()
                        .likeRight(AttrEntity::getAttrName, pageParams.getKey());
            });
        }
        IPage<AttrEntity> attrEntityIPage = baseMapper.selectPage(new QueryPage<AttrEntity>().getPage(pageParams),
                wrapper);
        return new PageUtils(attrEntityIPage);


    }

    @Override
    public List<Long> getSearchAttrIds(List<Long> attrValueIds) {
        return baseMapper.selectSearchAttrs(attrValueIds);

    }
}