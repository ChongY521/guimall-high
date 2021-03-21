package com.gulimall.product.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gulimall.common.vo.PageVo;
import com.gulimall.product.convert.AttrConvert;
import com.gulimall.product.dao.AttrGroupDao;
import com.gulimall.product.entity.AttrEntity;
import com.gulimall.product.entity.AttrGroupEntity;
import com.gulimall.product.service.AttrAttrgroupRelationService;
import com.gulimall.product.service.AttrGroupService;
import com.gulimall.product.service.AttrService;
import com.gulimall.product.service.CategoryService;
import com.gulimall.product.vo.AttrGroupVo;
import com.gulimall.product.vo.AttrGroupWithAttrsRespVo;
import com.gulimall.product.vo.SpuItemAttrGroupVo;
import com.gulimall.service.utils.PageUtils;
import com.gulimall.service.utils.QueryPage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.stream.Collectors;


@Service("attrGroupService")
public class AttrGroupServiceImpl extends ServiceImpl<AttrGroupDao, AttrGroupEntity> implements AttrGroupService {

    @Autowired
    CategoryService categoryService;
    @Autowired
    AttrAttrgroupRelationService attrAttrgroupRelationService;
    @Autowired
    AttrService attrService;


    @Override
    public PageUtils queryPage(PageVo pageParam, Long categoryId) {
        LambdaQueryWrapper<AttrGroupEntity> wrapper = new LambdaQueryWrapper<>();
        if (categoryId != 0L) {
            wrapper.eq(AttrGroupEntity::getCategoryId, categoryId);
        }
        if (pageParam != null && !StringUtils.isEmpty(pageParam.getKey())) {
            String key = pageParam.getKey();
            wrapper.and((e) -> {
                e.eq(AttrGroupEntity::getAttrGroupId, key).or().likeRight(AttrGroupEntity::getAttrGroupName, key);
            });
        }
        IPage<AttrGroupEntity> page = this.page(new QueryPage<AttrGroupEntity>().getPage(pageParam), wrapper);
        return new PageUtils(page);
    }

    @Override
    public PageUtils queryAttrRelationPage(PageVo pageParams, Long attrGroupId) {
//        new LambdaQueryWrapper<>()
//
//        baseMapper.selectPage() ;

        return null;
    }

    @Override
    public AttrGroupVo getAttrGroupInfo(Long attrGroupId) {
        AttrGroupEntity attrGroup = baseMapper.selectById(attrGroupId);
        List<Long> categoryPath = categoryService.findCategoryPath(attrGroup.getCategoryId());
        AttrGroupVo attrGroupVo = AttrConvert.INSTANCE.entity2vo(attrGroup);
        attrGroupVo.setCategoryPath(categoryPath);
        return attrGroupVo;
    }

    @Override
    public List<AttrGroupEntity> getAllGroupByCategoryId(Long categoryId) {
        return baseMapper.selectList(
                new LambdaQueryWrapper<AttrGroupEntity>()
                        .eq(AttrGroupEntity::getCategoryId, categoryId));
    }

    @Transactional
    @Override
    public void removeGroup(List<Long> attrGroupIds) {
        baseMapper.deleteBatchIds(attrGroupIds);
        attrAttrgroupRelationService.removeByAttrGroupIds(attrGroupIds);
    }

    /**
     * 获取分类下所有分组&关联属性
     */
    @Override
    public List<AttrGroupWithAttrsRespVo> getAttrGroupWithAttrByCategoryId(Long categoryId) {
        List<AttrGroupEntity> allGroupByCategoryId = getAllGroupByCategoryId(categoryId);
        // TODO 查询的时候  0 2 都需要查询  基本属性 和 及时销售属性 也是基本属性
        return allGroupByCategoryId.stream().map(
                attrGroup -> {
                    AttrGroupWithAttrsRespVo respVo = AttrConvert.INSTANCE.entity2respVo(attrGroup);
                    // 获取当前分组下的所有属性

                    List<AttrEntity> attrEntities = attrService.getAttrRelationByAttrGroupId(attrGroup.getAttrGroupId());
                    respVo.setAttrs(attrEntities);
                    return respVo;
                }
        ).collect(Collectors.toList());
    }

    @Override
    public List<SpuItemAttrGroupVo> getAttrGroupWithAttrsBySpuId(Long spuId, Long categoryId) {
//        查出当前spu对应的属性的分组信息以及 分组下所有属性的信息

        // 查看所有分组 通过 categoryId 三级分类Id
        System.out.println("spuId:"+spuId + ":::  categoryId:"+ categoryId );
      return   baseMapper.selectAttrGroupWithAttrsBySpuId(spuId,categoryId) ;
    }
}