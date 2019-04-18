package com.rzlearn.user.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

/**
 * 用户名密码和权限信息.
 *
 * @author zhangwb
 */
@Data
@ApiModel("用户及权限描述")
public class UserInfoWithPermissionDTO {

    /**
     * 用户名称
     */
    @ApiModelProperty(value="用户名称")
    private String userName;

    /**
     * 组织id
     */
    @ApiModelProperty(value="组织id")
    private Long organizationId;

    /**
     * 密码
     */
    @ApiModelProperty(value="密码")
    private String password;

    /**
     * 权限集合
     */
    @ApiModelProperty(value="权限集合")
    private Set<String> permissions;

    /**
     * 角色集合
     */
    @ApiModelProperty(value="角色集合")
    private Set<String> roles;
}
