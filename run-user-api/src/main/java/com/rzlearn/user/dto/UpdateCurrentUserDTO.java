package com.rzlearn.user.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * <p>ClassName:SaveUserDTO</p>
 * <p>Description:</p>
 *
 * @author JiPeigong
 * @date 2018-06-07 18:49
 **/
@Data
@ApiModel("修改当前用户信息")
public class UpdateCurrentUserDTO {

    /**
     * 姓名
     */
    @ApiModelProperty(value = "用户姓名")
    private String name;

    /**
     * 邮箱地址
     */
    @ApiModelProperty(value = "邮箱地址")
    private String email;

    /**
     * 用户头像附件id
     */
    @ApiModelProperty(value = "用户头像附件id")
    private String avatar;

    /**
     * qq号
     */
    @ApiModelProperty(value = "qq号")
    private String qq;

    /**
     * 微信号
     */
    @ApiModelProperty(value = "微信号")
    private String weiXin;

    /**
     * 微博号
     */
    @ApiModelProperty(value = "微博号")
    private String weiBo;

    /**
     * 性别(1：男；0：女)
     */
    @ApiModelProperty(value = "性别")
    private String sex;

    /**
     * 住址
     */
    @ApiModelProperty(value = "住址")
    private String address;

    /**
     * 身份证号码
     */
    @ApiModelProperty(value = "身份证号码")
    private String idCard;

    /**
     * 生日
     */
    @ApiModelProperty(value = "生日")
    private Date birthday;

}
