package com.rzlearn.setting.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * <p>ClassName:SaveWebAppConfigDTO</p>
 * <p>Description:修改web应用配置信息实体</p>
 *
 * @author JiPeigong
 * @date 2018-08-06 14:49
 **/
@Data
@ApiModel("获取web应用配置信息实体")
public class GetWebAppConfigDTO {

    /**
     * 主键id
     */
    @NotNull
    @ApiModelProperty(value = "主键id", required = true)
    private Long id;

    /**
     * 域名
     */
    @ApiModelProperty(value = "域名")
    private String domainName;

    /**
     * 名称
     */
    @ApiModelProperty(value = "web名称")
    private String name;

    /**
     * 登录页提示语
     */
    @ApiModelProperty(value = "登录页提示语")
    private String loginTitle;

    /**
     * 登陆页底部提示语
     */
    @ApiModelProperty(value = "登陆页底部提示语")
    private String loginBottomHint;

    @ApiModelProperty(value = "登陆logo id")
    private String loginLogoId;

    @ApiModelProperty(value = "首页logo id")
    private String homeLogoId;

    @ApiModelProperty(value = "重置密码logo id")
    private String forgetLogoId;

    /**
     * 首页标题
     */
    @ApiModelProperty(value = "首页标题")
    private String homeTitle;

    /**
     * poweredBy
     */
    @ApiModelProperty(value = "poweredBy")
    private String poweredBy;
}
