package com.rzlearn.user.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>ClassName:PermissionDTO</p>
 * <p>Description:</p>
 * @author JiPeigong
 * @date 2018-12-19 11:13:47
 **/
@Data
@ApiModel("权限描述")
public class PermissionDTO {

    private Long id;
    /**
     * 权限名称
     */
    @ApiModelProperty(value="权限名称")
    private String permissionName;

    /**
     * 对应页面的菜单名称
     */
    @ApiModelProperty(value="菜单名称")
    private String menuName;

    /**
     * 父id
     */
    @ApiModelProperty(value="父id")
    private Long parentId;

    /**
     * 子菜单集合
     */
    @ApiModelProperty(value="子菜单集合")
    private List<PermissionDTO> children;

}
