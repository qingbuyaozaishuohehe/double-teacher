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
 * @date 2018-06-07 18:49
 **/
@Data
@ApiModel("查询平台用户实体描述")
public class GetPlatformUserDTO {
    /**
     * 主键
     */
    @ApiModelProperty(value="用户id")
    private Long id;

    /**
     * 组织id
     */
    @ApiModelProperty(value="组织id")
    private Long organizationId;

    /**
     * 姓名
     */
    @ApiModelProperty(value="用户姓名")
    private String name;

    /**
     * 用户名
     */
    @ApiModelProperty(value="用户名")
    private String userName;

    /**
     * 手机号码
     */
    @ApiModelProperty(value="手机号码")
    private String phone;

    /**
     * 邮箱地址
     */
    @ApiModelProperty(value="邮箱地址")
    private String email;

    /**
     * 用户角色集合
     */
    @ApiModelProperty(value = "用户角色集合")
    private List<String> roleCodes;
}