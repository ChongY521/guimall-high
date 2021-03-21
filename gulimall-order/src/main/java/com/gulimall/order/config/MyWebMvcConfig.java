package com.gulimall.order.config;

import com.gulimall.order.interceptor.LoginUserInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author aqiang9  2020-09-24
 */
@Configuration
public class MyWebMvcConfig
{

	@Bean
	public WebMvcConfigurer webMvcConfigurer()
	{
		return new WebMvcConfigurer()
		{
			@Override
			public void addInterceptors(InterceptorRegistry registry)
			{
				registry.addInterceptor(new LoginUserInterceptor()).addPathPatterns("/**");
			}
		};
	}

}
