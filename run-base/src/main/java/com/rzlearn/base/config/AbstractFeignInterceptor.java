package com.rzlearn.base.config;

import com.rzlearn.base.constant.HeaderConsts;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.apache.commons.lang3.StringUtils;
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
public abstract class AbstractFeignInterceptor implements RequestInterceptor {

    @Override
    public void apply(RequestTemplate requestTemplate) {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder
                .getRequestAttributes();
        if (attributes != null) {
            HttpServletRequest request = attributes.getRequest();
            Enumeration<String> headerNames = request.getHeaderNames();
            if (headerNames != null) {
                while (headerNames.hasMoreElements()) {
                    String name = headerNames.nextElement();
                    if (!StringUtils.equalsIgnoreCase(HeaderConsts.USER_INFO, name)
                            && !StringUtils.equalsIgnoreCase(HeaderConsts.REFER, name)
                            && !StringUtils.equalsIgnoreCase(HeaderConsts.APPID, name)
                            && !StringUtils.equalsIgnoreCase(HeaderConsts.X_FORWARDED_FOR, name)
                            && !StringUtils.equalsIgnoreCase(HeaderConsts.X_REAL_IP, name) ) {
                        continue;
                    } else {
                        String values = request.getHeader(name);
                        requestTemplate.header(name, values);
                    }
                }
            }
        }
    }
}
