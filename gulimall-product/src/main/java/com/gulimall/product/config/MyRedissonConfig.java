package com.gulimall.product.config;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author aqiang9  2020-08-17
 */
@Configuration
public class MyRedissonConfig {

    @Bean
    public RedissonClient redissonClient(RedisProperties redisProperties) {
        System.out.println(redisProperties.getHost() );
        System.out.println(redisProperties.getPort()  );
        Config config = new Config();

//        config.setTransportMode(TransportMode.EPOLL);
        config.useSingleServer().setAddress("redis://39.97.224.239:6379" );
//        config.useClusterServers()
//                //可以用"rediss://"来启用SSL连接
//                .addNodeAddress("redis://66:7181");

        RedissonClient redisson = Redisson.create(config);
        return redisson;
    }
}
