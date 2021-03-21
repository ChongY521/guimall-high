package com.gulimall.auth.web;

import com.gulimall.auth.config.OAuth2WeiboConfigProperties;
import com.gulimall.auth.constant.AuthConstant;
import com.gulimall.auth.feign.MemberFeignService;
import com.gulimall.auth.vo.UserLoginVo;
import com.gulimall.common.constant.SessionConstant;
import com.gulimall.common.utils.CommonResult;
import com.gulimall.common.vo.UserInfoVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;

/**
 * @author aqiang9  2020-09-01
 */
@Controller
@Slf4j
public class LoginController {
    @Autowired
    OAuth2WeiboConfigProperties auth2WeiboConfigProperties ;

    @Autowired
    MemberFeignService memberFeignService;

    @PostMapping("/login")
    public String login(UserLoginVo loginVo, RedirectAttributes redirectAttributes, HttpSession session) {
        CommonResult<UserInfoVo> login = memberFeignService.login(loginVo);
        if (login.isOk()) {
//            登陆成功
            UserInfoVo userInfoVo = login.getData();
            // 存放用户信息
            session.setAttribute(SessionConstant.USER_INFO_KEY  , userInfoVo);

          return AuthConstant.toOriginUrl( loginVo.getOriginUrl() ) ;
        }
        redirectAttributes.addAttribute("msg", login.getMsg());
        redirectAttributes.addAttribute("originUrl",  loginVo.getOriginUrl() );

//       登陆成功后  留一个 cookie 标志


        return "redirect:http://auth.gulimall.com/login.html";
    }


    /**
     *
     *
     * @param originUrl  来源地址 ，完成后需跳回去
     */
    @GetMapping("/login.html")
    public String login(String originUrl , Model model , HttpSession session) {
        // 应保存 原来的地址
        if (session.getAttribute(SessionConstant.USER_INFO_KEY) == null) {
            model.addAttribute( AuthConstant.ORIGIN_URL_KEY , originUrl ) ;
            model.addAttribute("clientId" , auth2WeiboConfigProperties.getAppKey() ) ;
            return "login" ;
        } else {
            return AuthConstant.toOriginUrl( originUrl ) ;
        }
    }

}
