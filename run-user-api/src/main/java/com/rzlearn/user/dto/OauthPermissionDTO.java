package com.rzlearn.user.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>ClassName:OauthPermissionDTO</p>
 * <p>Description:</p>
 * @author JiPeigong
 * @date 2018-12-21 13:16:46
 **/

@Data
@ApiModel("映射权限")
public class OauthPermissionDTO {

    private Long id;
    /**
     * 权限名称
     */
    @ApiModelProperty(value="权限名称")
    private String permissionName;


    /**
     * url
     */
    @ApiModelProperty(value="url")
    private String url;


}
