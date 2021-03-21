package com.gulimall.member.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gulimall.member.entity.MemberStatisticsInfoEntity;
import com.gulimall.service.utils.PageUtils;

import java.util.Map;

/**
 * 会员统计信息
 *
 * @author aqiang9
 * @email ${email}
 * @date 2020-07-27 10:19:25
 */
public interface MemberStatisticsInfoService extends IService<MemberStatisticsInfoEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

