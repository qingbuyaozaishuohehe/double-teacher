package com.rzlearn.user.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * <p>ClassName:SaveUserDTO</p>
 * <p>Description:</p>
 *
 * @author JiPeigong
 * @date 2018 -06-07 18:49
 */
@Data
@ApiModel("获取分页查询用户实体描述")
public class GetPageUserDTO {
    /**
     * 主键
     */
    @ApiModelProperty(value = "用户id")
    private Long id;

    /**
     * 组织id
     */
    @ApiModelProperty(value = "组织id")
    private Long organizationId;

    /**
     * 应用id
     */
    @ApiModelProperty(value = "应用id")
    private Long domainId;

    /**
     * 姓名
     */
    @ApiModelProperty(value = "用户姓名")
    private String name;

    /**
     * 用户状态
     */
    @ApiModelProperty(value = "用户状态")
    private String state;

    /**
     * 用户角色集合
     */
    @ApiModelProperty(value = "用户角色集合")
    private List<String> roles;

    /**
     * roleCodes
     */
    @ApiModelProperty(value = "角色code字符串")
    private String roleCodes;

    /**
     * 用户名
     */
    @ApiModelProperty(value = "用户名")
    private String userName;

    /**
     * 手机号码
     */
    @ApiModelProperty(value = "手机号码")
    private String phone;

    /**
     * 邮箱地址
     */
    @ApiModelProperty(value = "邮箱地址")
    private String email;

    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间")
    private Date gmtCreate;

    /**
     * 最后登录时间
     */
    @ApiModelProperty(value = "最后登录时间")
    private Date lastLoginDate;
}