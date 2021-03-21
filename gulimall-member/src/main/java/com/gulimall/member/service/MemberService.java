package com.gulimall.member.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gulimall.common.exception.BusinessException;
import com.gulimall.member.entity.MemberEntity;
import com.gulimall.member.vo.SocialUser;
import com.gulimall.service.utils.PageUtils;

import java.util.Map;

/**
 * 会员
 *
 * @author aqiang9
 * @email ${email}
 * @date 2020-07-27 10:19:25
 */
public interface MemberService extends IService<MemberEntity> {

    PageUtils queryPage(Map<String, Object> params);

    void register(MemberEntity memberEntity) throws BusinessException ;


    void checkPhoneUnique(String phone) throws BusinessException;
    void checkUsernameUnique(String username)  throws BusinessException  ;

    /**
     * 登陆
     * @param loginAccount  登陆账号
     * @param password 密码
     * @return 用户信息
     */
    MemberEntity login(String loginAccount, String password);

    /**
     * 社交登陆
     * @param socialUser 用户信息
     * @return MemberEntity 用户信息
     */
    MemberEntity login(SocialUser socialUser);
}

