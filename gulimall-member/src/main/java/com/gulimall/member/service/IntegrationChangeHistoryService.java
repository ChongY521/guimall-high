package com.gulimall.member.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gulimall.member.entity.IntegrationChangeHistoryEntity;
import com.gulimall.service.utils.PageUtils;

import java.util.Map;

/**
 * 积分变化历史记录
 *
 * @author aqiang9
 * @email ${email}
 * @date 2020-07-27 10:19:25
 */
public interface IntegrationChangeHistoryService extends IService<IntegrationChangeHistoryEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

