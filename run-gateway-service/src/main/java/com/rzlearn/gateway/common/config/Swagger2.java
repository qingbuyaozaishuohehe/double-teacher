package com.rzlearn.gateway.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.http.ResponseEntity;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.*;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.time.LocalDate;
import java.util.List;

import static com.google.common.collect.Lists.newArrayList;

/**
 * Swagger2 配置.
 *
 * @author zhangwb
 */
@Configuration
@EnableSwagger2
@Profile({"dev","stage"})
public class Swagger2 {
    /**
     * Pet api docket.
     *
     * @return the docket
     */
    @Bean
    public Docket petApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.rzlearn.gateway.controller"))
                .paths(PathSelectors.any())
                .build()
                .apiInfo(apiInfo())
                .pathMapping("/")
                .directModelSubstitute(LocalDate.class, String.class)
                .genericModelSubstitutes(ResponseEntity.class)
                .useDefaultResponseMessages(false)
                .securitySchemes(newArrayList(apiKey()))
                .securityContexts(newArrayList(securityContext()))
                .enableUrlTemplating(false);
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("Api Documentation")
                .description("Api Documentation")
                .contact(new Contact("ruihu", "https://www.com.rzlearn.com/", "manager@com.rzlearn.com"))
                .version("1.0")
                .build();
    }

    private ApiKey apiKey() {
        // 用于Swagger UI测试时添加Bearer Token
        return new ApiKey("BearerToken", "authorization", "header");
    }

    private SecurityContext securityContext() {
        return SecurityContext.builder()
                .securityReferences(defaultAuth())
                // 注意要与Restful API路径一致
                .forPaths(PathSelectors.regex("/*/.*"))
                .build();
    }

    /**
     * Default auth list.
     *
     * @return the list
     */
    List<SecurityReference> defaultAuth() {
        AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = authorizationScope;
        return newArrayList(new SecurityReference("BearerToken", authorizationScopes));
    }

    /**
     * Ui config ui configuration.
     *
     * @return the ui configuration
     */
    @Bean
    UiConfiguration uiConfig() {
        return new UiConfiguration(false, false, 1,
                1, ModelRendering.MODEL, false,
                DocExpansion.LIST, null, 50, OperationsSorter.ALPHA,
                false, TagsSorter.ALPHA, "");
    }
}
