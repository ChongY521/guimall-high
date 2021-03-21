package com.gulimall.ware.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gulimall.common.vo.PageVo;
import com.gulimall.service.utils.PageUtils;
import com.gulimall.service.utils.QueryPage;
import com.gulimall.ware.dao.WareInfoDao;
import com.gulimall.ware.entity.WareInfoEntity;
import com.gulimall.ware.service.WareInfoService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;


@Service("wareInfoService")
public class WareInfoServiceImpl extends ServiceImpl<WareInfoDao, WareInfoEntity> implements WareInfoService {

    @Override
    public PageUtils queryPage(PageVo params) {
        LambdaQueryWrapper<WareInfoEntity> wrapper = new LambdaQueryWrapper<>();
        String key = params.getKey();
        if (!StringUtils.isEmpty(key) ){
            wrapper.eq(WareInfoEntity::getId ,key).or()
                    .like(WareInfoEntity::getName , key)
                    .or()
                    .like(WareInfoEntity::getAddress , key)
                    .or()
                    .like(WareInfoEntity::getAreacode ,key ) ;
        }
        IPage<WareInfoEntity> page = this.page(
                new QueryPage<WareInfoEntity>().getPage(params),
                wrapper
        );
        return new PageUtils(page);
    }

}