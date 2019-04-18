package com.rzlearn.gateway.security;

import com.rzlearn.base.dto.ResultMsg;
import com.rzlearn.base.exception.BusinessException;
import com.rzlearn.base.support.MessageCode;
import com.rzlearn.user.dto.UserInfoWithPermissionDTO;
import com.rzlearn.user.feign.IUserController;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * UserDetailsService重写.
 *
 * @author zhangwb
 */
@Component("userDetailsService")
@Slf4j
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private IUserController userController;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public UserDetails loadUserByUsername(final String login) {
        ResultMsg<UserInfoWithPermissionDTO> rsg = userController.getOneAndPermissionByName(login);
        rsg.checkBusinessException();
        UserInfoWithPermissionDTO userInfoWithPermissionDTO = rsg.getResultObject();

        Optional<UserInfoWithPermissionDTO> userByLoginFromDatabase = Optional.of(userInfoWithPermissionDTO);
        return userByLoginFromDatabase.map(user -> createSpringSecurityUser(user))
                .orElseThrow(() -> new BusinessException(MessageCode.USER_IS_NULL));
    }

    private org.springframework.security.core.userdetails.User createSpringSecurityUser(UserInfoWithPermissionDTO runschUserDTO) {
        List<GrantedAuthority> grantedAuthorities = runschUserDTO.getPermissions().stream()
                .map(authority -> new SimpleGrantedAuthority(authority))
                .collect(Collectors.toList());
        return new org.springframework.security.core.userdetails.User(runschUserDTO.getUserName(),
                runschUserDTO.getPassword(),
                grantedAuthorities);
    }
}
