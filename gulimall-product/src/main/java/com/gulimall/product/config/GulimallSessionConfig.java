package com.gulimall.product.config;

import com.gulimall.common.constant.SessionConstant;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.session.web.http.CookieSerializer;
import org.springframework.session.web.http.DefaultCookieSerializer;

/**
 * @author aqiang9  2020-09-04
 */
@Configuration
public class GulimallSessionConfig {
    @Bean
    public CookieSerializer cookieSerializer() {
        DefaultCookieSerializer serializer = new DefaultCookieSerializer();
        serializer.setCookieName(SessionConstant.COOKIE_NAME);
        serializer.setCookiePath(SessionConstant.COOKIE_PATH);
        serializer.setDomainName(SessionConstant.DOMAIN_NAME);

        return serializer;
    }


    // TODO 必须交这个名
    @Bean
    public RedisSerializer<Object> springSessionDefaultRedisSerializer() {
        return new GenericJackson2JsonRedisSerializer();
    }
}
