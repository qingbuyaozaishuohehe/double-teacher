package com.rzlearn.user.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.rzlearn.user.dto.GetPageUserDTO;
import com.rzlearn.user.dto.GetUserDTO;
import com.rzlearn.user.dto.PageUserDTO;
import com.rzlearn.user.entity.RunschUser;
import com.baomidou.mybatisplus.service.IService;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Set;

/**
 * <p>
 * 用户表 服务类
 * </p>
 *
 * @author JiPeigong
 * @since 2018 -06-07
 */
public interface IRunschUserService extends IService<RunschUser> {

    /**
     * Page user list.
     *
     * @param page        the page
     * @param pageUserDTO the page user dto
     * @return the list
     */
    Page<GetPageUserDTO> pageUser(Page<GetPageUserDTO> page, PageUserDTO pageUserDTO);

    /**
     * List user by role and domain list.
     *
     * @param roleCodes the role codes
     * @param domainId  the domain id
     * @return the list
     */
    List<GetUserDTO> listUserByRoleAndDomain(Set<String> roleCodes, Long domainId);

}
