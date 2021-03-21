package com.gulimall.auth.feign;

import com.gulimall.auth.vo.SocialUser;
import com.gulimall.auth.vo.UserLoginVo;
import com.gulimall.auth.vo.UserRegisterVo;
import com.gulimall.common.utils.CommonResult;
import com.gulimall.common.vo.UserInfoVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @author aqiang9  2020-09-02
 */
@FeignClient("gulimall-member")
public interface MemberFeignService {


    @PostMapping("/member/member/register")
    CommonResult<UserInfoVo> register(@RequestBody UserRegisterVo registerVo);


    @PostMapping("/member/member/login")
    CommonResult<UserInfoVo> login(@RequestBody UserLoginVo loginVo) ;

    @PostMapping("/member/member/oauth2/login")
    CommonResult<UserInfoVo> oauthLogin(@RequestBody SocialUser socialUser) ;

}
