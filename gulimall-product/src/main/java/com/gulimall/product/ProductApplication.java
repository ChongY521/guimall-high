package com.gulimall.product;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

/**
 * @author aqiang9  2020-07-27
 */
@EnableRedisHttpSession
@EnableFeignClients(basePackages ="com.gulimall.product.feign") // 开启远程调用
@SpringBootApplication(scanBasePackages = "com.gulimall")// 将全局异常处理 扫描进入
@EnableDiscoveryClient // 允许服务发现
public class ProductApplication {
    public static void main(String[] args){
        SpringApplication.run(ProductApplication.class,args) ;
    }
}