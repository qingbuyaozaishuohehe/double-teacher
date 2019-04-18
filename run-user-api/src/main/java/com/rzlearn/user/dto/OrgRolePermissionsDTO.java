package com.rzlearn.user.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * The type Org role permissions dto.
 *
 * @author JiPeigong
 */
@Data
@ApiModel("组织角色权限描述")
public class OrgRolePermissionsDTO implements Serializable {

    private static final long serialVersionUID = -8204445174666764034L;

    /**
     * 权限id
     */
    @ApiModelProperty(value="权限id")
    private Long id;

    /**
     * 组织id
     */
    @ApiModelProperty(value="组织id")
    private Long organizationId;

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
     * 父权限名称
     */
    @ApiModelProperty(value="父id")
    private Long parentId;

}
