package com.gulimall.auth.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author aqiang9  2020-09-03
 */
@ConfigurationProperties(prefix = "oauth2.weibo")
@Configuration
@Getter
@Setter
public class OAuth2WeiboConfigProperties {

    private String redirectUri = "http://auth.gulimall.com/oauth2/weibo/success" ;
    private String appKey = "3423025082" ;
    private String appSecret = "b401dc67ea9bea83d3ec6c5f69df3127";
    private String grantType = "authorization_code" ;
}
