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
@ApiModel("新增用户实体描述")
public class SaveUserDTO {

    /**
     * 主键
     */
    @ApiModelProperty(value="用户id")
    private Long id;

    /**
     * 默认应用
     */
    @ApiModelProperty(value="默认应用")
    private Long domainId;

    /**
     * 组织id
     */
    @ApiModelProperty(value="组织id",required=true)
    @NotNull
    private Long organizationId;

    /**
     * 角色code集合
     */
    @ApiModelProperty(value="角色Code",required=true)
    @NotNull
    private LinkedHashSet<String> roleCodes;

    /**
     * 姓名
     */
    @ApiModelProperty(value="用户姓名",required=true)
    @NotNull
    private String name;

    /**
     * 用户名
     */
    @ApiModelProperty(value="用户名",required=true)
    @NotNull
    private String userName;

    /**
     * 用户密码
     */
    @ApiModelProperty(value="用户密码",required=true)
    @NotNull
    private String passwordHash;

    /**
     * 手机号码
     */
    @ApiModelProperty(value="手机号码",required=true)
    @NotNull
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

}
