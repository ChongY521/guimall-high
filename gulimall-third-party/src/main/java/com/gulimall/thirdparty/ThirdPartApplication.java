package com.gulimall.thirdparty;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author aqiang9  2020-07-28
 */
@EnableDiscoveryClient
@SpringBootApplication
public class ThirdPartApplication {
    public static void main(String[] args){
        SpringApplication.run(ThirdPartApplication.class,args) ;
    }
}

