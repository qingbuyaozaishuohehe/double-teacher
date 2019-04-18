package com.rzlearn.gateway.common.config;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;

/**
 * <p>ClassName:CustomOauthException</p>
 * <p>Description:</p>
 *
 * @author Zhangwb
 * @date 2018-12-03 11:28
 **/
@JsonSerialize(using = CustomOauthExceptionSerializer.class)
public class CustomOauthException extends OAuth2Exception {
    public CustomOauthException(String msg) {
        super(msg);
    }
}
