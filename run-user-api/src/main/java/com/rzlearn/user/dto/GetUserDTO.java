package com.rzlearn.user.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
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
@ApiModel("查询用户实体描述")
public class GetUserDTO {
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
     * domainId
     */
    @ApiModelProperty(value="domainId")
    private Long domainId;

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
     * 用户头像地址
     */
    @ApiModelProperty(value="用户头像地址")
    private String avatar;

    /**
     * qq号
     */
    @ApiModelProperty(value="qq号")
    private String qq;

    /**
     * 微信号
     */
    @ApiModelProperty(value="微信号")
    private String weiXin;

    /**
     * 微博号
     */
    @ApiModelProperty(value="微博号")
    private String weiBo;

    /**
     * 性别(1：男；0：女)
     */
    @ApiModelProperty(value="性别")
    private String sex;

    /**
     * 住址
     */
    @ApiModelProperty(value="住址")
    private String address;

    /**
     * 身份证号码
     */
    @ApiModelProperty(value="身份证号码")
    private String idCard;

    /**
     * 生日
     */
    @ApiModelProperty(value="生日")
    private Date birthday;
    /**
     * 用户角色集合
     */
    @ApiModelProperty(value = "用户角色集合")
    private Set<String> roleCodes;

    /**
     * 用户默认角色
     */
    @ApiModelProperty(value = "用户默认角色")
    private String defaultRole;

}