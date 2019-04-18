package com.rzlearn.user.service.impl;

import com.rzlearn.user.dto.RolePermissionsDTO;
import com.rzlearn.user.entity.RunschPermission;
import com.rzlearn.user.mapper.RunschPermissionMapper;
import com.rzlearn.user.service.IRunschPermissionService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 用户权限表 服务实现类
 * </p>
 *
 * @author JiPeigong
 * @since 2018-06-07
 */
@Service
public class RunschPermissionServiceImpl extends ServiceImpl<RunschPermissionMapper, RunschPermission> implements IRunschPermissionService {

    @Override
    public List<RolePermissionsDTO> listRolePermission(String roleCode, String language) {
        return baseMapper.listRolePermission(roleCode, language);
    }
}
