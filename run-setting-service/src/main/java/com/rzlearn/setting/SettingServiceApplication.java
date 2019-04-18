package com.rzlearn.setting;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;

/**
 * <p>ClassName:StandardServiceApplication</p>
 * <p>Description:</p>
 * @author JiPeigong
 * @date 2018-11-14 13:05:32
 **/
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients(basePackages = {"com.rzlearn.*.feign"})
public class SettingServiceApplication {

    /**
     * The entry point of application.
     *
     * @param args the input arguments
     */
    public static void main(String[] args) {
        SpringApplication.run(SettingServiceApplication.class, args);
    }
}
