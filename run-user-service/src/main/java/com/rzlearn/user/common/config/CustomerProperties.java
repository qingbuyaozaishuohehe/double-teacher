package com.rzlearn.user.common.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * <p>ClassName:CustomerProperties</p>
 * <p>Description:自定义属性配置</p>
 *
 * @author ZhangWenbing
 * @date 2018 -06-25 08:48:52
 */
@ConfigurationProperties(
        prefix = "customer",
        ignoreUnknownFields = false
)
@Data
public class CustomerProperties {
    private final CustomerProperties.Qq qq = new CustomerProperties.Qq();
    private final CustomerProperties.Weibo weibo = new CustomerProperties.Weibo();
    private final CustomerProperties.Weixin weixin = new CustomerProperties.Weixin();

    /**
     * The type Qq.
     */
    @Data
    public class Qq {

        private String appId;

        private String appKey;

        private String redirectUrl;

        private String scope;

        private String userInfoUrl;

        private String accessTokenUrl;

        private String getOpenIdUrl;

        private String authorizeUrl;
    }

    /**
     * The type Weibo.
     */
    @Data
    public class Weibo {

        private String appId;

        private String appKey;

        private String redirectUrl;

        private String scope;

        private String userInfoUrl;

        private String accessTokenUrl;

        private String authorizeUrl;

    }

    /**
     * The type Weixin.
     */
    @Data
    public class Weixin {

        private String appId;

        private String appKey;

        private String redirectUrl;

        private String scope;

        private String userInfoUrl;

        private String accessTokenUrl;

        private String authorizeUrl;
    }
}
