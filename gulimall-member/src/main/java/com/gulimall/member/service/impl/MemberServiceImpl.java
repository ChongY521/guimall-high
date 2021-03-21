package com.gulimall.member.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gulimall.common.exception.BusinessException;
import com.gulimall.member.dao.MemberDao;
import com.gulimall.member.entity.MemberEntity;
import com.gulimall.member.entity.MemberLevelEntity;
import com.gulimall.member.exception.MemberErrorCode;
import com.gulimall.member.service.MemberLevelService;
import com.gulimall.member.service.MemberService;
import com.gulimall.member.vo.SocialUser;
import com.gulimall.member.vo.WeiboSocialUserInfo;
import com.gulimall.service.utils.PageUtils;
import com.gulimall.service.utils.Query;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import java.util.Map;


@Service("memberService")
@Slf4j
public class MemberServiceImpl extends ServiceImpl<MemberDao, MemberEntity> implements MemberService {

    @Autowired
    BCryptPasswordEncoder passwordEncoder;


    @Autowired
    MemberLevelService memberLevelService;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<MemberEntity> page = this.page(
                new Query<MemberEntity>().getPage(params),
                new QueryWrapper<MemberEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public void register(MemberEntity memberEntity) throws BusinessException {
// 唯一性判断
        checkPhoneUnique(memberEntity.getMobile());
        checkUsernameUnique(memberEntity.getUsername());

        // 设置数据
        // 1、设置默认等级
        MemberLevelEntity defaultLevel = memberLevelService.getDefaultLevel();
        if (defaultLevel != null) {
            memberEntity.setLevelId(defaultLevel.getId());
        }
        memberEntity.setPassword(passwordEncoder.encode(memberEntity.getPassword()));

        // 添加用户
        baseMapper.insert(  memberEntity ) ;
    }

    @Override
    public void checkPhoneUnique(String phone) throws BusinessException {
        Integer count = baseMapper.selectCount(new LambdaQueryWrapper<MemberEntity>().eq(MemberEntity::getMobile, phone));
        if (count > 0) {
            throw new BusinessException(MemberErrorCode.PHONE_EXIST_EXCEPTION);
        }
    }

    @Override
    public void checkUsernameUnique(String username) throws BusinessException {
        Integer count = baseMapper.selectCount(new LambdaQueryWrapper<MemberEntity>().eq(MemberEntity::getUsername, username));
        if (count > 0) {
            throw new BusinessException(MemberErrorCode.USERNAME_EXIST_EXCEPTION);
        }
    }

    @Override
    public MemberEntity login(String loginAccount, String password) {

        MemberEntity memberEntity = baseMapper.selectOne(
                new LambdaQueryWrapper<MemberEntity>()
                        .eq(MemberEntity::getUsername, loginAccount)
                        .or()
                        .eq(MemberEntity::getMobile, loginAccount));
        if (memberEntity != null && passwordEncoder.matches(password, memberEntity.getPassword())) {
            return memberEntity;
        }
// 登陆失败
        return null;

    }

    @Override
    public MemberEntity login(SocialUser socialUser) {
        // TODO 修改表字段
//        1、判断是否登录过
        MemberEntity member = baseMapper.selectOne(new LambdaQueryWrapper<MemberEntity>().eq(MemberEntity::getSocialUid, socialUser.getUid()));
        if (member != null) {
            // 登陆过
            MemberEntity memberEntity = new MemberEntity();
            memberEntity.setId(member.getId());
            memberEntity.setAccessToken(socialUser.getAccess_token());
            memberEntity.setExpiresIn(socialUser.getExpires_in());
            baseMapper.updateById(memberEntity);

            member.setExpiresIn(socialUser.getExpires_in());
            member.setAccessToken(socialUser.getAccess_token());
            return member;
        }
        // 注册
        member = new MemberEntity();
        // 查询用户信息
        // access_token	true	string	采用OAuth授权方式为必填参数，OAuth授权后获得。
        //uid
        member.setSocialUid(socialUser.getUid());
        member.setAccessToken(socialUser.getAccess_token());
        member.setExpiresIn(socialUser.getExpires_in());

        try {
            String url =
                    "https://api.weibo.com/2/users/show.json?access_token="+socialUser.getAccess_token()+"&uid=" + socialUser.getUid() ;
            ResponseEntity<WeiboSocialUserInfo> response = new RestTemplate().getForEntity(url ,
                    WeiboSocialUserInfo.class);
            if (response.getStatusCodeValue() == 200 && response.getBody() != null) {
                // 查询成功
                WeiboSocialUserInfo body = response.getBody();

                member.setNickname(body.getName());
                member.setGender("m".equals(body.getGender()) ? 1 : 0);

                if (!StringUtils.isEmpty(body.getProfile_image_url())) {
                    member.setHeader(body.getProfile_image_url());
                }
//        TODO 更多详细信息
            }
        } catch (Exception e) {
            log.error("详细信息查询错误, {} ", e.getMessage());
        }
        MemberLevelEntity defaultLevel = memberLevelService.getDefaultLevel();
        if (defaultLevel != null) {
            member.setLevelId(defaultLevel.getId());
        }
        baseMapper.insert(member);
        return member;
    }
}