package com.rzlearn.gateway;

import com.rzlearn.gateway.common.config.CustomerProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * 启动类.
 *
 * @author zhangwb
 */
@SpringBootApplication
@EnableZuulProxy
@EnableAsync
@EnableFeignClients(basePackages = {"com.rzlearn.*.feign"})
@EnableConfigurationProperties({CustomerProperties.class})
public class GatewayServiceApplication {

    /**
     * The entry point of application.
     *
     * @param args the input arguments
     */
    public static void main(String[] args) {
        SpringApplication.run(GatewayServiceApplication.class, args);
    }
}
