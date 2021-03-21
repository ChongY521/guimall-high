package com.gulimall.search;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author aqiang9  2020-08-12
 */
@EnableDiscoveryClient
@SpringBootApplication
@EnableFeignClients(basePackages = "com.gulimall.search.feign")
public class SearchApplication {
    public static void main(String[] args){
        SpringApplication.run(SearchApplication.class,args) ;
    }
}
