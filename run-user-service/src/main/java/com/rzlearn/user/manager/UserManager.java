package com.rzlearn.user.manager;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.mapper.Condition;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.rzlearn.base.basic.Page;
import com.rzlearn.base.constant.BaseConsts;
import com.rzlearn.base.dto.HeaderUserInfoDTO;
import com.rzlearn.base.exception.BusinessException;
import com.rzlearn.base.support.MessageCode;
import com.rzlearn.base.util.ListUtil;
import com.rzlearn.base.util.LogRecordUtil;
import com.rzlearn.base.util.SecurityUtil;
import com.rzlearn.base.util.WebUtils;
import com.rzlearn.common.constant.DicConsts;
import com.rzlearn.common.dto.GetDicDTO;
import com.rzlearn.user.common.util.RedisUtil;
import com.rzlearn.user.constant.BusinessConst;
import com.rzlearn.user.constant.RedisConst;
import com.rzlearn.user.dto.*;
import com.rzlearn.user.entity.RunschPermission;
import com.rzlearn.user.entity.RunschRolePermission;
import com.rzlearn.user.entity.RunschUser;
import com.rzlearn.user.entity.RunschUserRole;
import com.rzlearn.user.service.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * <p>ClassName:UserManager</p>
 * <p>Description:</p>
 *
 * @author JiPeigong
 * @date 2018 -06-07 18:03
 */
@Service
@Slf4j
public class UserManager {

    public static final String USER_NAME = "user_name";
    public static final String PHONE = "phone";

    @Autowired
    private IRunschUserService runschUserService;

    @Autowired
    private IRunschUserRoleService runschUserRoleService;

    @Autowired
    private IRunschRolePermissionService runschRolePermissionService;

    @Autowired
    private IRunschPermissionService runschPermissionService;

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private IUserService userService;

    @Autowired
    private PermissionManager permissionManager;

    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * 判断
     *
     * @param phone
     * @param userId
     * @return
     */
    private Boolean checkPhoneExist(String phone, Long userId) {
        if (StringUtils.isNotEmpty(phone)) {
            RunschUser runschUser = new RunschUser();
            runschUser.setPhone(phone);
            EntityWrapper wrapper = new EntityWrapper(runschUser);
            if (userId != null) {
                wrapper.notIn("id", userId);
            }
            runschUser = runschUserService.selectOne(wrapper);

            if (runschUser == null) {
                runschUser = new RunschUser();
                runschUser.setPhone(SecurityUtil.encryptDes(phone));
                EntityWrapper wrapper2 = new EntityWrapper(runschUser);
                if (userId != null) {
                    wrapper2.notIn("id", userId);
                }
                runschUser = runschUserService.selectOne(wrapper2);

                if (runschUser == null) {
                    return false;
                } else {
                    return true;
                }
            } else {
                return true;
            }
        }
        return false;
    }

    /**
     * Gets user by id.
     *
     * @param id the id
     * @return the user by id
     */
    public GetUserDTO getUserById(Long id) {
        GetUserDTO getUserDTO = new GetUserDTO();
        RunschUser runschUser = runschUserService.selectById(id);
        if (runschUser == null) {
            throw new BusinessException(MessageCode.USER_IS_NULL);
        }
        decryptUser(runschUser);
        getUserDTO.setId(runschUser.getId());
        getUserDTO.setName(runschUser.getName());
        getUserDTO.setEmail(runschUser.getEmail());
        getUserDTO.setAddress(runschUser.getAddress());
        getUserDTO.setBirthday(runschUser.getBirthday());
        getUserDTO.setEmail(runschUser.getEmail());
        getUserDTO.setOrganizationId(runschUser.getOrganizationId());
        getUserDTO.setDomainId(runschUser.getDomainId());
        getUserDTO.setUserName(runschUser.getUserName());
        getUserDTO.setPhone(runschUser.getPhone());
        getUserDTO.setAvatar(runschUser.getAvatar());
        getUserDTO.setQq(runschUser.getQq());
        getUserDTO.setWeiXin(runschUser.getWeiXin());
        getUserDTO.setWeiBo(runschUser.getWeiBo());
        getUserDTO.setSex(runschUser.getSex());
        getUserDTO.setIdCard(runschUser.getIdCard());

        RunschUserRole runschUserRole = new RunschUserRole();
        runschUserRole.setUserId(id);
        List<RunschUserRole> userRoleList = runschUserRoleService.selectList(new EntityWrapper<>(runschUserRole));

        if (userRoleList != null) {
            getUserDTO.setRoleCodes(userRoleList.stream().map(RunschUserRole::getRoleCode).collect(Collectors.toSet()));
        }

        for (RunschUserRole userRole : userRoleList) {
            if (userRole.getIsDefault() == 1) {
                getUserDTO.setDefaultRole(userRole.getRoleCode());
            }
        }

        if (StringUtils.isEmpty(getUserDTO.getDefaultRole()) && ListUtil.isNotEmpty(userRoleList)) {
            getUserDTO.setDefaultRole(userRoleList.get(0).getRoleCode());
        }

        return getUserDTO;
    }

    /**
     * Gets platform user by id.
     *
     * @param id the id
     * @return the platform user by id
     */
    public GetPlatformUserDTO getPlatformUserById(Long id) {
        GetPlatformUserDTO getPlatformUserDTO = new GetPlatformUserDTO();
        RunschUser runschUser = runschUserService.selectById(id);
        if (runschUser == null) {
            throw new BusinessException(MessageCode.USER_IS_NULL);
        }
        decryptUser(runschUser);
        getPlatformUserDTO.setId(runschUser.getId());
        getPlatformUserDTO.setName(runschUser.getName());
        getPlatformUserDTO.setEmail(runschUser.getEmail());
        getPlatformUserDTO.setEmail(runschUser.getEmail());
        getPlatformUserDTO.setOrganizationId(runschUser.getOrganizationId());
        getPlatformUserDTO.setUserName(runschUser.getUserName());
        getPlatformUserDTO.setPhone(runschUser.getPhone());

        RunschUserRole runschUserRole = new RunschUserRole();
        runschUserRole.setUserId(runschUser.getId());
        List<RunschUserRole> userRoleList = runschUserRoleService.selectList(new EntityWrapper<>(runschUserRole));

        if (userRoleList != null) {
            getPlatformUserDTO.setRoleCodes(userRoleList.stream().map(RunschUserRole::getRoleCode).collect(Collectors.toList()));
        }
        return getPlatformUserDTO;
    }

    /**
     * Gets users by ids.
     *
     * @param ids the ids
     * @return the users by ids
     */
    public List<GetUserDTO> getUsersByIds(List<Long> ids) {
        List<GetUserDTO> listUserDTO = new ArrayList<GetUserDTO>();
        List<RunschUser> listUser = runschUserService.selectBatchIds(ids);
        if (listUser == null || listUser.size() == 0) {
            throw new BusinessException(MessageCode.USER_IS_NULL);
        }
        decryptUsers(listUser);
        for (RunschUser runschUser : listUser) {
            listUserDTO.add(initGetUserDTO(runschUser));
        }

        return listUserDTO;
    }

    /**
     * @param runschUser
     * @return
     */
    private GetUserDTO initGetUserDTO(RunschUser runschUser) {
        GetUserDTO getUserDTO = new GetUserDTO();
        getUserDTO.setId(runschUser.getId());
        getUserDTO.setName(runschUser.getName());
        getUserDTO.setEmail(runschUser.getEmail());
        getUserDTO.setAddress(runschUser.getAddress());
        getUserDTO.setBirthday(runschUser.getBirthday());
        getUserDTO.setEmail(runschUser.getEmail());
        getUserDTO.setOrganizationId(runschUser.getOrganizationId());
        getUserDTO.setUserName(runschUser.getUserName());
        getUserDTO.setPhone(runschUser.getPhone());
        getUserDTO.setAvatar(runschUser.getAvatar());
        getUserDTO.setQq(runschUser.getQq());
        getUserDTO.setWeiXin(runschUser.getWeiXin());
        getUserDTO.setWeiBo(runschUser.getWeiBo());
        getUserDTO.setSex(runschUser.getSex());
        getUserDTO.setIdCard(runschUser.getIdCard());
        return getUserDTO;
    }

    /**
     * Gets user by userName.
     *
     * @param userName the user name
     * @return the user by userName
     */
    public GetUserDTO getUserByUserName(String userName) {
        if (StringUtils.isEmpty(userName)) {
            return null;
        }
        GetUserDTO getUserDTO = new GetUserDTO();
        RunschUser runschUser = runschUserService.selectOne(new EntityWrapper<RunschUser>().eq(USER_NAME, userName));
        if (runschUser == null) {
            throw new BusinessException(MessageCode.USER_IS_NULL);
        }
        decryptUser(runschUser);
        getUserDTO.setId(runschUser.getId());
        getUserDTO.setName(runschUser.getName());
        getUserDTO.setEmail(runschUser.getEmail());
        getUserDTO.setAddress(runschUser.getAddress());
        getUserDTO.setBirthday(runschUser.getBirthday());
        getUserDTO.setEmail(runschUser.getEmail());
        getUserDTO.setOrganizationId(runschUser.getOrganizationId());
        getUserDTO.setDomainId(runschUser.getDomainId());
        getUserDTO.setUserName(runschUser.getUserName());
        getUserDTO.setPhone(runschUser.getPhone());
        getUserDTO.setAvatar(runschUser.getAvatar());
        getUserDTO.setQq(runschUser.getQq());
        getUserDTO.setWeiXin(runschUser.getWeiXin());
        getUserDTO.setWeiBo(runschUser.getWeiBo());
        getUserDTO.setSex(runschUser.getSex());
        getUserDTO.setIdCard(runschUser.getIdCard());
        return getUserDTO;
    }


    /**
     * Gets user by user names.
     *
     * @param userNames the user names
     * @return the user by user names
     */
    public List<GetUserDTO> getUserByUserNames(List<String> userNames) {
        List<GetUserDTO> getUserDTOList = new ArrayList<>(16);
        if (ListUtil.isEmpty(userNames)) {
            return getUserDTOList;
        }
        List<RunschUser> runschUserList = runschUserService.selectList(new Condition().in(USER_NAME, userNames));
        if (ListUtil.isNotEmpty(runschUserList)) {
            for (RunschUser runschUser : runschUserList) {
                GetUserDTO getUserDTO = new GetUserDTO();
                decryptUser(runschUser);
                getUserDTO.setId(runschUser.getId());
                getUserDTO.setName(runschUser.getName());
                getUserDTO.setEmail(runschUser.getEmail());
                getUserDTO.setAddress(runschUser.getAddress());
                getUserDTO.setBirthday(runschUser.getBirthday());
                getUserDTO.setEmail(runschUser.getEmail());
                getUserDTO.setOrganizationId(runschUser.getOrganizationId());
                getUserDTO.setUserName(runschUser.getUserName());
                getUserDTO.setPhone(runschUser.getPhone());
                getUserDTO.setAvatar(runschUser.getAvatar());
                getUserDTO.setQq(runschUser.getQq());
                getUserDTO.setWeiXin(runschUser.getWeiXin());
                getUserDTO.setWeiBo(runschUser.getWeiBo());
                getUserDTO.setSex(runschUser.getSex());
                getUserDTO.setIdCard(runschUser.getIdCard());
                getUserDTOList.add(getUserDTO);
            }
        }
        return getUserDTOList;
    }

    /**
     * Save user boolean.
     *
     * @param saveUserDTO the user dto
     * @return the boolean
     */
    @Transactional(rollbackFor = Exception.class)
    public SaveUserDTO saveUser(SaveUserDTO saveUserDTO) {
        if (saveUserDTO.getId() == null) {
            throw new BusinessException(MessageCode.PARAM_ILLEGAL);
        }
        RunschUser checkUser = new RunschUser();
        checkUser.setUserName(saveUserDTO.getUserName());
        checkUser = runschUserService.selectOne(new EntityWrapper<>(checkUser));
        if (checkUser != null) {
            throw new BusinessException(MessageCode.USER_NAME_IS_EXIST);
        }

        if (checkPhoneExist(saveUserDTO.getPhone(), null)) {
            throw new BusinessException(MessageCode.USER_PHONE_IS_EXIST);
        }

        Boolean result;
        RunschUser runschUser = initRunschUser(saveUserDTO);
        encryptUser(runschUser);
        result = runschUserService.insert(runschUser);
        if (!result) {
            throw new BusinessException(MessageCode.CRAETE_FAILED);
        }
        if (result && saveUserDTO.getRoleCodes() != null && saveUserDTO.getRoleCodes().size() > 0) {
            List<RunschUserRole> userRoles = new ArrayList<>();
            initUserRoles(userRoles, saveUserDTO.getRoleCodes(), runschUser.getId());
            result = runschUserRoleService.insertBatch(userRoles);
        }
        if (!result) {
            throw new BusinessException(MessageCode.CRAETE_FAILED);
        }
        saveUserDTO.setId(runschUser.getId());
        return saveUserDTO;
    }

    /**
     * @param runschUser
     */
    private void encryptUser(RunschUser runschUser) {
        if (StringUtils.isNotEmpty(runschUser.getPhone())) {
            runschUser.setPhone(SecurityUtil.encryptDes(runschUser.getPhone()));
        }
        if (StringUtils.isNotEmpty(runschUser.getEmail())) {
            runschUser.setEmail(SecurityUtil.encryptDes(runschUser.getEmail()));
        }
        if (StringUtils.isNotEmpty(runschUser.getIdCard())) {
            runschUser.setIdCard(SecurityUtil.encryptDes(runschUser.getIdCard()));
        }
        if (StringUtils.isNotEmpty(runschUser.getQq())) {
            runschUser.setQq(SecurityUtil.encryptDes(runschUser.getQq()));
        }
        if (StringUtils.isNotEmpty(runschUser.getWeiXin())) {
            runschUser.setWeiXin(SecurityUtil.encryptDes(runschUser.getWeiXin()));
        }
        if (StringUtils.isNotEmpty(runschUser.getWeiBo())) {
            runschUser.setWeiBo(SecurityUtil.encryptDes(runschUser.getWeiBo()));
        }
        if (StringUtils.isNotEmpty(runschUser.getAddress())) {
            runschUser.setAddress(SecurityUtil.encryptDes(runschUser.getAddress()));
        }

    }

    /**
     * 加密
     *
     * @param pageUserDTO
     */
    private void encryptPageUserDTO(PageUserDTO pageUserDTO) {
        if (StringUtils.isNotEmpty(pageUserDTO.getPhoneNum())) {
            pageUserDTO.setPhoneNum(SecurityUtil.encryptDes(pageUserDTO.getPhoneNum()));
        }
    }

    /**
     * 加密
     *
     * @param runschUserList
     */
    private void encryptUsers(List<RunschUser> runschUserList) {
        if (ListUtil.isNotEmpty(runschUserList)) {
            for (RunschUser runschUser : runschUserList) {
                encryptUser(runschUser);
            }
        }
    }

    /**
     * 解密
     *
     * @param runschUser
     * @return
     */
    private RunschUser decryptUser(RunschUser runschUser) {
        if (StringUtils.isNotEmpty(runschUser.getPhone())) {
            try {
                runschUser.setPhone(SecurityUtil.decryptDes(runschUser.getPhone()));
            } catch (Exception e) {
            }
        }
        if (StringUtils.isNotEmpty(runschUser.getEmail())) {
            try {
                runschUser.setEmail(SecurityUtil.decryptDes(runschUser.getEmail()));
            } catch (Exception e) {
            }
        }
        if (StringUtils.isNotEmpty(runschUser.getIdCard())) {
            try {
                runschUser.setIdCard(SecurityUtil.decryptDes(runschUser.getIdCard()));
            } catch (Exception e) {
            }
        }
        if (StringUtils.isNotEmpty(runschUser.getQq())) {
            try {
                runschUser.setQq(SecurityUtil.decryptDes(runschUser.getQq()));
            } catch (Exception e) {
            }
        }
        if (StringUtils.isNotEmpty(runschUser.getWeiXin())) {
            try {
                runschUser.setWeiXin(SecurityUtil.decryptDes(runschUser.getWeiXin()));
            } catch (Exception e) {
            }
        }
        if (StringUtils.isNotEmpty(runschUser.getWeiBo())) {
            try {
                runschUser.setWeiBo(SecurityUtil.decryptDes(runschUser.getWeiBo()));
            } catch (Exception e) {
            }
        }
        if (StringUtils.isNotEmpty(runschUser.getAddress())) {
            try {
                runschUser.setAddress(SecurityUtil.decryptDes(runschUser.getAddress()));
            } catch (Exception e) {
            }
        }
        return runschUser;
    }

    /**
     * 解密用户集合
     *
     * @param runschUserList
     * @return
     */
    private List<RunschUser> decryptUsers(List<RunschUser> runschUserList) {
        if (ListUtil.isNotEmpty(runschUserList)) {
            for (RunschUser runschUser : runschUserList) {
                decryptUser(runschUser);
            }
        }
        return runschUserList;
    }

    /**
     * 解密
     *
     * @param user
     */
    private void decryptPageUserDTO(GetPageUserDTO user) {
        if (StringUtils.isNotEmpty(user.getPhone())) {
            try {
                user.setPhone(SecurityUtil.decryptDes(user.getPhone()));
            } catch (Exception e) {
            }
        }
        if (StringUtils.isNotEmpty(user.getEmail())) {
            try {
                user.setEmail(SecurityUtil.decryptDes(user.getEmail()));
            } catch (Exception e) {
            }
        }
    }

    /**
     * Remove user boolean.
     *
     * @param userId the user id
     * @return the boolean
     */
    public Boolean removeUser(Long userId) {
        return runschUserService.deleteById(userId);
    }

    /**
     * Remove batch user boolean.
     *
     * @param userIds the user ids
     * @return the boolean
     */
    public Boolean removeBatchUser(List<Long> userIds) {
        return runschUserService.deleteBatchIds(userIds);
    }

    /**
     * Save platform user save user dto.
     *
     * @param saveUserDTO the save user dto
     * @return the save user dto
     */
    /*@Transactional(rollbackFor = Exception.class)
    public Boolean savePlatformUser(SaveUserDTO saveUserDTO) {
        List<String> roles = saveUserDTO.getRoleCodes();
        if (roles.contains(BusinessConst.ROLE_SUPER_ADMIN)) {
            throw new BusinessException(MessageCode.NO_ACCESS);
        }
        if (roles.contains(BusinessConst.ROLE_BUSINESS_ADMIN)) {
            if (!WebUtils.getCurrentUserRoles().contains(BusinessConst.ROLE_SUPER_ADMIN)) {
                throw new BusinessException(MessageCode.NO_ACCESS);
            }
        }
        if (roles.contains(BusinessConst.ROLE_SUPER_COURSE_DEV_ADMIN) || roles.contains(BusinessConst.ROLE_SUPER_COURSE_DEV)) {
            if (!(WebUtils.getCurrentUserRoles().contains(BusinessConst.ROLE_SUPER_ADMIN) || WebUtils
                    .getCurrentUserRoles().contains(BusinessConst.ROLE_BUSINESS_ADMIN))) {
                throw new BusinessException(MessageCode.NO_ACCESS);
            }
        }
        List<CengineUserNameDTO> cengineUserNameDTOList = new ArrayList<>();
        CengineUserNameDTO cengineUserNameDTO = new CengineUserNameDTO();
        cengineUserNameDTO.setStudentName(saveUserDTO.getUserName());
        cengineUserNameDTOList.add(cengineUserNameDTO);

        CengineAssignCourseDTO cengineAssignCourseDTO = new CengineAssignCourseDTO();
        cengineAssignCourseDTO.setUserNameList(cengineUserNameDTOList);
        cengineAssignCourseDTO.setRoleId(BusinessConst.USER_ROLE_ID);

        ResultMsg<Boolean> resultMsg = cengineSchAffairsController.saveTeacher(cengineAssignCourseDTO);
        resultMsg.checkBusinessException();
        Boolean teachEngineResult = resultMsg.getResultObject();
        if (!teachEngineResult) {
            throw new BusinessException(MessageCode.CRAETE_FAILED);
        }
        Long id = IdWorker.getId();
        saveUserDTO.setId(id);
        return saveUser(saveUserDTO) != null;
    }*/

    /**
     * @param saveUserDTO
     * @return
     */
    private RunschUser initRunschUser(SaveUserDTO saveUserDTO) {
        RunschUser runschUser = new RunschUser();
        runschUser.setId(saveUserDTO.getId());
        if (saveUserDTO.getDomainId() != null) {
            runschUser.setDomainId(saveUserDTO.getDomainId());
        } else {
            Long domainId = WebUtils.getCurrentUserDomainId();
            runschUser.setDomainId(domainId);
        }
        runschUser.setName(saveUserDTO.getName());
        runschUser.setEmail(saveUserDTO.getEmail());
        runschUser.setAddress(saveUserDTO.getAddress());
        runschUser.setBirthday(saveUserDTO.getBirthday());
        runschUser.setEmail(saveUserDTO.getEmail());
        runschUser.setOrganizationId(saveUserDTO.getOrganizationId());
        runschUser.setUserName(saveUserDTO.getUserName());
        String encryptedPassword = passwordEncoder.encode(saveUserDTO.getPasswordHash());
        runschUser.setPasswordHash(encryptedPassword);
        runschUser.setPhone(saveUserDTO.getPhone());
        runschUser.setAvatar(saveUserDTO.getAvatar());
        runschUser.setQq(saveUserDTO.getQq());
        runschUser.setWeiXin(saveUserDTO.getWeiXin());
        runschUser.setWeiBo(saveUserDTO.getWeiBo());
        runschUser.setSex(saveUserDTO.getSex());
        runschUser.setIdCard(saveUserDTO.getIdCard());
        return runschUser;
    }

    /**
     * Save batch user boolean.
     *
     * @param saveUserDTOList the save user dto list
     * @return the boolean
     */
    @Transactional(rollbackFor = Exception.class)
    public Boolean saveBatchUser(List<SaveUserDTO> saveUserDTOList) {
        List<String> userNames = saveUserDTOList.stream().map(SaveUserDTO::getUserName).collect(Collectors.toList());
        Condition condition = new Condition();
        condition.in(USER_NAME, userNames);
        List<RunschUser> checkUserList = runschUserService.selectList(condition);
        if (checkUserList != null && checkUserList.size() > 0) {
            throw new BusinessException(MessageCode.USER_NAME_IS_EXIST, JSON.toJSONString(checkUserList));
        }
        List<String> userPhones = saveUserDTOList.stream().map(SaveUserDTO::getPhone).collect(Collectors.toList());
        condition = new Condition();
        condition.in(PHONE, userPhones);
        checkUserList = runschUserService.selectList(condition);
        if (checkUserList != null && checkUserList.size() > 0) {
            throw new BusinessException(MessageCode.USER_PHONE_IS_EXIST, JSON.toJSONString(checkUserList));
        }
        List<RunschUser> userList = new ArrayList();
        List<RunschUserRole> userRoles = new ArrayList();
        for (SaveUserDTO saveUserDTO : saveUserDTOList) {
            userList.add(initRunschUser(saveUserDTO));
            if (saveUserDTO.getRoleCodes() != null && saveUserDTO.getRoleCodes().size() > 0) {
                initUserRoles(userRoles, saveUserDTO.getRoleCodes(), saveUserDTO.getId());
            }
        }
        encryptUsers(userList);
        Boolean result = runschUserService.insertBatch(userList);
        if (!result) {
            throw new BusinessException(MessageCode.CRAETE_FAILED);
        }

        result = runschUserRoleService.insertBatch(userRoles);

        if (!result) {
            throw new BusinessException(MessageCode.CRAETE_FAILED);
        }

        return true;
    }

    /**
     * Delete batch user boolean.
     *
     * @param ids the ids
     * @return the boolean
     */
    public boolean deleteBatchUser(List<Long> ids) {
        return runschUserService.deleteBatchIds(ids);
    }

    /**
     * Update user boolean.
     *
     * @param updateUserDTO the user dto
     * @return the boolean
     */
    @Transactional(rollbackFor = Exception.class)
    public Boolean updateUser(UpdateUserDTO updateUserDTO) {
        Set<String> roles = updateUserDTO.getRoleCodes();
        if (roles != null && roles.contains(BusinessConst.ROLE_SUPER_ADMIN)) {
            if (!BusinessConst.ROLE_SUPER_ADMIN.equals(WebUtils.getCurrentDefaultRole())) {
                throw new BusinessException(MessageCode.NO_ACCESS);
            }
        }

        if (checkPhoneExist(updateUserDTO.getPhone(), updateUserDTO.getId())) {
            throw new BusinessException(MessageCode.USER_PHONE_IS_EXIST);
        }

        RunschUser runschUser = new RunschUser();
        runschUser.setId(updateUserDTO.getId());
        runschUser.setName(updateUserDTO.getName());
        runschUser.setEmail(updateUserDTO.getEmail());
        runschUser.setAddress(updateUserDTO.getAddress());
        runschUser.setBirthday(updateUserDTO.getBirthday());
        runschUser.setEmail(updateUserDTO.getEmail());
        runschUser.setPhone(updateUserDTO.getPhone());
        runschUser.setAvatar(updateUserDTO.getAvatar());
        runschUser.setQq(updateUserDTO.getQq());
        runschUser.setWeiXin(updateUserDTO.getWeiXin());
        runschUser.setWeiBo(updateUserDTO.getWeiBo());
        runschUser.setSex(updateUserDTO.getSex());
        runschUser.setIdCard(updateUserDTO.getIdCard());
        encryptUser(runschUser);
        boolean result = runschUserService.updateById(runschUser);
        if (!result) {
            throw new BusinessException(MessageCode.UPDATE_FAILED);
        }
        result = initUpdateRoles(result, updateUserDTO.getRoleCodes(), updateUserDTO.getId());
        if (!result) {
            throw new BusinessException(MessageCode.UPDATE_FAILED);
        }

        return result;
    }

    /**
     * Update platform user boolean.
     *
     * @param updatePlatfromUserDTO the update platfrom user dto
     * @return the boolean
     */
    @Transactional(rollbackFor = Exception.class)
    public Boolean updatePlatformUser(UpdatePlatfromUserDTO updatePlatfromUserDTO) {
        Set<String> roles = updatePlatfromUserDTO.getRoleCodes();
        if (roles != null && roles.contains(BusinessConst.ROLE_SUPER_ADMIN)) {
            if (!BusinessConst.ROLE_SUPER_ADMIN.equals(WebUtils.getCurrentDefaultRole())) {
                throw new BusinessException(MessageCode.NO_ACCESS);
            }
        }
        if (checkPhoneExist(updatePlatfromUserDTO.getPhone(), updatePlatfromUserDTO.getId())) {
            throw new BusinessException(MessageCode.USER_PHONE_IS_EXIST);
        }
        RunschUser runschUser = new RunschUser();
        runschUser.setId(updatePlatfromUserDTO.getId());
        runschUser.setName(updatePlatfromUserDTO.getName());
        runschUser.setEmail(updatePlatfromUserDTO.getEmail());
        runschUser.setPhone(updatePlatfromUserDTO.getPhone());
        runschUser.setOrganizationId(updatePlatfromUserDTO.getOrganizationId());
        if (StringUtils.isNotEmpty(updatePlatfromUserDTO.getPassword())) {
            runschUser.setPasswordHash(passwordEncoder.encode(updatePlatfromUserDTO.getPassword()));
        }
        encryptUser(runschUser);
        boolean result = runschUserService.updateById(runschUser);
        if (!result) {
            throw new BusinessException(MessageCode.UPDATE_FAILED);
        }
        result = initUpdateRoles(result, updatePlatfromUserDTO.getRoleCodes(), updatePlatfromUserDTO.getId());
        if (!result) {
            throw new BusinessException(MessageCode.CRAETE_FAILED);
        }
        return result;
    }

    /**
     * @param result
     * @param roleCodes
     * @param id
     * @return
     */
    private boolean initUpdateRoles(boolean result, Set<String> roleCodes, Long id) {
        if (result && roleCodes != null && roleCodes.size() > 0) {
            RunschUserRole runschUserRole = new RunschUserRole();
            runschUserRole.setUserId(id);
            runschUserRoleService.delete(new EntityWrapper<>(runschUserRole));
            List<RunschUserRole> userRoles = new ArrayList<>();
            initUserRoles(userRoles, roleCodes, id);
            result = runschUserRoleService.insertBatch(userRoles);
        }
        return result;
    }

    /**
     * @param userRoles
     * @param roleCodes
     * @param id
     */
    private void initUserRoles(List<RunschUserRole> userRoles, Set<String> roleCodes, Long id) {
        log.info("initUserRoles:{}", JSON.toJSONString(roleCodes));
        int i = 0;
        for (String roleCode : roleCodes) {
            RunschUserRole runschUserRole = new RunschUserRole();
            runschUserRole.setRoleCode(roleCode);
            runschUserRole.setUserId(id);
            if (i == 0) {
                runschUserRole.setIsDefault(BusinessConst.DEFAULT_ROLE);
            }
            userRoles.add(runschUserRole);
            i = i + 1;
        }
    }

    /**
     * Get current user info get current user dto.
     *
     * @return the get current user dto
     */
    public GetCurrentUserDTO getCurrentUserInfo() {
        Long userId = WebUtils.getCurrentUserId();
        if (userId == null) {
            throw new BusinessException(MessageCode.NO_LOGIN);
        }
        RunschUser runschUser = runschUserService.selectById(userId);
        if (runschUser == null) {
            throw new BusinessException(MessageCode.USER_IS_NULL);
        }
        decryptUser(runschUser);
        GetCurrentUserDTO getCurrentUserDTO = new GetCurrentUserDTO();
        getCurrentUserDTO.setId(runschUser.getId());
        getCurrentUserDTO.setUserName(runschUser.getUserName());
        getCurrentUserDTO.setName(runschUser.getName());
        getCurrentUserDTO.setSex(runschUser.getSex());
        getCurrentUserDTO.setPhone(runschUser.getPhone());
        getCurrentUserDTO.setBirthday(runschUser.getBirthday());
        getCurrentUserDTO.setEmail(runschUser.getEmail());
        getCurrentUserDTO.setAddress(runschUser.getAddress());
        getCurrentUserDTO.setAvatar(runschUser.getAvatar());
        getCurrentUserDTO.setOrganizationId(runschUser.getOrganizationId());
        return getCurrentUserDTO;
    }

    /**
     * Update current user boolean.
     *
     * @param updateCurrentUserDTO the update current user dto
     * @return the boolean
     */
    @Transactional(rollbackFor = Exception.class)
    public Boolean updateCurrentUser(UpdateCurrentUserDTO updateCurrentUserDTO) {
        Long userId = WebUtils.getCurrentUserId();
        if (userId == null) {
            throw new BusinessException(MessageCode.NO_LOGIN);
        }
        RunschUser runschUser = new RunschUser();
        runschUser.setId(userId);
        runschUser.setName(updateCurrentUserDTO.getName());
        runschUser.setEmail(updateCurrentUserDTO.getEmail());
        runschUser.setAddress(updateCurrentUserDTO.getAddress());
        runschUser.setBirthday(updateCurrentUserDTO.getBirthday());
        runschUser.setEmail(updateCurrentUserDTO.getEmail());
        runschUser.setAvatar(updateCurrentUserDTO.getAvatar());
        runschUser.setQq(updateCurrentUserDTO.getQq());
        runschUser.setWeiXin(updateCurrentUserDTO.getWeiXin());
        runschUser.setWeiBo(updateCurrentUserDTO.getWeiBo());
        runschUser.setSex(updateCurrentUserDTO.getSex());
        runschUser.setIdCard(updateCurrentUserDTO.getIdCard());
        encryptUser(runschUser);
        boolean result = runschUserService.updateById(runschUser);
        if (!result) {
            throw new BusinessException(MessageCode.UPDATE_FAILED);
        }
        return result;
    }

    /**
     * Update phone num boolean.
     *
     * @param password   the password
     * @param phoneNum   the phone num
     * @param verifyCode the verify code
     * @return the boolean
     */
    public Boolean updatePhoneNum(String password, String phoneNum, String verifyCode) {
        Long userId = WebUtils.getCurrentUserId();
        if (userId == null) {
            throw new BusinessException(MessageCode.NO_LOGIN);
        }
        Boolean passwordTrue = checkCurrentPassword(password);
        if (!passwordTrue) {
            throw new BusinessException(MessageCode.PASSWORD_INCORRECT);
        }

        if (checkPhoneExist(phoneNum, null)) {
            throw new BusinessException(MessageCode.USER_PHONE_IS_EXIST);
        }

        if (StringUtils.isEmpty(verifyCode) || StringUtils.isEmpty(phoneNum)) {
            throw new BusinessException(MessageCode.PARAM_ILLEGAL);
        }
        String redisCode = redisUtil.get(RedisConst.SHORT_MSG_CODE + phoneNum, String.class);
        Boolean verifyCodeEqual = StringUtils.equals(redisCode, verifyCode);

        if (!verifyCodeEqual) {
            throw new BusinessException(MessageCode.VERIFICATION_CODE_ERROR);
        }
        redisUtil.remove(RedisConst.SHORT_MSG_CODE + phoneNum);
        RunschUser runschUser = new RunschUser();
        runschUser.setId(userId);
        runschUser.setPhone(phoneNum);
        encryptUser(runschUser);
        return runschUserService.updateById(runschUser);
    }

    /**
     * Update current password boolean.
     *
     * @param oldPassword the old password
     * @param newPassword the new password
     * @return the boolean
     */
    public Boolean updateCurrentPassword(String oldPassword, String newPassword) {
        Long userId = WebUtils.getCurrentUserId();
        if (userId == null) {
            throw new BusinessException(MessageCode.NO_LOGIN);
        }
        Boolean passwordTrue = checkCurrentPassword(oldPassword);
        if (!passwordTrue) {
            throw new BusinessException(MessageCode.PASSWORD_INCORRECT);
        }
        RunschUser runschUser = new RunschUser();
        runschUser.setId(userId);
        String encryptedPassword = passwordEncoder.encode(newPassword);
        runschUser.setPasswordHash(encryptedPassword);
        return runschUserService.updateById(runschUser);
    }

    /**
     * 根据用户名初始化用户权限信息
     *
     * @param userName the user name
     * @return the login info dto
     */
    public LoginInfoDTO initUser(String userName) {
        if (StringUtils.isEmpty(userName)) {
            throw new BusinessException(MessageCode.NO_LOGIN);
        }
        // 根据用户名查询用户
        LoginInfoDTO resultLoginInfoDTO = new LoginInfoDTO();
        resultLoginInfoDTO.setRoles(new ArrayList<>(16));
        RunschUser runschUser = new RunschUser();
        runschUser.setUserName(userName);
        runschUser = runschUserService.selectOne(new EntityWrapper<>(runschUser));
        decryptUser(runschUser);

        // 获取当前用户的语言
        String language = WebUtils.getCurrentUserLanguage();
        Map<String, RunschPermission> permissionMap = new LinkedHashMap<>(16);
        // 根据语言获取角色字典对应信息
        List<GetDicDTO> roleCodeDics = userService.listDic(DicConsts.DIC_ROLE_CODE, null, null);
        Map<String, String> roleCodeDicMap = new HashMap<>(16);
        for (GetDicDTO getDicDTO : roleCodeDics) {
            roleCodeDicMap.put(getDicDTO.getCode(), getDicDTO.getName());
        }
        if (runschUser != null) {
            resultLoginInfoDTO.setName(runschUser.getName());
            resultLoginInfoDTO.setUserName(runschUser.getUserName());
            resultLoginInfoDTO.setAvatar(runschUser.getAvatar());
            resultLoginInfoDTO.setDomainId(runschUser.getDomainId());
            resultLoginInfoDTO.setOrganizationId(runschUser.getOrganizationId());

            // 查询用户所有角色
            RunschUserRole userRole = new RunschUserRole();
            userRole.setUserId(runschUser.getId());
            List<RunschUserRole> userRoleList = runschUserRoleService.selectList(new EntityWrapper<>(userRole));
            for (RunschUserRole ur : userRoleList) {
                resultLoginInfoDTO.getRoles().add(ur.getRoleCode());
            }
        }
        return resultLoginInfoDTO;
    }

    /**
     * 初始化权限map
     *
     * @param permissionMap
     * @param id
     * @param permissionName
     * @param parentId
     * @param menuName
     */
    private void initPermissionMap(Map<String, RunschPermission> permissionMap, Long id, String permissionName, Long parentId, String menuName) {
        RunschPermission runschPermission = new RunschPermission();
        runschPermission.setId(id);
        runschPermission.setPermissionName(permissionName);
        runschPermission.setParentId(parentId);
        runschPermission.setMenuName(menuName);
        permissionMap.put(permissionName, runschPermission);
    }

    /**
     * 递归初始化菜单集合
     *
     * @param permission         the permission
     * @param allList            the all list
     * @param handledPermissions the handled permissions
     */
    private void initMenus(PermissionDTO permission, List<PermissionDTO> allList, Set<Long> handledPermissions) {
        for (PermissionDTO p : allList) {
            if (handledPermissions.contains(p.getId())) {
                continue;
            }
            if (p.getParentId().equals(permission.getId())) {
                handledPermissions.add(p.getId());
                initMenus(p, allList, handledPermissions);
                permission.getChildren().add(p);
            }
        }
    }

    /**
     * 根据用户名称查询用户信息及用户权限（拦截权限）
     *
     * @param userName the user name
     * @return the user info with permission dto
     */
    public UserInfoWithPermissionDTO getOneAndPermissionByName(String userName) {
        if (StringUtils.isEmpty(userName)) {
            throw new BusinessException(MessageCode.USER_IS_NULL);
        }
        // 根据用户查询用户
        RunschUser runschUser = new RunschUser();
        runschUser.setUserName(userName);
        runschUser = runschUserService.selectOne(new EntityWrapper<>(runschUser));

        if (runschUser == null) {
            if (Pattern.matches(BusinessConst.PHONE_REGEXP, userName)) {
                runschUser = new RunschUser();
                runschUser.setPhone(userName);
                runschUser = runschUserService.selectOne(new EntityWrapper<>(runschUser));
                if (runschUser == null) {
                    runschUser = new RunschUser();
                    runschUser.setPhone(SecurityUtil.encryptDes(userName));
                    runschUser = runschUserService.selectOne(new EntityWrapper<>(runschUser));
                    if (runschUser == null) {
                        throw new BusinessException(MessageCode.USER_IS_NULL);
                    }
                }
            } else {
                throw new BusinessException(MessageCode.USER_IS_NULL);
            }
        }

        decryptUser(runschUser);

        if (StringUtils.equals(runschUser.getState(), BusinessConst.USER_STATE_STOP)) {
            throw new BusinessException(MessageCode.USER_IS_DEACTIVATE);
        }

        UserInfoWithPermissionDTO userInfoWithPermissionDTO = new UserInfoWithPermissionDTO();
        userInfoWithPermissionDTO.setRoles(new HashSet<>(16));
        userInfoWithPermissionDTO.setPermissions(new HashSet<>(16));
        // 获取当前用户的语言
        HeaderUserInfoDTO userInfoDTO = redisUtil.get(RedisConst.REDIS_USER_INFO + runschUser.getUserName(), HeaderUserInfoDTO.class);
        userInfoWithPermissionDTO.setUserName(runschUser.getUserName());
        userInfoWithPermissionDTO.setPassword(runschUser.getPasswordHash());
        userInfoWithPermissionDTO.setOrganizationId(runschUser.getOrganizationId());
        Set<String> permissions = new HashSet<>();
        Set<String> roles = new HashSet<>();
        String language;
        if (userInfoDTO == null) {
            userInfoDTO = new HeaderUserInfoDTO();
        }
        userInfoDTO.setUserName(runschUser.getUserName());
        if (StringUtils.isNotEmpty(userInfoDTO.getLanguage())) {
            language = userInfoDTO.getLanguage();
        } else {
            language = BusinessConst.DEFAULT_LANGUAGE;
        }
        userInfoDTO.setLanguage(language);
        userInfoDTO.setUserId(runschUser.getId());
        userInfoDTO.setOrganizationId(runschUser.getOrganizationId());
        if (userInfoDTO.getPermissions() != null && userInfoDTO.getRoles() != null) {
            permissions = userInfoDTO.getPermissions();
            roles = userInfoDTO.getRoles();
        } else {
            // 遍历角色获取权限
            RunschUserRole userRole = new RunschUserRole();
            userRole.setUserId(runschUser.getId());
            List<RunschUserRole> userRoleList = runschUserRoleService.selectList(new EntityWrapper<>(userRole));
            for (RunschUserRole ur : userRoleList) {
                if (ur.getIsDefault() == 1) {
                    userInfoDTO.setDefaultRole(ur.getRoleCode());
                }
                roles.add(ur.getRoleCode());
                // 获取公开权限
                List<RolePermissionsDTO> rolePermissions = permissionManager.listRolePermission(ur.getRoleCode(), language);
                if (rolePermissions != null) {
                    for (RolePermissionsDTO rolePermissionsDTO : rolePermissions) {
                        permissions.add(rolePermissionsDTO.getPermissionName());
                    }
                }
            }
        }

        if (StringUtils.isEmpty(userInfoDTO.getDefaultRole())
                && userInfoDTO.getRoles() != null
                && userInfoDTO.getRoles().size() > 0) {
            userInfoDTO.setDefaultRole(new ArrayList<>(userInfoDTO.getRoles()).get(0));
        }
        userInfoWithPermissionDTO.setPermissions(permissions);
        userInfoDTO.setPermissions(permissions);
        userInfoDTO.setRoles(roles);
        userInfoDTO.setDomainId(runschUser.getDomainId());
        userInfoWithPermissionDTO.setRoles(roles);
        redisUtil.set(RedisConst.REDIS_USER_INFO + runschUser.getUserName(), userInfoDTO);
        LogRecordUtil.record(BaseConsts.RECORD_LOG_TYPE_LOGIN, runschUser.getId(), runschUser.getUserName());
        return userInfoWithPermissionDTO;
    }

    /**
     * 初始化所有权限缓存
     */
    public void initAllPermission() {

        List<RunschRolePermission> allRolePermissions = runschRolePermissionService.selectList(new EntityWrapper<RunschRolePermission>(new RunschRolePermission()));
        List<RunschPermission> allPermissions = runschPermissionService.selectList(new EntityWrapper<RunschPermission>(new RunschPermission()));

        Map<String, RunschPermission> permissionMap = new HashMap<>(16);
        Set<String> languageSet = new HashSet<>();

        for (RunschPermission permission : allPermissions) {
            permissionMap.put(permission.getPermissionName() + RedisConst.REDIS_SPLIT + permission.getLanguage(), permission);
            languageSet.add(permission.getLanguage());
        }

        // 缓存所有公开权限
        // 清除缓存
        redisUtil.removePattern(RedisConst.REDIS_ROLE_PERMISSIONS + RedisConst.REDIS_ALL_MATCH);
        Map<String, List<RolePermissionsDTO>> rolePermissonMap = new HashMap<>(16);
        for (RunschRolePermission runschRolePermission : allRolePermissions) {
            for (String language : languageSet) {
                String key = runschRolePermission.getRoleCode() + RedisConst.REDIS_SPLIT + language;
                if (rolePermissonMap.get(key) == null) {
                    rolePermissonMap.put(key, new ArrayList<>());
                }
                RunschPermission permission = permissionMap.get(runschRolePermission.getPermissionName() + RedisConst.REDIS_SPLIT + language);
                if (permission != null) {
                    RolePermissionsDTO rolePermissionsDTO = new RolePermissionsDTO();
                    rolePermissionsDTO.setPermissionName(permission.getPermissionName());
                    rolePermissionsDTO.setMenuName(permission.getMenuName());
                    rolePermissionsDTO.setParentId(permission.getParentId());
                    rolePermissonMap.get(key).add(rolePermissionsDTO);
                }
            }
        }
        for (Map.Entry<String, RunschPermission> entry : permissionMap.entrySet()) {
            String rpKey = RedisConst.REDIS_ROLE_PERMISSIONS + entry.getKey();
            redisUtil.set(rpKey, entry.getValue());
        }

        // 缓存所有组织私有权限
        // 清除缓存
        redisUtil.removePattern(RedisConst.REDIS_ORG_ROLE_PERMISSIONS + RedisConst.REDIS_ALL_MATCH);
        Map<String, List<OrgRolePermissionsDTO>> roleOrgPermissonMap = new HashMap<>(16);
        for (Map.Entry<String, List<OrgRolePermissionsDTO>> entry : roleOrgPermissonMap.entrySet()) {
            String orgRoleKey = RedisConst.REDIS_ORG_ROLE_PERMISSIONS + entry.getKey();
            redisUtil.set(orgRoleKey, entry.getValue());
        }
    }

    /**
     * List user names by ids user names dto.
     *
     * @param userIdsDTO the user ids dto
     * @return the user names dto
     */
    public UserNamesDTO listUserNamesByIds(UserIdsDTO userIdsDTO) {
        UserNamesDTO resultUserNamesDTO = new UserNamesDTO();
        List<String> userNames = new ArrayList();
        if (userIdsDTO == null || userIdsDTO.getUserIds() == null || userIdsDTO.getUserIds().size() == 0) {
            throw new BusinessException(MessageCode.PARAM_ILLEGAL);
        }
        List<Long> ids = userIdsDTO.getUserIds();
        List<RunschUser> list = runschUserService.selectBatchIds(ids);
        if (list != null) {
            for (RunschUser runschUser : list) {
                userNames.add(runschUser.getUserName());
            }
        }
        resultUserNamesDTO.setUserNames(userNames);
        return resultUserNamesDTO;
    }

    /**
     * List users by user names list.
     *
     * @param userNames the user names
     * @return the list
     */
    public List<GetUserDTO> listUsersByUserNames(List<String> userNames) {
        List<GetUserDTO> getUserDTOList = new ArrayList<>();
        Condition condition = new Condition();
        condition.in(USER_NAME, userNames);
        List<RunschUser> userList = runschUserService.selectList(condition);
        decryptUsers(userList);
        for (RunschUser runschUser : userList) {
            GetUserDTO getUserDTO = new GetUserDTO();
            getUserDTO.setId(runschUser.getId());
            getUserDTO.setUserName(runschUser.getUserName());
            getUserDTO.setName(runschUser.getName());
            getUserDTO.setOrganizationId(runschUser.getOrganizationId());
            getUserDTO.setEmail(runschUser.getEmail());
            getUserDTO.setAddress(runschUser.getAddress());
            getUserDTO.setAvatar(runschUser.getAvatar());
            getUserDTO.setPhone(runschUser.getPhone());
            getUserDTO.setBirthday(runschUser.getBirthday());
            getUserDTO.setSex(runschUser.getSex());
            getUserDTO.setIdCard(runschUser.getIdCard());
            getUserDTOList.add(getUserDTO);
        }
        return getUserDTOList;
    }

    /**
     * 保存语言
     *
     * @param language the language
     * @return the boolean
     */
    public boolean saveUserLanguage(String language) {
        boolean result;
        HeaderUserInfoDTO currentUser = WebUtils.getCurrentUser();
        if (currentUser == null) {
            currentUser = new HeaderUserInfoDTO();
        }
        currentUser.setLanguage(language);
        result = redisUtil.set(RedisConst.REDIS_USER_INFO + currentUser.getUserName(), currentUser);
        if (!result) {
            throw new BusinessException(MessageCode.UPDATE_FAILED);
        }
        return result;
    }

    /**
     * Gets password by user name.
     *
     * @param userId the user id
     * @return the password by user name
     */
    public String getPasswordByUserId(Long userId) {
        if (userId == null) {
            throw new BusinessException(MessageCode.USER_IS_NULL);
        }
        String password;
        RunschUser runschUser = runschUserService.selectById(userId);
        if (runschUser == null) {
            throw new BusinessException(MessageCode.USER_IS_NULL);
        }
        return runschUser.getPasswordHash();
    }

    /**
     * Update password boolean.
     *
     * @param password the password
     * @return the boolean
     */
    public boolean checkCurrentPassword(String password) {
        Long userId = WebUtils.getCurrentUserId();
        String userPassword = getPasswordByUserId(userId);
        return BCrypt.checkpw(password, userPassword);
    }

    /**
     * Update password by user name boolean.
     *
     * @param userId   the user id
     * @param password the password
     * @return the boolean
     */
    @Transactional(rollbackFor = Exception.class)
    public Boolean resetPasswordByUserId(Long userId, String password) {
        RunschUser runschUser = runschUserService.selectById(userId);
        if (runschUser == null) {
            throw new BusinessException(MessageCode.USER_IS_NULL);
        }
        return updatePasswordById(userId, password);
    }

    /**
     * Update password by id boolean.
     *
     * @param userId   the user id
     * @param password the password
     * @return the boolean
     */
    private Boolean updatePasswordById(Long userId, String password) {
        String encryptedPassword = passwordEncoder.encode(password);
        RunschUser newRunschUser = new RunschUser();
        newRunschUser.setId(userId);
        newRunschUser.setPasswordHash(encryptedPassword);
        return runschUserService.updateById(newRunschUser);
    }

    /**
     * Update pwd by phone verify boolean.
     *
     * @param phoneNum the phone num
     * @param token    the token
     * @param password the password
     * @return boolean boolean
     */
    public Boolean updatePwdByPhoneVerify(String phoneNum, String token, String password) {
        Boolean result = false;
        Object passwordTokenObj = redisUtil.get(RedisConst.PASSWORD_TOKEN + phoneNum);
        if (passwordTokenObj == null) {
            throw new BusinessException(MessageCode.FAILED);
        }
        if (!StringUtils.equals(token, passwordTokenObj.toString())) {
            throw new BusinessException(MessageCode.FAILED);
        }
        redisUtil.remove(RedisConst.PASSWORD_TOKEN + phoneNum);
        if (StringUtils.isEmpty(phoneNum) || StringUtils.isEmpty(password)) {
            throw new BusinessException(MessageCode.PARAM_ILLEGAL);
        }
        RunschUser runschUser = new RunschUser();
        runschUser.setPhone(phoneNum);
        runschUser = runschUserService.selectOne(new EntityWrapper<>(runschUser));
        if (runschUser == null) {
            runschUser = new RunschUser();
            runschUser.setPhone(SecurityUtil.encryptDes(phoneNum));
            EntityWrapper wrapper2 = new EntityWrapper(runschUser);
            runschUser = runschUserService.selectOne(wrapper2);
        }

        if (runschUser != null) {
            RunschUser runshUser = new RunschUser();
            runshUser.setId(runschUser.getId());
            String encryptedPassword = passwordEncoder.encode(password);
            runshUser.setPasswordHash(encryptedPassword);
            result = runschUserService.updateById(runshUser);
        }
        return result;
    }

    /**
     * Check phone verify code boolean.
     *
     * @param phoneNum the phone num
     * @param code     the code
     * @return the boolean
     */
    public String checkPhoneVerifyCode(String phoneNum, String code) {
        if (StringUtils.isEmpty(code) || StringUtils.isEmpty(phoneNum)) {
            throw new BusinessException(MessageCode.PARAM_ILLEGAL);
        }
        String redisCode = redisUtil.get(RedisConst.SHORT_MSG_CODE + phoneNum, String.class);
        Boolean equal = StringUtils.equals(redisCode, code);
        String uuid;
        if (equal) {
            redisUtil.remove(RedisConst.SHORT_MSG_CODE + phoneNum);
            uuid = UUID.randomUUID().toString();
            Boolean redisSet = redisUtil.set(RedisConst.PASSWORD_TOKEN + phoneNum, uuid, BusinessConst.VERIFY_TIME_OUT);
            if (!redisSet) {
                throw new BusinessException(MessageCode.VERIFICATION_CODE_EXPIRE);
            }
        } else {
            throw new BusinessException(MessageCode.VERIFICATION_CODE_ERROR);
        }
        return uuid;
    }

    /**
     * Check email verify code boolean.
     *
     * @param email the email
     * @param code  the code
     * @return the boolean
     */
    public Boolean checkEmailVerifyCode(String email, String code) {
        if (StringUtils.isEmpty(code) || StringUtils.isEmpty(email)) {
            throw new BusinessException(MessageCode.PARAM_ILLEGAL);
        }
        String redisCode = redisUtil.get(RedisConst.EMAIL_CODE + email, String.class);
        return StringUtils.equals(redisCode, code);
    }


    /**
     * Get oauth permissions list.
     *
     * @return the list
     */
    public List<OauthPermissionDTO> getOauthPermissions() {
        List<OauthPermissionDTO> resultList = new ArrayList<>();
        List<RunschPermission> list = runschPermissionService.selectList(new Condition());
        for (RunschPermission runschPermission : list) {
            OauthPermissionDTO oauthPermissionDTO = new OauthPermissionDTO();
            oauthPermissionDTO.setPermissionName(runschPermission.getPermissionName());
            oauthPermissionDTO.setUrl(runschPermission.getUrl());
            resultList.add(oauthPermissionDTO);
        }
        return resultList;
    }

    /**
     * Page user page.
     *
     * @param pageUserDTO the page user dto
     * @return the page
     */
    public Page<GetPageUserDTO> pageUser(PageUserDTO pageUserDTO) {
        Page<GetPageUserDTO> resultPage = new Page<>();
        com.baomidou.mybatisplus.plugins.Page<GetPageUserDTO> page = new com.baomidou.mybatisplus.plugins.Page();
        page.setCurrent(pageUserDTO.getPageNo());
        page.setSize(pageUserDTO.getPageSize());
        encryptPageUserDTO(pageUserDTO);
        page = runschUserService.pageUser(page, pageUserDTO);
        resultPage.setCurrent(page.getCurrent());
        resultPage.setTotal(page.getTotal());
        resultPage.setPages(page.getPages());
        resultPage.setSize(page.getSize());

        List<GetDicDTO> dicDTOList = userService.listDic(DicConsts.DIC_ROLE_CODE, null, null);
        Map<String, String> roleCodeMap = new HashMap<>(16);
        for (GetDicDTO getDicDTO : dicDTOList) {
            roleCodeMap.put(getDicDTO.getCode(), getDicDTO.getName());
        }
        for (GetPageUserDTO user : page.getRecords()) {
            decryptPageUserDTO(user);
            List<String> roles = new ArrayList<>();
            String[] roleCodes = user.getRoleCodes().split(BusinessConst.ROLE_CODE_PREFIX);
            if (roleCodes != null) {
                for (String roleCode : roleCodes) {
                    String roleName = roleCodeMap.get(roleCode);
                    if (StringUtils.isNotEmpty(roleName)) {
                        roles.add(roleName);
                    }
                }
            }
            user.setRoles(roles);
        }
        resultPage.setRecords(page.getRecords());
        return resultPage;
    }

    /**
     * List user by role code list.
     *
     * @param roleCode the role code
     * @return the list
     */
    public List<GetUserDTO> listUserByRoleCode(String roleCode) {
        List<GetUserDTO> resultList = new ArrayList<>();
        if (StringUtils.isEmpty(roleCode)) {
            throw new BusinessException(MessageCode.PARAM_ILLEGAL);
        }
        RunschUserRole runschUserRole = new RunschUserRole();
        runschUserRole.setRoleCode(roleCode);
        List<RunschUserRole> userRoleList = runschUserRoleService.selectList(new EntityWrapper<>(runschUserRole));
        if (userRoleList != null) {
            List<Long> userIds = userRoleList.stream().map(RunschUserRole::getUserId).collect(Collectors.toList());
            List<RunschUser> userList = runschUserService.selectBatchIds(userIds);
            decryptUsers(userList);
            for (RunschUser runschUser : userList) {
                GetUserDTO getUserDTO = new GetUserDTO();
                getUserDTO.setId(runschUser.getId());
                getUserDTO.setName(runschUser.getName());
                getUserDTO.setUserName(runschUser.getUserName());
                resultList.add(getUserDTO);
            }
        }
        return resultList;
    }

    /**
     * List user by role and domain list.
     *
     * @param roleCodes the role codes
     * @param domainId  the domain id
     * @return the list
     */
    public List<GetUserDTO> listUserByRoleAndDomain(Set<String> roleCodes, Long domainId) {
        return runschUserService.listUserByRoleAndDomain(roleCodes, domainId);
    }

    /**
     * Update user state boolean.
     *
     * @param userId the user id
     * @param state  the state
     * @return the boolean
     */
    public Boolean updateUserState(Long userId, String state) {
        RunschUser runschUser = new RunschUser();
        runschUser.setState(state);
        runschUser.setId(userId);
        return runschUserService.updateById(runschUser);
    }

    /**
     * Check phone num exist list.
     *
     * @param phoneNums the phone nums
     * @return the list
     */
    public List<Integer> checkPhoneNumExist(List<String> phoneNums) {
        if (phoneNums == null || phoneNums.size() == 0) {
            throw new BusinessException(MessageCode.PARAM_ILLEGAL);
        }
        List<String> encryptPhoneNums = new ArrayList<>();
        for (String phoneNum : phoneNums) {
            encryptPhoneNums.add(SecurityUtil.encryptDes(phoneNum));
        }
        phoneNums.addAll(encryptPhoneNums);
        List<Integer> resultList = new ArrayList<>();
        Map<String, Integer> phoneNumMap = new HashMap<>(16);
        for (int i = 1; i < phoneNums.size() + 1; i++) {
            phoneNumMap.put(phoneNums.get(i - 1), i);
        }
        Condition condition = new Condition();
        condition.in(PHONE, phoneNums);
        List<RunschUser> list = runschUserService.selectList(condition);
        if (list != null) {
            for (RunschUser runschUser : list) {
                resultList.add(phoneNumMap.get(runschUser.getPhone()));
            }
        }
        return resultList;
    }

    /**
     * Check user name exist list.
     *
     * @param userNames the user names
     * @return the list
     */
    public List<Integer> checkUserNameExist(List<String> userNames) {
        if (userNames == null || userNames.size() == 0) {
            throw new BusinessException(MessageCode.PARAM_ILLEGAL);
        }
        List<Integer> resultList = new ArrayList<>();
        Map<String, Integer> userNameMap = new HashMap<>(16);
        for (int i = 1; i < userNames.size() + 1; i++) {
            userNameMap.put(userNames.get(i - 1).toLowerCase(), i);
        }
        Condition condition = new Condition();
        condition.in(USER_NAME, userNames);
        List<RunschUser> list = runschUserService.selectList(condition);
        if (list != null) {
            for (RunschUser runschUser : list) {
                resultList.add(userNameMap.get(runschUser.getUserName().toLowerCase()));
            }
        }
        return resultList;
    }

    /**
     * List user by user names list.
     *
     * @param userNameList the user name list
     * @return the list
     */
    public List<GetUserDTO> listUserByUserNames(List<String> userNameList) {
        List<GetUserDTO> getUserDTOList = new ArrayList<>();
        Condition condition = new Condition();
        condition.in(USER_NAME, userNameList);
        List<RunschUser> runschUsers = runschUserService.selectList(condition);
        decryptUsers(runschUsers);
        if (ListUtil.isNotEmpty(runschUsers)) {
            for (RunschUser runschUser : runschUsers) {
                getUserDTOList.add(initGetUserDTO(runschUser));
            }
        }
        return getUserDTOList;
    }

    /**
     * Switch default role boolean.
     *
     * @param roleCode the role code
     * @return the boolean
     */
    public Boolean switchDefaultRole(String roleCode) {
        RunschUserRole runschUserRole = new RunschUserRole();
        runschUserRole.setUserId(WebUtils.getCurrentUserId());
        List<RunschUserRole> list = runschUserRoleService.selectList(new EntityWrapper<>(runschUserRole));

        String defaultCode = null;
        for (RunschUserRole userRole : list) {
            if (StringUtils.equals(userRole.getRoleCode(), roleCode)) {
                userRole.setIsDefault(1);
                defaultCode = roleCode;
            } else {
                userRole.setIsDefault(0);
            }
            userRole.setGmtCreate(null);
            userRole.setGmtModified(null);
        }
        Boolean result;
        result = runschUserRoleService.updateBatchById(list);

        if (result && StringUtils.isNotEmpty(defaultCode)) {
            HeaderUserInfoDTO headerUserInfoDTO = WebUtils.getCurrentUser();
            headerUserInfoDTO.setDefaultRole(defaultCode);
            redisUtil.set(RedisConst.REDIS_USER_INFO + headerUserInfoDTO.getUserName(), headerUserInfoDTO);
        }
        return result;
    }
}
