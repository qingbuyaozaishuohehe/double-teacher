package com.rzlearn.setting.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
import java.util.Set;

/**
 * <p>ClassName:SaveUserDTO</p>
 * <p>Description:</p>
 *
 * @author JiPeigong
 * @date 2018-06-07 18:49
 **/
@Data
@ApiModel("查询用户实体")
public class GetDomainUserDTO {
    /**
     * 主键
     */
    @ApiModelProperty(value="用户id")
    private Long id;

    /**
     * domainId
     */
    @ApiModelProperty(value="domainId")
    private Long domainId;

    /**
     * 用户名
     */
    @ApiModelProperty(value="用户名")
    private String userName;

}