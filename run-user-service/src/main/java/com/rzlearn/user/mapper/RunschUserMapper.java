package com.rzlearn.user.mapper;

import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.rzlearn.base.basic.Page;
import com.rzlearn.user.dto.GetPageUserDTO;
import com.rzlearn.user.dto.GetUserDTO;
import com.rzlearn.user.dto.PageUserDTO;
import com.rzlearn.user.entity.RunschUser;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Set;

/**
 * <p>
 * 用户表 Mapper 接口
 * </p>
 *
 * @author JiPeigong
 * @since 2018 -06-07
 */
public interface RunschUserMapper extends BaseMapper<RunschUser> {

    /**
     * Page user list.
     *
     * @param page        the page
     * @param pageUserDTO the page user dto
     * @return the list
     */
    List<GetPageUserDTO> pageUser(Pagination page, PageUserDTO pageUserDTO);

    /**
     * List user by role and domain list.
     *
     * @param roleCodes the role codes
     * @param domainId  the domain id
     * @return the list
     */
    List<GetUserDTO> listUserByRoleAndDomain(@Param(value = "roleCodes")Set<String> roleCodes, @Param(value = "domainId")Long domainId);


}
