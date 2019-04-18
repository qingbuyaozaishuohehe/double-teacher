package com.rzlearn.gateway.common.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.ArrayList;
import java.util.List;

/**
 * 自定义属性配置.
 *
 * @author zhangwb
 */
@ConfigurationProperties(
        prefix = "customer",
        ignoreUnknownFields = false
)
@Data
public class CustomerProperties {
    private final CustomerProperties.Security security = new CustomerProperties.Security();
    private final CustomerProperties.Cors cors = new CustomerProperties.Cors();

    /**
     * The type Security.
     */
    @Data
    public class Security {
        private final CustomerProperties.Security.Authentication authentication = new CustomerProperties.Security.Authentication();

        /**
         * The type Authentication.
         */
        @Data
        public class Authentication {
            private final CustomerProperties.Security.Authentication.Oauth oauth = new CustomerProperties.Security.Authentication.Oauth();

            /**
             * The type Oauth.
             */
            @Data
            public class Oauth {
                private String client;
                private String secret;
                private int tokenValidityInSeconds;
                private int refreshTokenValidityInSeconds;
            }
        }
    }

    /**
     * The type Cors.
     */
    @Data
    public class Cors {
        private List<String> allowedOrigins = new ArrayList<>();

        private List<String> allowedMethods = new ArrayList<>();

        private List<String> allowedHeaders = new ArrayList<>();
    }
}
