package com.gulimall.member.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * @author aqiang9  2020-09-02
 */
@Configuration
public class PasswordEncoderConfig {

    @Bean
    @Lazy
    public BCryptPasswordEncoder cryptPasswordEncoder(){
        return new BCryptPasswordEncoder() ;
    }


}
