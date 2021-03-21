package com.gulimall.ware;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author aqiang9  2020-08-08
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients("com.gulimall.ware.feign")
public class WareApplication {
    public static void main(String[] args){
        SpringApplication.run(WareApplication.class,args) ;
    }
}
