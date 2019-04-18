package com.rzlearn.user.mapper;

import com.rzlearn.user.dto.RolePermissionsDTO;
import com.rzlearn.user.entity.RunschPermission;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 用户权限表 Mapper 接口
 * </p>
 *
 * @author JiPeigong
 * @since 2018 -06-07
 */
public interface RunschPermissionMapper extends BaseMapper<RunschPermission> {

    /**
     * List role permisssion list.
     *
     * @param roleCode the role code
     * @param language the language
     * @return the list
     */
    List<RolePermissionsDTO> listRolePermission(@Param(value = "roleCode") String roleCode, @Param(value = "language") String language);
}
