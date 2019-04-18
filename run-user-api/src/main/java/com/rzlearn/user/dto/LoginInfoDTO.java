package com.rzlearn.user.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * 登录请求.
 *
 * @author zhangwb
 */
@Data
@ApiModel("登录信息描述")
public class LoginInfoDTO {

    /**
     * 用户名
     */
    @ApiModelProperty(value="用户名")
    private String userName;

    /**
     * 组织id
     */
    @ApiModelProperty(value="组织id")
    private Long organizationId;

    /**
     * web app id
     */
    @ApiModelProperty(value="应用id")
    private Long domainId;

    /**
     * 姓名
     */
    @ApiModelProperty(value="姓名")
    private String name;

    /**
     * 头像
     */
    @ApiModelProperty(value="头像")
    private String avatar;

    /**
     * 角色集合
     */
    @ApiModelProperty(value="角色集合")
    private List<String> roles;
}
