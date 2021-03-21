package com.gulimall.product.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gulimall.common.vo.PageVo;
import com.gulimall.product.convert.BrandConvert;
import com.gulimall.product.dao.BrandDao;
import com.gulimall.product.entity.BrandEntity;
import com.gulimall.product.service.BrandService;
import com.gulimall.product.service.CategoryBrandRelationService;
import com.gulimall.product.vo.BrandVo;
import com.gulimall.service.utils.PageUtils;
import com.gulimall.service.utils.QueryPage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;


@Service("brandService")
public class BrandServiceImpl extends ServiceImpl<BrandDao, BrandEntity> implements BrandService {
    @Autowired
    private CategoryBrandRelationService categoryBrandRelationService;

    @Override
    public PageUtils queryPage(PageVo pageParams) {
        LambdaQueryWrapper<BrandEntity> wrapper = new LambdaQueryWrapper<>();

        if (!StringUtils.isEmpty(pageParams.getKey())) {
            wrapper.eq(BrandEntity::getBrandId, pageParams.getKey())
                    .or()
                    .likeRight(BrandEntity::getName, pageParams.getKey());
        }

        IPage<BrandEntity> page = this.page(new QueryPage<BrandEntity>().getPage(pageParams), wrapper);

        System.out.println(page);
        return new PageUtils(page);
    }

    @Override
    public void updateDetail(BrandVo brandVo) {
        BrandEntity brandEntity = BrandConvert.INSTANCE.vo2entity(brandVo);
        baseMapper.updateById(brandEntity);
        //TODO 更新品牌关联数据

        if (!StringUtils.isEmpty(        brandVo.getName() )) {
            categoryBrandRelationService.updateCategoryName(brandEntity.getBrandId(), brandEntity.getName());
        }

    }
}