package com.rzlearn.setting.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * <p>ClassName:GetPageWebAppConfigDTO</p>
 * <p>Description:软件应用配置信息实体</p>
 *
 * @author JiPeigong
 * @date 2018-08-06 14:49
 **/
@Data
@ApiModel("软件应用配置信息实体")
public class GetPageWebAppConfigDTO {

    /**
     * id
     */
    @ApiModelProperty(value = "id")
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
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间")
    private Date gmtCreate;

    /**
     * 修改时间
     */
    @ApiModelProperty(value = "修改时间")
    private Date gmtModified;
}
