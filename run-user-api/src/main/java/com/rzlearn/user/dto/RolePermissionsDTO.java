package com.rzlearn.user.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * <p>ClassName:RolePermissionsDTO</p>
 * <p>Description:</p>
 * @author JiPeigong
 * @date 2018-06-14 15:50:51
 **/
@Data
@ApiModel("角色权限描述")
public class RolePermissionsDTO {

    /**
     * 权限id
     */
    @ApiModelProperty(value="权限id")
    private Long id;

    /**
     * 权限名称
     */
    @ApiModelProperty(value="权限名称")
    private String permissionName;

    /**
     * 菜单名称
     */
    @ApiModelProperty(value="菜单名称")
    private String menuName;

    /**
     * 父权限id
     */
    @ApiModelProperty(value="父权限id")
    private Long parentId;
}
