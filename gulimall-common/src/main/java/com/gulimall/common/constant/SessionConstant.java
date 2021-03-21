package com.gulimall.common.constant;

/**
 * @author aqiang9  2020-09-04
 */
public final class SessionConstant {
    /**
     * 统一session 的名字
     */
    public static final String COOKIE_NAME = "GULISESSION";
    /**
     * cookie 存放的 路径
     */
    public static final String COOKIE_PATH = "/";
    /**
     * cookie 统一的域名
     */
    public static final String DOMAIN_NAME = "gulimall.com";


    public static final String USER_INFO_KEY = "userInfo";
    public static final String TEMP_USER_COOKIE_NAME = "gulimall_user_key";
    /**
     * 临时用户保存时间 默认 1 个月
     */
    public static final Integer TEMP_USER_COOKIE_TIME = 60 * 60 * 24 * 30;


}
