package com.gulimall.member.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gulimall.common.vo.PageVo;
import com.gulimall.member.dao.MemberLevelDao;
import com.gulimall.member.entity.MemberLevelEntity;
import com.gulimall.member.service.MemberLevelService;
import com.gulimall.service.utils.PageUtils;
import com.gulimall.service.utils.QueryPage;
import org.springframework.stereotype.Service;


@Service("memberLevelService")
public class MemberLevelServiceImpl extends ServiceImpl<MemberLevelDao, MemberLevelEntity> implements MemberLevelService {

    @Override
    public PageUtils queryPage(PageVo pageParams) {
        IPage<MemberLevelEntity> page = this.page(
                new QueryPage<MemberLevelEntity>().getPage(pageParams),
                new QueryWrapper<MemberLevelEntity>()
        );
        return new PageUtils(page);
    }

    @Override
    public MemberLevelEntity getDefaultLevel() {
        return baseMapper.selectOne(new LambdaQueryWrapper<MemberLevelEntity>().eq(MemberLevelEntity::getDefaultStatus, 1));
    }

}