package com.gulimall.doc.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.config.GatewayProperties;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.support.NameUtils;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import springfox.documentation.swagger.web.SwaggerResource;
import springfox.documentation.swagger.web.SwaggerResourcesProvider;

import java.util.ArrayList;
import java.util.List;

@Component
@Primary
//@AllArgsConstructor
public class SwaggerResourceConfig implements SwaggerResourcesProvider {
    private static final Logger log = LoggerFactory.getLogger(SwaggerResourceConfig.class);

    private final RouteLocator routeLocator;
    private final GatewayProperties gatewayProperties;

    public SwaggerResourceConfig(RouteLocator routeLocator, GatewayProperties gatewayProperties) {
        this.routeLocator = routeLocator;
        this.gatewayProperties = gatewayProperties;
        System.out.println(gatewayProperties);
    }

    @Override
    public List<SwaggerResource> get() {
        List<SwaggerResource> resources = new ArrayList<>();
        List<String> routes = new ArrayList<>();
        System.out.println("name-prefix" + NameUtils.GENERATED_NAME_PREFIX);

        routeLocator.getRoutes().subscribe(route -> routes.add(route.getId()));
        System.out.println("routers : " + routes);
        gatewayProperties.getRoutes()
                .stream()
                .filter(routeDefinition -> routes.contains(routeDefinition.getId()))
                .forEach(route -> {
                    route.getPredicates().stream()
                            // 拿到所有的 path
                            .filter(predicateDefinition -> ("Path").equalsIgnoreCase(predicateDefinition.getName()))
                            .filter(predicateDefinition -> !predicateDefinition.getArgs().get(NameUtils.GENERATED_NAME_PREFIX + "0").startsWith("/api"))
                            .forEach(
                                    predicateDefinition -> {
                                        String s = predicateDefinition.getArgs().get(NameUtils.GENERATED_NAME_PREFIX + "0");

                                        log.info("路由id : {} ,  {}  , {} ", route.getId(), s, s.replace("**", "v2/api-docs"));

                                        resources.add(swaggerResource(route.getId(),
                                                predicateDefinition.getArgs().get(NameUtils.GENERATED_NAME_PREFIX + "0"
                                                ).replace("/**", "/v2/api-docs")));
                                    });
                });
        return resources;
    }

    private SwaggerResource swaggerResource(String name, String location) {
        log.info("name:{},location:{}", name, location);
        SwaggerResource swaggerResource = new SwaggerResource();
        swaggerResource.setName(name);
        swaggerResource.setLocation(location);
        swaggerResource.setSwaggerVersion("2.0");
        return swaggerResource;
    }
}