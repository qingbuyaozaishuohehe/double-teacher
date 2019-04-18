package com.rzlearn.gateway.common.config;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import springfox.documentation.swagger.web.SwaggerResource;
import springfox.documentation.swagger.web.SwaggerResourcesProvider;

import java.util.ArrayList;
import java.util.List;

/**
 * Swagger2 关联资源配置.
 *
 * @author zhangwb
 */
@Component
@Primary
public class DocumentationConfig implements SwaggerResourcesProvider {
    @Override
    public List<SwaggerResource> get() {
        List resources = new ArrayList<>();
        resources.add(swaggerResource("网关", "/api-docs", "2.0"));
        resources.add(swaggerResource("共通服务(run-cmm)", "/run-cmm/api-docs", "2.0"));
        resources.add(swaggerResource("用户中心(run-user)", "/run-user/api-docs", "2.0"));
        resources.add(swaggerResource("设置中心(run-setting)", "/run-setting/api-docs", "2.0"));
        return resources;
    }

    private SwaggerResource swaggerResource(String name, String location, String version) {
        SwaggerResource swaggerResource = new SwaggerResource();
        swaggerResource.setName(name);
        swaggerResource.setLocation(location);
        swaggerResource.setSwaggerVersion(version);
        return swaggerResource;
    }
}
