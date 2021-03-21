package com.gulimall.auth.web;

import com.gulimall.auth.constant.AuthConstant;
import com.gulimall.auth.exception.AuthErrorCode;
import com.gulimall.auth.feign.MemberFeignService;
import com.gulimall.auth.feign.ThreadPartFeignService;
import com.gulimall.auth.vo.UserRegisterVo;
import com.gulimall.common.constant.SessionConstant;
import com.gulimall.common.utils.CommonResult;
import com.gulimall.common.vo.UserInfoVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * @author aqiang9  2020-09-01
 */
@Controller
@Slf4j
public class RegisterController {
    private static final String REDIRECT_HTML = "redirect:http://auth.gulimall.com/reg.html" ;
    @Autowired
    ThreadPartFeignService threadPartFeignService;

    @Autowired
    MemberFeignService memberFeignService;

    @Autowired
    StringRedisTemplate redisTemplate;

    @GetMapping("/reg.html")
    public String regHtml(String originUrl , Model model , HttpSession session){
        // 应保存 原来的地址
        if (session.getAttribute( SessionConstant.USER_INFO_KEY ) == null) {
            model.addAttribute( AuthConstant.ORIGIN_URL_KEY  , originUrl ) ;
            return "reg" ;
        } else {
            return AuthConstant.toOriginUrl( originUrl ) ;
        }
    }

    @GetMapping("/sms/sendcode")
    @ResponseBody
    public CommonResult<String> sendCode(@RequestParam("phone") String phone) {
        if (StringUtils.isEmpty(phone)){
            return CommonResult.fail("手机号不能为空") ;
        }
        if (!phone.matches("^((13[0-9])|(14[5|7])|(15([0-3]|[5-9]))|(18[0,5-9]))\\d{8}$")) {
            return CommonResult.fail("手机号格式错误") ;
        }
        // 1、接口防刷
//  2、验证码校验
        String code = UUID.randomUUID().toString().substring(0, 5);

//        60 秒过期
        Boolean ifAbsent = redisTemplate.opsForValue().setIfAbsent(AuthConstant.SMS_CODE_CACHE_PREFIX + phone, code, 60, TimeUnit.SECONDS);
        // 保存成功
        if (ifAbsent != null && ifAbsent) {
//            threadPartFeignService.sendSmsCode(phone, code);
            log.info("验证码： phone: {} , code : {}  ", phone, code);
            return CommonResult.ok("发送成功");
        } else {
            return CommonResult.fail(AuthErrorCode.SMS_FREQUENCY_TOO_HIGH_ERROR);
        }
    }

    /**
     * redirectAttributes  ： 利用 session 进行处理
     *
     * @param userRegisterVo     注册信息
     * @param result             错误消息
     * @param redirectAttributes 重定向
     */
    @PostMapping("/register")
    public String register(@Valid UserRegisterVo userRegisterVo , String originUrl  , BindingResult result, RedirectAttributes redirectAttributes, HttpSession session) {
        redirectAttributes.addAttribute(AuthConstant.ORIGIN_URL_KEY , originUrl  );

        if (result.hasErrors()) {
            Map<String, String> errorMap = result.getFieldErrors().stream()
                    .collect(Collectors.toMap(e->e.getField() , e->e.getDefaultMessage() ));
//            model.addAttribute("errors" , errorMap ) ;
            // Request method ‘POST’ not suppors
//            请求转发 的区别
            redirectAttributes.addAttribute("errors", errorMap);

            return REDIRECT_HTML  ;
        }
        // 真正的 注册功能
//        1、比对验证码
        String codeKey = AuthConstant.SMS_CODE_CACHE_PREFIX + userRegisterVo.getMobile();

        String code = redisTemplate.opsForValue().get(codeKey);

        Map<String, String> errorMessage = new HashMap<>(4);
        if (StringUtils.isEmpty(code)) {
            errorMessage.put("code", "验证码已失效, 请从新获取！！！");
            redirectAttributes.addAttribute("errors", errorMessage);
            // 注册页
            return REDIRECT_HTML;
        }
        if (!code.equals(userRegisterVo.getCode())) {
            errorMessage.put("code", "验证码错误！！！");

            redirectAttributes.addAttribute("errors", errorMessage);
            return REDIRECT_HTML ;
        }
        // 调用注册逻辑
        CommonResult<UserInfoVo> register = memberFeignService.register(userRegisterVo);
        // 登陆成功 并 登陆
        if (register.isOk()) {
            // 清除redis 缓存
            redisTemplate.delete(codeKey);
            //放入缓存就可以了
            session.setAttribute(SessionConstant.USER_INFO_KEY , register.getData());
            // TODO 返回来源地址
           return AuthConstant.toOriginUrl( originUrl  ) ;
        }
        redirectAttributes.addAttribute("msg", register.getMsg());

        return REDIRECT_HTML ;
    }

}
