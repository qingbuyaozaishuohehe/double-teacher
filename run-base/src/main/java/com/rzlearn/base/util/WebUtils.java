package com.rzlearn.base.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.rzlearn.base.constant.BaseConsts;
import com.rzlearn.base.constant.HeaderConsts;
import com.rzlearn.base.dto.HeaderUserInfoDTO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.SetUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.HashSet;
import java.util.Set;

/**
 * <p>ClassName:WebUtils</p>
 * <p>Description: 会话工具类</p>
 *
 * @author JiPeigong
 * @date 2018年5月11日
 */
@Slf4j
public class WebUtils {

    /**
     * 获取 request
     *
     * @return HttpServletRequest current request
     */
    public static final HttpServletRequest getCurrentRequest() {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        return attributes.getRequest();
    }

    /**
     * Get current user language string.
     *
     * @return the string
     */
    public static final String getCurrentUserLanguage() {
        String language;
        HeaderUserInfoDTO headerUserInfoDTO = getCurrentUser();
        if (headerUserInfoDTO != null && headerUserInfoDTO.getLanguage() != null) {
            language = headerUserInfoDTO.getLanguage();
        } else {
            language = BaseConsts.DEFAULT_LANGUAGE;
            // LocaleContextHolder.getLocale().getLanguage();
        }
        return language;
    }

    /**
     * Gets refer.
     *
     * @return the refer
     */
    public static final String getRefer() {
        return getCurrentRequest().getHeader(HeaderConsts.REFER);
    }

    /**
     * Gets domain.
     *
     * @return the domain
     */
    public static final String getDomain() {
        String domain = getRefer();
        if (StringUtils.isNotEmpty(domain)) {
            domain = domain.replaceAll(BaseConsts.HTTPS_PRE, "");
            domain = domain.replaceAll(BaseConsts.HTTP_PRE, "");
            int index = domain.indexOf(BaseConsts.HTTP_SUFFIX);
            if (index != -1) {
                String[] domainArr = domain.split(BaseConsts.HTTP_SUFFIX);
                if (domainArr != null && domainArr.length > 0) {
                    domain = domainArr[0];
                }
            }
            return domain;
        }
        return domain;
    }

    /**
     * Gets appid.
     *
     * @return the appid
     */
    public static final String getAppId() {
        String appId = getCurrentRequest().getHeader(HeaderConsts.APPID);
        return appId;
    }

    /**
     * Gets current user roles.
     *
     * @return the current user roles
     */
    public static final Set<String> getCurrentUserRoles() {
        HeaderUserInfoDTO headerUserInfoDTO = getCurrentUser();
        Set<String> roleSet = new HashSet<>(16);
        if (headerUserInfoDTO != null) {
            if (headerUserInfoDTO.getRoles() != null && headerUserInfoDTO.getRoles().size() > 0) {
                for (String roleCode : headerUserInfoDTO.getRoles()) {
                    roleSet.add(roleCode.toLowerCase());
                }
            }
        }
        return roleSet;
    }

    /**
     * 获取当前用户信息
     *
     * @return HeaderHeaderUserInfoDTO current user
     */
    public static final HeaderUserInfoDTO getCurrentUser() {
        return getCurrentUser(getCurrentRequest());
    }

    /**
     * Get current default role string.
     *
     * @return the string
     */
    public static final String getCurrentDefaultRole(){
        return getCurrentUser().getDefaultRole();
    }

    /**
     * 获取当前用户信息
     *
     * @param request the request
     * @return HeaderHeaderUserInfoDTO current user
     */
    public static final HeaderUserInfoDTO getCurrentUser(HttpServletRequest request) {
        String userInfoJson = getCurrentUserJson(request);
        HeaderUserInfoDTO headerUserInfoDTO = null;
        try {
            headerUserInfoDTO = JSON.parseObject(userInfoJson, HeaderUserInfoDTO.class);

        } catch (JSONException e) {
            log.error("获取用户信息失败，请检查头信息!");
        }
        return headerUserInfoDTO;
    }

    /**
     * 获取当前用户Json信息
     *
     * @param request the request
     * @return current user json
     */
    public static final String getCurrentUserJson(HttpServletRequest request) {
        return request.getHeader(HeaderConsts.USER_INFO);
    }

    /**
     * 获取当前用户的用户名
     *
     * @return current user name
     */
    public static final String getCurrentUserName() {
        String userName = null;
        HeaderUserInfoDTO headerUserInfoDTO = getCurrentUser();
        if (headerUserInfoDTO != null) {
            userName = headerUserInfoDTO.getUserName();
        }
        return userName;
    }

    /**
     * 获取当前用户的应用id
     *
     * @return
     */
    public static final Long getCurrentUserDomainId() {
        Long domainId = null;
        HeaderUserInfoDTO headerUserInfoDTO = getCurrentUser();
        if (headerUserInfoDTO != null) {
            domainId = headerUserInfoDTO.getDomainId();
        }
        return domainId;
    }

    /**
     * 获取当前用户的用户名
     *
     * @param request the request
     * @return current user name
     */
    public static final String getCurrentUserName(HttpServletRequest request) {
        String userName = null;
        HeaderUserInfoDTO headerUserInfoDTO = getCurrentUser(request);
        if (headerUserInfoDTO != null) {
            userName = headerUserInfoDTO.getUserName();
        }
        return userName;
    }

    /**
     * 获取当前用户id.
     *
     * @return the current user id
     */
    public static final Long getCurrentUserId() {
        Long userId = null;
        HeaderUserInfoDTO headerUserInfoDTO = getCurrentUser();
        if (headerUserInfoDTO != null) {
            userId = headerUserInfoDTO.getUserId();
        }
        return userId;
    }

    /**
     * 获取当前组织id.
     *
     * @return the current organization id
     */
    public static final Long getCurrentOrganizationId() {
        Long organizationId = null;
        HeaderUserInfoDTO headerUserInfoDTO = getCurrentUser();
        if (headerUserInfoDTO != null) {
            organizationId = headerUserInfoDTO.getOrganizationId();
        }
        return organizationId;
    }

    /**
     * Gets remote ip.
     *
     * @return the remote ip
     */
    public static final String getRemoteIp() {
        HttpServletRequest request = getCurrentRequest();
        String remoteAddr = request.getRemoteAddr();
        String forwarded = request.getHeader(HeaderConsts.X_FORWARDED_FOR);
        String realIp = request.getHeader(HeaderConsts.X_REAL_IP);

        String ip;
        if (realIp == null) {
            if (forwarded == null) {
                ip = remoteAddr;
            } else {
                ip = /*remoteAddr + BaseConsts.HTTP_SUFFIX + */forwarded.split(BaseConsts.HEADER_SUFFIX)[0];
            }
        } else {
            if (realIp.equals(forwarded)) {
                ip = realIp;
            } else {
                if (forwarded != null) {
                    forwarded = forwarded.split(BaseConsts.HEADER_SUFFIX)[0];
                }
                ip = /*realIp + BaseConsts.HTTP_SUFFIX + */forwarded;
            }
        }
        return ip;
    }
}
