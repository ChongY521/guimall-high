package com.gulimall.member.convert;

import com.gulimall.member.entity.MemberEntity;
import com.gulimall.member.vo.MemberRegisterVo;
import com.gulimall.member.vo.MemberVo;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @author aqiang9  2020-09-02
 */
@Mapper
public interface MemberConvert {
    MemberConvert INSTANCE = Mappers.getMapper(MemberConvert.class);

    MemberEntity vo2entity(MemberRegisterVo registerVo);

    MemberVo entity2vo(MemberEntity memberEntity);

}
