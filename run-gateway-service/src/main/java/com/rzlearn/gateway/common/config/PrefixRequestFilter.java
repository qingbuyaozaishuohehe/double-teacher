package com.rzlearn.gateway.common.config;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.rzlearn.base.constant.HeaderConsts;
import com.rzlearn.base.dto.HeaderUserInfoDTO;
import com.rzlearn.base.dto.ResultMsg;
import com.rzlearn.base.support.MessageCode;
import com.rzlearn.gateway.common.util.RedisUtil;
import com.rzlearn.gateway.constant.BusinessConst;
import com.rzlearn.gateway.constant.RedisConst;
import com.rzlearn.gateway.security.SecurityUtils;
import com.rzlearn.setting.feign.ISysConfigController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;

import java.io.IOException;

import static com.netflix.zuul.context.RequestContext.getCurrentContext;

/**
 * <p>ClassName:PrefixRequestFilter</p>
 * <p>Description:</p>
 *
 * @author JiPeigong
 * @date 2018-12-21 13:41:50
 **/
@Configuration
public class PrefixRequestFilter extends ZuulFilter {

    @Autowired
    private RedisUtil redisUtil;

    @Override
    public String filterType() {
        return "pre";
    }

    @Override
    public int filterOrder() {
        return 0;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Autowired
    private ISysConfigController sysConfigController;

    @Override
    public Object run() {
        RequestContext context = getCurrentContext();
        String bearerToken = context.getRequest().getHeader("authorization");
        if (StringUtils.hasText(bearerToken)) {
            String userName = SecurityUtils.getCurrentUserLogin().get();
            StringBuilder stringBuilder = new StringBuilder(RedisConst.REDIS_USER_INFO);
            stringBuilder.append(userName);
            HeaderUserInfoDTO redisUserInfoDTO = redisUtil.get(stringBuilder.toString(), HeaderUserInfoDTO.class);
            ResultMsg<String> rsg = sysConfigController.getSysConfig(BusinessConst.SYSTEM_MAINTENANCE_UPGRADE);
            rsg.checkBusinessException();
            if (redisUserInfoDTO != null) {
                if (!redisUserInfoDTO.getRoles().contains(BusinessConst.USER_ROLE_SUPER_ADMIN) &&
                        org.apache.commons.lang3.StringUtils.equals(BusinessConst.SYSTEM_IS_MAINTENANCE_UPGRADE, rsg.getResultObject())) {
                    context.setSendZuulResponse(false);
                    context.setResponseStatusCode(503);
                    context.getResponse().setContentType("application/json;charset=UTF-8");
                    byte[] bytes = JSON.toJSONBytes(new ResultMsg(MessageCode.SYSTEM_MAINTENANCE_UPGRADE), SerializerFeature.DisableCircularReferenceDetect);
                    try {
                        context.getResponse().getOutputStream().write(bytes);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                context.addZuulRequestHeader(HeaderConsts.USER_INFO, JSON.toJSONString(redisUserInfoDTO));
            } else {
                redisUserInfoDTO = new HeaderUserInfoDTO();
                redisUserInfoDTO.setUserName(userName);
                context.addZuulRequestHeader(HeaderConsts.USER_INFO, JSON.toJSONString(redisUserInfoDTO));
            }
        } else {
            context.addZuulRequestHeader(HeaderConsts.USER_INFO, null);
        }
        return null;
    }
}
