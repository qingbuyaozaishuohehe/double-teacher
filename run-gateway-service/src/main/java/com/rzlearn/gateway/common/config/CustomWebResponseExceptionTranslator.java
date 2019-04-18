package com.rzlearn.gateway.common.config;

import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;
import org.springframework.security.oauth2.provider.error.WebResponseExceptionTranslator;
import org.springframework.stereotype.Component;

/**
 * <p>ClassName:CustomWebResponseExceptionTranslator</p>
 * <p>Description:</p>
 *
 * @author Zhangwb
 * @date 2018-12-03 13:12
 **/
@Component
public class CustomWebResponseExceptionTranslator implements WebResponseExceptionTranslator {
    @Override
    public ResponseEntity<OAuth2Exception> translate(Exception e) throws Exception {

        return ResponseEntity
                .status(500)
                .body(new CustomOauthException(e.getMessage()));
    }
}
