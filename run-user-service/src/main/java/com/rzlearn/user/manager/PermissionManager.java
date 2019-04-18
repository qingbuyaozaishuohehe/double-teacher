package com.rzlearn.user.manager;

import com.rzlearn.user.dto.RolePermissionsDTO;
import com.rzlearn.user.service.IRunschPermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>ClassName:PermissionManager</p>
 * <p>Description:用户权限管理</p>
 *
 * @author JiPeigong
 * @date 2019-01-10 10:35:15
 **/
@Service
public class PermissionManager {

    public static final String LANGUAGE = "language";
    public static final String PERMISSION_NAME = "permission_name";

    @Autowired
    private IRunschPermissionService runschPermissionService;

    /**
     * List role permisssion list.
     *
     * @param roleCode the role code
     * @param language the language
     * @return the list
     */
    @Cacheable(value = "ROLE_PERMISSIONS_", key = "'ROLE_PERMISSIONS_'+#roleCode+'_'+#language")
    public List<RolePermissionsDTO> listRolePermission(String roleCode, String language) {
        // 根据角色id查询所有权限
        return runschPermissionService.listRolePermission(roleCode, language);
    }
}