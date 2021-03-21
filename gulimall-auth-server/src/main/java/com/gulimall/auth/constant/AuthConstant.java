package com.gulimall.auth.constant;

import org.springframework.util.StringUtils;

/**
 * @author aqiang9  2020-09-02
 */
public final class AuthConstant {

    private static final  String loginDefaultReturnUrl = "redirect:http://www.gulimall.com" ;


    // 后面接手机号
    public static final  String SMS_CODE_CACHE_PREFIX = "sms:code:";
    public static final String  ORIGIN_URL_KEY = "originUrl" ;


    /**
     * 返回来源地址 或 默认地址
     * @param originUrl 来源地址
     */
    public static  String toOriginUrl(String originUrl){
        if (StringUtils.isEmpty(originUrl)) {
            return loginDefaultReturnUrl ;
        }
        return "redirect:" + originUrl  ;
    }
}
