package com.gulimall.order;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author aqiang9  2020-09-11
 */
@EnableDiscoveryClient
@SpringBootApplication
@EnableFeignClients
public class OrderApplication {
    public static void main(String[] args){
        SpringApplication.run(OrderApplication.class,args) ;
    }
}
