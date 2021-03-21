package com.gulimall.cart.conf;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author aqiang9  2020-09-01
 */
@Configuration
@EnableConfigurationProperties(ThreadPoolConfigProperties.class)
public class MyThreadConfig {


    @Bean
    public ThreadPoolExecutor threadPoolExecutor(ThreadPoolConfigProperties poolConfig){
        return new ThreadPoolExecutor(poolConfig.getCoreSize()  , poolConfig.getMaxSize() , poolConfig.getKeepAliveTime() , TimeUnit.SECONDS,
                new LinkedBlockingDeque<>( 200 ),
                Executors.defaultThreadFactory(), new ThreadPoolExecutor.AbortPolicy() ) ;
    }
}
