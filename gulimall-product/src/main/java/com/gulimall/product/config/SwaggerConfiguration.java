package com.gulimall.product.config;

import com.github.xiaoymin.knife4j.spring.annotations.EnableKnife4j;
import com.github.xiaoymin.knife4j.spring.configuration.Knife4jProperties;
import io.swagger.annotations.ApiOperation;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.annotation.Order;
import springfox.bean.validators.configuration.BeanValidatorPluginsConfiguration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author aqiang9  2020-08-09
 */
@Configuration
@EnableSwagger2
@EnableKnife4j
@Import(BeanValidatorPluginsConfiguration.class)
@ConfigurationProperties(prefix = "swagger")
@Getter
@Setter
public class SwaggerConfiguration {
    private String title;

    private String contact;

    private String version;

    private String license;

    private String licenseUrl;

    private String termsOfServiceUrl;

    private String description;

    private String basePackage;


    @Bean(value = "gulimallApi")
    @Order(value = 1)
    public Docket groupRestApi(Knife4jProperties knife4jProperties) {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(groupApiInfo(knife4jProperties))
//                .groupName("谷粒商城-商品服务")
//                .getGroupName()
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.gulimall.product.controller"))
                .apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo groupApiInfo(Knife4jProperties knife4jProperties){
        return new ApiInfoBuilder()
                .title("gulimall-product")
                .description("<div style='font-size:14px;color:red;'>谷粒商城-商品服务 RESTFULL APIs</div>")
//                .termsOfServiceUrl("http://www.group.com/")
                .contact("abc.mall")
                .version("1.0")
                .build();
    }
//
//    public Docket groupRestApi() {
//        return new Docket(DocumentationType.SWAGGER_2)
//                .apiInfo(groupApiInfo())
//                //分组名称
//                // .groupName("2.X版本")
//                .select()
//                // // 对所有api进行监控
//                // .apis(RequestHandlerSelectors.any())
//                .apis(RequestHandlerSelectors.basePackage(basePackage))
//                //不显示错误的接口地址
//                .paths(PathSelectors.any())
//                //错误路径不监控
//                .paths(Predicates.not(PathSelectors.regex("/error.*")))
//                .build();
//    }
//
//    private ApiInfo groupApiInfo() {
//        return new ApiInfoBuilder()
//                .title(title)
//                .contact(contact)
//                .version(version)
//                .license(license)
//                .licenseUrl(licenseUrl)
//                .termsOfServiceUrl(termsOfServiceUrl)
//                .description(description)
//                .build();
//    }
}

