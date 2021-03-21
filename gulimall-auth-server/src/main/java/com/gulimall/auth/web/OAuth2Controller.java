package com.gulimall.auth.web;

import com.gulimall.auth.config.OAuth2WeiboConfigProperties;
import com.gulimall.auth.feign.MemberFeignService;
import com.gulimall.auth.vo.SocialUser;
import com.gulimall.common.constant.SessionConstant;
import com.gulimall.common.utils.CommonResult;
import com.gulimall.common.vo.UserInfoVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * @author aqiang9  2020-09-03
 */
@Controller
@RequestMapping("/oauth2")
@Slf4j
public class OAuth2Controller {

    @Autowired
    OAuth2WeiboConfigProperties configProperties;
    @Autowired
    MemberFeignService memberFeignService;


    private String weiboTokenUrl = "https://api.weibo.com/oauth2/access_token?client_id={1}&client_secret={2}&grant_type={3}&redirect_uri={4}&code={5}";

    @Autowired
    RestTemplate restTemplate;

    @GetMapping("/weibo/success")
    public String weiBoSuccess(@RequestParam("code") String code, RedirectAttributes redirectAttributes, HttpSession session ,   HttpServletRequest request) {
        // 1、通过 code 换取token  post
//        ResponseEntity<SocialUser> response = restTemplate.postForEntity(weiboTokenUrl, new Object() , SocialUser.class,
//                configProperties.getAppKey(),
//                configProperties.getAppSecret(),
//                configProperties.getGrantType(),
//                configProperties.getRedirectUri(),
//                code
//        );
        String url = "https://api.weibo.com/oauth2/access_token?client_id=3423025082&client_secret" +
                "=b401dc67ea9bea83d3ec6c5f69df3127&grant_type=authorization_code&redirect_uri=http://auth.gulimall" +
                ".com/oauth2/weibo/success&code=" + code  ;
        ResponseEntity<SocialUser> response = restTemplate.postForEntity(url, 1 , SocialUser.class);
        if (response.getStatusCodeValue() != 200) {
            // 失败
            redirectAttributes.addAttribute("msg", "授权失败, 请重新授权");
            return "redirect:http://auth.gulimall.com/login.html";
        }
        SocialUser socialUser = response.getBody();
        // 2、 通过 token 换取用户信息
//        restTemplate.getForEntity("https://api.weibo.com/2/users/show.json")
//


        // 如果是第一次登陆 自动注册
        CommonResult<UserInfoVo> loginUserInfo = memberFeignService.oauthLogin(socialUser);
        if (loginUserInfo.isOk()) {
            log.info("用户信息 , {}  ", loginUserInfo.getData());

            UserInfoVo userInfoVo = loginUserInfo.getData();
//    TODO  默认作用域 为 当前域名（cookie）  -- 变系列化机制

            // 存放用户信息
            session.setAttribute(SessionConstant.USER_INFO_KEY , userInfoVo);
//        返回成功页面
            return "redirect:http://www.gulimall.com";
        }
        redirectAttributes.addAttribute("msg", "登陆异常, 请重新登录");
        return "redirect:http://auth.gulimall.com/login.html";
    }


}
