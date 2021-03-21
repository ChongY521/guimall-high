package com.gulimall.doc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author aqiang9  2020-08-09
 */
@EnableDiscoveryClient
@SpringBootApplication
public class ServiceDocApplication {
    public static void main(String[] args){
        SpringApplication.run(ServiceDocApplication.class,args) ;
    }
}
