package com.rzlearn.user.common.config;

import com.rzlearn.base.config.AbstractFeignInterceptor;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;

/**
 * <p>ClassName:FeiginConfig</p>
 * <p>Description:</p>
 *
 * @author JiPeigong
 * @date 2018-06-08 21:36
 * @see
 * @since
 **/
@Configuration
public class FeignInterceptor extends AbstractFeignInterceptor {

}
