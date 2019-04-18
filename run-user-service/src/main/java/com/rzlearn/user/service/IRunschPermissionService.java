package com.rzlearn.user.service;

import com.rzlearn.user.dto.RolePermissionsDTO;
import com.rzlearn.user.entity.RunschPermission;
import com.baomidou.mybatisplus.service.IService;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 用户权限表 服务类
 * </p>
 *
 * @author JiPeigong
 * @since 2018 -06-07
 */
public interface IRunschPermissionService extends IService<RunschPermission> {

    /**
     * List role permission list.
     *
     * @param roleCode the role code
     * @param language the language
     * @return the list
     */
    List<RolePermissionsDTO> listRolePermission(String roleCode, String language);

}
