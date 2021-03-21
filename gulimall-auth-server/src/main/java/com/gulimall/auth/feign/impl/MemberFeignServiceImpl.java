package com.gulimall.auth.feign.impl;

import com.gulimall.auth.feign.MemberFeignService;
import com.gulimall.auth.vo.SocialUser;
import com.gulimall.auth.vo.UserLoginVo;
import com.gulimall.auth.vo.UserRegisterVo;
import com.gulimall.common.utils.CommonResult;
import com.gulimall.common.vo.UserInfoVo;

/**
 * @author aqiang9  2020-09-20
 */
public class MemberFeignServiceImpl implements MemberFeignService {
	@Override
	public CommonResult<UserInfoVo> register(UserRegisterVo registerVo) {

		return CommonResult.fail("服务走丢了");
	}

	@Override
	public CommonResult<UserInfoVo> login(UserLoginVo loginVo) {
		return CommonResult.fail("服务走丢了");
	}

	@Override
	public CommonResult<UserInfoVo> oauthLogin(SocialUser socialUser) {
		return CommonResult.fail("服务走丢了");
	}
}
