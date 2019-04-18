package com.rzlearn.user.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/**
 * <p>ClassName:SaveUserDTO</p>
 * <p>Description:</p>
 *
 * @author JiPeigong
 * @date 2018-06-07 18:49
 **/
@Data
@ApiModel("修改平台用户实体描述")
public class UpdatePlatfromUserDTO {
    /**
     * 主键
     */
    @ApiModelProperty(value = "用户id", required = true)
    @NotNull
    private Long id;

    /**
     * 角色code集合
     */
    @ApiModelProperty(value = "角色Code")
    private LinkedHashSet<String> roleCodes;

    /**
     * 组织id
     */
    @ApiModelProperty(value="组织id",required=true)
    @NotNull
    private Long organizationId;

    /**
     * 姓名
     */
    @ApiModelProperty(value = "用户姓名")
    private String name;

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
     * 密码
     */
    @ApiModelProperty(value = "密码")
    private String password;
}
