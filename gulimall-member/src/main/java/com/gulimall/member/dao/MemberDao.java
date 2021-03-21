package com.gulimall.member.dao;

import com.gulimall.member.entity.MemberEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 会员
 * 
 * @author aqiang9
 * @email ${email}
 * @date 2020-07-27 10:19:25
 */
@Mapper
public interface MemberDao extends BaseMapper<MemberEntity> {
	
}
