package com.rzlearn.base.dto;

import lombok.Data;

import java.util.Set;

/**
 * 用户信息.
 *
 * @author zhangwb
 */
@Data
public class HeaderUserInfoDTO {

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 应用id
     */
    private Long domainId;

    /**
     * 组织id
     */
    private Long organizationId;

    /**
     * 用户名
     */
    private String userName;

    /**
     * 语言
     */
    private String language;

    /**
     * 角色集合
     */
    private Set<String> roles;

    /**
     * 默认角色
     */
    private String defaultRole;

    /**
     * 权限集合
     */
    private Set<String> permissions;
}
