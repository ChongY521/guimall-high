package com.gulimall.cart;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author aqiang9  2020-09-08
 */
@EnableFeignClients(basePackages = "com.gulimall.cart.feign")
@SpringBootApplication
@EnableDiscoveryClient
public class CartApplication {
    public static void main(String[] args){
        SpringApplication.run(CartApplication.class,args) ;
    }
}
