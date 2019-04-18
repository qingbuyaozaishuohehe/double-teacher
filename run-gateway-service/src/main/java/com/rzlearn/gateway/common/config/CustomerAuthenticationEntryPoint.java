package com.rzlearn.gateway.common.config;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.rzlearn.base.dto.ResultMsg;
import com.rzlearn.base.support.MessageCode;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.common.exceptions.InvalidTokenException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * <p>ClassName:CustomAuthenticationEntryPoint</p>
 * <p>Description:自定义认证错误拦截</p>
 *
 * @author ZhangWenbing
 * @date 2018-06-12 15:59:06
 **/
@Component
@Slf4j
public class CustomerAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException) throws IOException {
        ResultMsg resultMsg = new ResultMsg();
        Throwable cause = authException.getCause();
        if (cause instanceof InvalidTokenException) {
            resultMsg.setResultCode(HttpStatus.SC_UNAUTHORIZED);
            resultMsg.setResultMessage(MessageCode.INVALID_TOKEN.msg());
        } else {
            resultMsg.setResultCode(HttpStatus.SC_UNAUTHORIZED);
            resultMsg.setResultMessage(MessageCode.NO_AUTHENTICATION.msg());
        }
        response.setContentType("application/json;charset=UTF-8");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        byte[] bytes = JSON.toJSONBytes(resultMsg, SerializerFeature.DisableCircularReferenceDetect);
        response.getOutputStream().write(bytes);
    }
}
