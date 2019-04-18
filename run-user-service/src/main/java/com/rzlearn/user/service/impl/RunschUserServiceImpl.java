package com.rzlearn.user.service.impl;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.rzlearn.user.dto.GetPageUserDTO;
import com.rzlearn.user.dto.GetUserDTO;
import com.rzlearn.user.dto.PageUserDTO;
import com.rzlearn.user.entity.RunschUser;
import com.rzlearn.user.mapper.RunschUserMapper;
import com.rzlearn.user.service.IRunschUserService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author JiPeigong
 * @since 2018-06-07
 */
@Service
public class RunschUserServiceImpl extends ServiceImpl<RunschUserMapper, RunschUser> implements IRunschUserService {

    @Override
    public Page<GetPageUserDTO> pageUser(Page<GetPageUserDTO> page, PageUserDTO pageUserDTO) {
        return page.setRecords(baseMapper.pageUser(page, pageUserDTO));
    }

    @Override
    public List<GetUserDTO> listUserByRoleAndDomain(Set<String> roleCodes, Long domainId) {
        return baseMapper.listUserByRoleAndDomain(roleCodes, domainId);
    }
}
