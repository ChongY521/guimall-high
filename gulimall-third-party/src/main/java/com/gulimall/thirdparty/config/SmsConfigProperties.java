package com.gulimall.thirdparty.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author aqiang9  2020-09-02
 */
@ConfigurationProperties(prefix = "gulimall.sms")
@Configuration
@Setter
@Getter
public class SmsConfigProperties {
    /**
     * 请求地址 支持http 和 https 及 WEBSOCKET
     */
    private String host ;
    /**
     * 后缀
     */
    private String path ;
    /**
     * 开通服务后 买家中心-查看AppCode
     */
    private String appcode ;
//    String code = "200020";  // 【4】请求参数，详见文档描述
//    String phone = "15680261157";  //  【4】请求参数，详见文档描述
    /**
     * 模板编号
     */
    private String sign = "175622";   //  【4】请求参数，详见文档描述
    /**
     * 模板编号
     */
    private String skin = "1";
}
