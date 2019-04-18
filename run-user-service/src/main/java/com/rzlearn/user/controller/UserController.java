package com.rzlearn.user.controller;

import com.rzlearn.base.basic.BaseController;
import com.rzlearn.base.basic.Page;
import com.rzlearn.base.dto.ResultMsg;
import com.rzlearn.base.exception.BusinessException;
import com.rzlearn.base.support.MessageCode;
import com.rzlearn.base.util.WebUtils;
import com.rzlearn.user.constant.BusinessConst;
import com.rzlearn.user.dto.*;
import com.rzlearn.user.feign.IUserController;
import com.rzlearn.user.manager.UserManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * <p>ClassName:UserController</p>
 * <p>Description:用户管理</p>
 *
 * @author JiPeigong
 * @date 2018-06-07 18:01
 **/
@RestController
@Slf4j
public class UserController extends BaseController implements IUserController {

    @Autowired
    private UserManager userManager;

    @Override
    public ResultMsg<GetUserDTO> getUserById(@RequestParam Long id) throws BusinessException {
        return new ResultMsg(MessageCode.SUCCESS, userManager.getUserById(id));
    }

    @Override
    public ResultMsg<GetPlatformUserDTO> getPlatformUserById(@RequestParam Long id) throws BusinessException {
        return new ResultMsg(MessageCode.SUCCESS, userManager.getPlatformUserById(id));
    }

    @Override
    public ResultMsg<GetCurrentUserDTO> getCurrentUserInfo() throws BusinessException {
        return new ResultMsg(MessageCode.SUCCESS, userManager.getCurrentUserInfo());
    }

    @Override
    public ResultMsg<List<GetUserDTO>> getUsersByIds(@RequestBody List<Long> ids) throws BusinessException {
        return new ResultMsg(MessageCode.SUCCESS, userManager.getUsersByIds(ids));
    }

    @Override
    public ResultMsg<GetUserDTO> getUserByUserName(@RequestParam(value = "userName") String userName) throws BusinessException {
        return new ResultMsg(MessageCode.SUCCESS, userManager.getUserByUserName(userName));
    }

    @Override
    public ResultMsg<List<GetUserDTO>> listUserByUserNames(@RequestParam(value = "userNameList") List<String> userNameList) throws BusinessException {
        return new ResultMsg(MessageCode.SUCCESS, userManager.listUserByUserNames(userNameList));
    }

    @Override
    public ResultMsg<SaveUserDTO> saveUser(@RequestBody SaveUserDTO saveUserDTO) throws BusinessException {
        return new ResultMsg(MessageCode.SUCCESS, userManager.saveUser(saveUserDTO));
    }

    @Override
    public ResultMsg<Boolean> removeUser(@RequestParam(value = "userId") Long userId) throws BusinessException {
        return new ResultMsg(MessageCode.SUCCESS, userManager.removeUser(userId));
    }

    @Override
    public ResultMsg<Boolean> removeBatchUser(@RequestParam(value = "userIds") List<Long> userIds) throws BusinessException {
        return new ResultMsg(MessageCode.SUCCESS, userManager.removeBatchUser(userIds));
    }

//    @Override
//    public ResultMsg<Boolean> savePlatformUser(@RequestBody SaveUserDTO saveUserDTO) throws BusinessException {
//        return new ResultMsg(MessageCode.SUCCESS, userManager.savePlatformUser(saveUserDTO));
//    }

    @Override
    public ResultMsg<Boolean> saveBatchUser(@Valid @RequestBody List<SaveUserDTO> saveUserDTOList) throws BusinessException {
        return new ResultMsg(MessageCode.SUCCESS, userManager.saveBatchUser(saveUserDTOList));
    }

    @Override
    public ResultMsg<Boolean> deleteBatchUser(@RequestParam(value = "ids") List<Long> ids) throws BusinessException {
        return new ResultMsg(MessageCode.SUCCESS, userManager.deleteBatchUser(ids));
    }

    @Override
    public ResultMsg<Boolean> updateUser(@RequestBody UpdateUserDTO updateUserDTO) throws BusinessException {
        return new ResultMsg(MessageCode.SUCCESS, userManager.updateUser(updateUserDTO));
    }

    @Override
    public ResultMsg<Boolean> updatePlatformUser(@RequestBody UpdatePlatfromUserDTO updatePlatfromUserDTO) throws BusinessException {
        return new ResultMsg(MessageCode.SUCCESS, userManager.updatePlatformUser(updatePlatfromUserDTO));
    }

    @Override
    public ResultMsg<Boolean> updateCurrentUser(@Valid @RequestBody UpdateCurrentUserDTO updateCurrentUserDTO) throws BusinessException {
        return new ResultMsg(MessageCode.SUCCESS, userManager.updateCurrentUser(updateCurrentUserDTO));
    }

    @Override
    public ResultMsg<Boolean> updatePhoneNum(String password, String phoneNum, String verifyCode) throws BusinessException {
        return new ResultMsg(MessageCode.SUCCESS, userManager.updatePhoneNum(password, phoneNum, verifyCode));
    }

    @Override
    public ResultMsg<Boolean> updateCurrentPassword(@RequestParam(value = "oldPassword") String oldPassword, @RequestParam(value = "newPassword") String newPassword) throws BusinessException {
        return new ResultMsg(MessageCode.SUCCESS, userManager.updateCurrentPassword(oldPassword, newPassword));
    }

    @Override
    public ResultMsg<Boolean> resetPasswordByUserId(@RequestParam(value = "userId") Long userId, @RequestParam(value = "password") String password) throws BusinessException {
        return new ResultMsg(MessageCode.SUCCESS, userManager.resetPasswordByUserId(userId, password));
    }

    @Override
    public ResultMsg<UserInfoWithPermissionDTO> getOneAndPermissionByName(@RequestParam String userName) throws BusinessException {
        return new ResultMsg(MessageCode.SUCCESS, userManager.getOneAndPermissionByName(userName));
    }

    @Override
    public ResultMsg<LoginInfoDTO> initUser() throws BusinessException {
        LoginInfoDTO loginInfoDTO = userManager.initUser(WebUtils.getCurrentUserName());
        return new ResultMsg(MessageCode.SUCCESS, loginInfoDTO);
    }

    @Override
    public ResultMsg<Boolean> cacheLanguage(@RequestParam String language) throws BusinessException {
        return new ResultMsg(MessageCode.SUCCESS, userManager.saveUserLanguage(language));
    }

    @Override
    public ResultMsg<UserNamesDTO> listUserNamesByIds(@Valid @RequestBody UserIdsDTO userIdsDTO) throws BusinessException {
        return new ResultMsg(MessageCode.SUCCESS, userManager.listUserNamesByIds(userIdsDTO));
    }

    @Override
    public ResultMsg<List<GetUserDTO>> listUsersByUserNames(@RequestParam(value = "userNames") List<String> userNames) throws BusinessException {
        return new ResultMsg(MessageCode.SUCCESS, userManager.listUsersByUserNames(userNames));
    }

    @Override
    public ResultMsg<String> checkPhoneVerifyCode(@RequestParam(value = "phoneNum") String phoneNum,
                                                  @RequestParam(value = "verifyCode") String verifyCode) throws BusinessException {
        return new ResultMsg(MessageCode.SUCCESS, userManager.checkPhoneVerifyCode(phoneNum, verifyCode));
    }

    @Override
    public ResultMsg checkCurrentPassword(String password) throws BusinessException {
        return new ResultMsg(MessageCode.SUCCESS, userManager.checkCurrentPassword(password));
    }

    @Override
    public ResultMsg<Boolean> updatePwdByPhoneVerify(@Valid @RequestBody TokenUpdatePasswordDTO tokenUpdatePasswordDTO) throws BusinessException {
        return new ResultMsg(MessageCode.SUCCESS, userManager.updatePwdByPhoneVerify(tokenUpdatePasswordDTO.getPhoneNum(), tokenUpdatePasswordDTO.getToken(), tokenUpdatePasswordDTO.getPassword()));
    }

    @Override
    public ResultMsg<List<OauthPermissionDTO>> getOauthPermissions() throws BusinessException {
        return new ResultMsg(MessageCode.SUCCESS, userManager.getOauthPermissions());
    }

    @Override
    public ResultMsg<Page<GetPageUserDTO>> pageUser(@Valid @RequestBody PageUserDTO pageUserDTO) throws BusinessException {
        return new ResultMsg(MessageCode.SUCCESS, userManager.pageUser(pageUserDTO));
    }

    @Override
    public ResultMsg<List<GetUserDTO>> listUserByRoleCode(@RequestParam(value = "roleCode") String roleCode) throws BusinessException {
        return new ResultMsg(MessageCode.SUCCESS, userManager.listUserByRoleCode(roleCode));
    }

    @Override
    public ResultMsg<List<GetUserDTO>> listUserByRoleAndDomain(@RequestParam(value = "roleCodes") Set<String> roleCodes,
                                                               @RequestParam(value = "domainId") Long domainId) throws BusinessException {
        return new ResultMsg(MessageCode.SUCCESS, userManager.listUserByRoleAndDomain(roleCodes, domainId));
    }

    @Override
    public ResultMsg<Boolean> updateUserState(@RequestParam(value = "userId") Long userId,
                                              @RequestParam(value = "state") String state) throws BusinessException {
        return new ResultMsg(MessageCode.SUCCESS, userManager.updateUserState(userId, state));
    }

    @Override
    public ResultMsg<List<Integer>> checkPhoneNumExist(@RequestParam(value = "phoneNums") List<String> phoneNums) throws BusinessException {
        return new ResultMsg(MessageCode.SUCCESS, userManager.checkPhoneNumExist(phoneNums));
    }

    @Override
    public ResultMsg<List<Integer>> checkUserNameExist(@RequestParam(value = "userNames") List<String> userNames) throws BusinessException {
        return new ResultMsg(MessageCode.SUCCESS, userManager.checkUserNameExist(userNames));
    }

    @Override
    public ResultMsg<Boolean> switchDefaultRole(@RequestParam(value = "roleCode") String roleCode) throws BusinessException {
        return new ResultMsg(MessageCode.SUCCESS, userManager.switchDefaultRole(roleCode));
    }

}
