package com.gulimall.auth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

/**
 * @author aqiang9  2020-09-01
 */


@EnableRedisHttpSession  // 开启redis-session
@EnableFeignClients(basePackages = "com.gulimall.auth.feign")
@EnableDiscoveryClient
@SpringBootApplication
public class AuthServerApplication {
    public static void main(String[] args){
        SpringApplication.run(AuthServerApplication.class,args) ;
    }
}
