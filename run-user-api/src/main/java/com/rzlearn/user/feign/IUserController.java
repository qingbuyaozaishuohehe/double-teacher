package com.rzlearn.user.feign;

import com.rzlearn.base.basic.Page;
import com.rzlearn.base.dto.ResultMsg;
import com.rzlearn.base.exception.BusinessException;
import com.rzlearn.user.dto.*;
import io.swagger.annotations.*;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Set;

/**
 * <p>ClassName:IUserController</p>
 * <p>Description:用户权限管理接口.</p>
 *
 * @author JiPeigong
 * @date 2018 -06-08 08:58:02
 */
@Api(tags = {"用户管理"})
@FeignClient("run-user")
public interface IUserController {

    /**
     * Gets user by id.
     *
     * @param id the id
     * @return the user by id
     * @throws BusinessException the business exception
     */
    @ApiOperation(value = "查询用户", notes = "根据主键id查询用户信息", response = GetUserDTO.class)
    @ApiImplicitParam(name = "id", value = "用户id", required = true, dataType = "Long")
    @RequestMapping(value = "/user/getUserById", method = RequestMethod.GET)
    ResultMsg<GetUserDTO> getUserById(@RequestParam(value = "id") Long id) throws BusinessException;

    /**
     * Gets platform user by id.
     *
     * @param id the id
     * @return the platform user by id
     * @throws BusinessException the business exception
     */
    @ApiOperation(value = "查询平台管理员用户", notes = "根据主键id查询平台管理员用户信息", response = GetUserDTO.class)
    @ApiImplicitParam(name = "id", value = "用户id", required = true, dataType = "Long")
    @RequestMapping(value = "/user/getPlatformUserById", method = RequestMethod.GET)
    ResultMsg<GetPlatformUserDTO> getPlatformUserById(@RequestParam(value = "id") Long id) throws BusinessException;

    /**
     * Gets current user info.
     *
     * @return the current user info
     * @throws BusinessException the business exception
     */
    @ApiOperation(value = "获取当前用户详情", notes = "获取当前用户详情", response = GetUserDTO.class)
    @RequestMapping(value = "/user/getCurrentUserInfo", method = RequestMethod.GET)
    ResultMsg<GetCurrentUserDTO> getCurrentUserInfo() throws BusinessException;

    /**
     * Gets users by ids.
     *
     * @param ids the ids
     * @return the users by ids
     * @throws BusinessException the business exception
     */
    @ApiOperation(value = "查询用户集合", notes = "根据主键id集合查询用户信息集合", response = GetUserDTO.class)
    @RequestMapping(value = "/user/sens/getUsersByIds", method = RequestMethod.POST, consumes = {"application/json"})
    ResultMsg<List<GetUserDTO>> getUsersByIds(@RequestBody @ApiParam(name = "id集合", value = "id集合", required = true) List<Long> ids) throws BusinessException;

    /**
     * Gets user by userName.
     *
     * @param userName the user name
     * @return the user by userName
     * @throws BusinessException the business exception
     */
    @ApiOperation(value = "查询用户", notes = "根据用户名查询用户信息", response = GetUserDTO.class)
    @ApiImplicitParam(name = "userName", value = "用户名称", required = true, dataType = "String")
    @RequestMapping(value = "/user/sens/getUserByUserName", method = RequestMethod.GET)
    ResultMsg<GetUserDTO> getUserByUserName(@RequestParam(value = "userName") String userName) throws BusinessException;

    /**
     * 根据用户名集合查询用户集合.
     *
     * @param userNameList 用户名称集合
     * @return 用户集合 result msg
     * @throws BusinessException the business exception
     */
    @ApiOperation(value = "根据用户名集合查询用户集合", notes = "根据用户名集合查询用户集合", response = GetUserDTO.class)
    @ApiImplicitParam(name = "userNameList", value = "用户名称集合", required = true, dataType = "String", allowMultiple = true)
    @RequestMapping(value = "/user/sens/listUserByUserNames", method = RequestMethod.GET)
    ResultMsg<List<GetUserDTO>> listUserByUserNames(@RequestParam(value = "userNameList") List<String> userNameList) throws
            BusinessException;

    /**
     * 保存用户信息
     *
     * @param saveUserDTO the user dto
     * @return the user dto
     * @throws BusinessException the business exception
     */
    @ApiOperation(value = "保存用户", notes = "保存用户", response = SaveUserDTO.class)
    @RequestMapping(value = "/user/sens/saveUser", method = RequestMethod.POST, consumes = {"application/json"})
    ResultMsg<SaveUserDTO> saveUser(@Valid @RequestBody @ApiParam(name = "保存用户对象实体", value = "保存用户对象实体", required = true) SaveUserDTO saveUserDTO) throws BusinessException;

    /**
     * Remove user result msg.
     *
     * @param userId the user id
     * @return the result msg
     * @throws BusinessException the business exception
     */
    @ApiOperation(value = "删除用户", notes = "删除用户", response = Boolean.class)
    @ApiImplicitParam(name = "userId", value = "用户id主键", required = true, dataType = "long")
    @RequestMapping(value = "/user/sens/removeUser", method = RequestMethod.DELETE)
    ResultMsg<Boolean> removeUser(@RequestParam(value = "userId") Long userId) throws BusinessException;

    /**
     * Remove batch user result msg.
     *
     * @param userIds the user ids
     * @return the result msg
     * @throws BusinessException the business exception
     */
    @ApiOperation(value = "批量删除用户", notes = "批量删除用户", response = Boolean.class)
    @ApiImplicitParam(name = "userIds", value = "用户id集合", required = true, dataType = "long", allowMultiple = true)
    @RequestMapping(value = "/user/sens/removeBatchUser", method = RequestMethod.DELETE)
    ResultMsg<Boolean> removeBatchUser(@RequestParam(value = "userIds") List<Long> userIds) throws BusinessException;

    /**
     * Save batch user result msg.
     *
     * @param saveUserDTOList the save user dto list
     * @return the result msg
     * @throws BusinessException the business exception
     */
    @ApiOperation(value = "批量保存用户", notes = "批量保存用户", response = Boolean.class)
    @RequestMapping(value = "/user/sens/saveBatchUser", method = RequestMethod.POST, consumes = {"application/json"})
    ResultMsg<Boolean> saveBatchUser(@Valid @RequestBody @ApiParam(name = "批量保存用户", value = "批量保存用户", required = true) List<SaveUserDTO> saveUserDTOList) throws BusinessException;

    /**
     * Delete batch user result msg.
     *
     * @param ids the ids
     * @return the result msg
     * @throws BusinessException the business exception
     */
    @ApiOperation(value = "批量删除用户", notes = "批量删除用户", response = Boolean.class)
    @RequestMapping(value = "/user/sens/deleteBatchUser", method = RequestMethod.GET)
    ResultMsg<Boolean> deleteBatchUser(@RequestParam(value = "ids") List<Long> ids) throws BusinessException;

    /**
     * 更新用户信息.
     *
     * @param updateUserDTO the user dto
     * @return the boolean
     * @throws BusinessException the business exception
     */
    @ApiOperation(value = "修改用户", notes = "修改用户", response = Boolean.class)
    @RequestMapping(value = "/user/updateUser", method = RequestMethod.PUT, consumes = {"application/json"})
    ResultMsg<Boolean> updateUser(@Valid @RequestBody @ApiParam(name = "修改用户对象实体", value = "修改用户对象实体", required = true) UpdateUserDTO updateUserDTO) throws BusinessException;

    /**
     * Update platform user result msg.
     *
     * @param updatePlatfromUserDTO the update platfrom user dto
     * @return the result msg
     * @throws BusinessException the business exception
     */
    @ApiOperation(value = "修改平台用户", notes = "修改平台用户", response = Boolean.class)
    @RequestMapping(value = "/user/updatePlatformUser", method = RequestMethod.PUT, consumes = {"application/json"})
    ResultMsg<Boolean> updatePlatformUser(@Valid @RequestBody @ApiParam(name = "修改平台用户", value = "修改平台用户", required = true) UpdatePlatfromUserDTO updatePlatfromUserDTO) throws BusinessException;

    /**
     * Update current user result msg.
     *
     * @param updateCurrentUserDTO the update current user dto
     * @return the result msg
     * @throws BusinessException the business exception
     */
    @ApiOperation(value = "修改个人信息", notes = "修改个人信息", response = Boolean.class)
    @RequestMapping(value = "/user/updateCurrentUser", method = RequestMethod.PUT, consumes = {"application/json"})
    ResultMsg<Boolean> updateCurrentUser(@Valid @RequestBody @ApiParam(name = "修改用户对象实体", value = "修改用户对象实体", required = true) UpdateCurrentUserDTO updateCurrentUserDTO) throws BusinessException;

    /**
     * Update phone num result msg.
     *
     * @param password   the password
     * @param phoneNum   the phone num
     * @param verifyCode the verify code
     * @return the result msg
     * @throws BusinessException the business exception
     */
    @ApiOperation(value = "修改关联手机", notes = "修改关联手机", response = Boolean.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "password", value = "用户密码", required = true, dataType = "String"),
            @ApiImplicitParam(name = "phoneNum", value = "手机号码", required = true, dataType = "String"),
            @ApiImplicitParam(name = "verifyCode", value = "手机验证码", required = true, dataType = "String")
    })
    @RequestMapping(value = "/user/updatePhoneNum", method = RequestMethod.PUT)
    ResultMsg<Boolean> updatePhoneNum(@RequestParam(value = "password") String password, @RequestParam(value = "phoneNum") String phoneNum, @RequestParam(value = "verifyCode") String verifyCode) throws BusinessException;

    /**
     * Update current password result msg.
     *
     * @param oldPassword the old password
     * @param newPassword the new password
     * @return the result msg
     * @throws BusinessException the business exception
     */
    @ApiOperation(value = "修改密码", notes = "修改密码", response = Boolean.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "oldPassword", value = "旧密码", required = true, dataType = "String"),
            @ApiImplicitParam(name = "newPassword", value = "新密码", required = true, dataType = "String")
    })
    @RequestMapping(value = "/user/updateCurrentPassword", method = RequestMethod.PUT)
    ResultMsg<Boolean> updateCurrentPassword(@RequestParam(value = "oldPassword") String oldPassword, @RequestParam(value = "newPassword") String newPassword) throws BusinessException;

    /**
     * Update password by user name boolean.
     *
     * @param userId   the user id
     * @param password the password
     * @return the boolean
     * @throws BusinessException the business exception
     */
    @ApiOperation(value = "重置密码", notes = "重置密码为默认密码", response = Boolean.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "用户ID", required = true, dataType = "String"),
            @ApiImplicitParam(name = "password", value = "密码", required = true, dataType = "String")
    })
    @RequestMapping(value = "/user/sens/resetPasswordByUserId", method = RequestMethod.PUT)
    ResultMsg<Boolean> resetPasswordByUserId(@RequestParam(value = "userId") Long userId, @RequestParam(value = "password") String password) throws BusinessException;

    /**
     * Select one and permission by name user info with permission dto.
     *
     * @param userName the user name
     * @return the user info with permission dto
     * @throws BusinessException the business exception
     */
    @ApiOperation(value = "查询用户权限", notes = "查询用户权限", response = LoginInfoDTO.class)
    @ApiImplicitParam(name = "userName", value = "用户名", required = true, dataType = "String")
    @RequestMapping(value = "/user/getPermissionByName", method = RequestMethod.GET)
    ResultMsg<UserInfoWithPermissionDTO> getOneAndPermissionByName(@RequestParam(value = "userName") String userName) throws BusinessException;

    /**
     * 用户登录.
     *
     * @return 用户角色信息 result msg
     * @throws BusinessException the business exception
     */
    @ApiOperation(value = "初始化用户信息", notes = "初始化用户信息", response = LoginInfoDTO.class)
    @RequestMapping(value = "/user/init", method = RequestMethod.GET)
    ResultMsg<LoginInfoDTO> initUser() throws BusinessException;

    /**
     * 缓存用户的语言类别.
     *
     * @param language the language
     * @return the result msg
     * @throws BusinessException the business exception
     */
    @ApiOperation(value = "缓存用户语言", notes = "缓存用户语言", response = LoginInfoDTO.class)
    @ApiImplicitParam(name = "language", value = "用户选择的语言", required = true, dataType = "String")
    @RequestMapping(value = "/user/cacheLanguage", method = RequestMethod.PUT)
    ResultMsg<Boolean> cacheLanguage(@RequestParam(value = "language") String language) throws BusinessException;

    /**
     * 根据ID集合查询用户名.
     *
     * @param userIdsDTO the user names dto
     * @return the result msg
     * @throws BusinessException the business exception
     */
    @ApiOperation(value = "根据ID集合查询用户名", notes = "根据ID集合查询用户名", response = UserNamesDTO.class)
    @RequestMapping(value = "/user/sens/listUserNamesByIds", method = RequestMethod.POST)
    ResultMsg<UserNamesDTO> listUserNamesByIds(@Valid @RequestBody @ApiParam(name = "用户ID集合对象", value = "用户ID集合对象", required = true) UserIdsDTO userIdsDTO) throws BusinessException;

    /**
     * List users by user names result msg.
     *
     * @param userNames the user names
     * @return the result msg
     * @throws BusinessException the business exception
     */
    @ApiOperation(value = "根据用户名集合获取用户集合", notes = "根据用户名集合获取用户集合", response = UserNamesDTO.class)
    @ApiImplicitParam(name = "userNames", value = "用户名集合", required = true, dataType = "string", allowMultiple = true)
    @RequestMapping(value = "/user/sens/listUsersByUserNames", method = RequestMethod.GET)
    ResultMsg<List<GetUserDTO>> listUsersByUserNames(@RequestParam(value = "userNames") List<String> userNames) throws BusinessException;

    /**
     * Gets phone verify code.
     *
     * @param phoneNum   the phone num
     * @param verifyCode the verify code
     * @return the phone verify code
     * @throws BusinessException the business exception
     */
    @ApiOperation(value = "校验验手机验证证码", notes = "校验验手机验证证码", response = String.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "phoneNum", value = "手机号码", required = true, dataType = "string"),
            @ApiImplicitParam(name = "verifyCode", value = "手机验证码", required = true, dataType = "string")
    })
    @RequestMapping(value = "/user/public/checkPhoneVerifyCode", method = RequestMethod.GET)
    ResultMsg<String> checkPhoneVerifyCode(@RequestParam(value = "phoneNum") String phoneNum,
                                           @RequestParam(value = "verifyCode") String verifyCode) throws BusinessException;

    /**
     * Check current password result msg.
     *
     * @param password the password
     * @return the result msg
     * @throws BusinessException the business exception
     */
    @ApiOperation(value = "校验当前用户密码", notes = "校验当前用户密码", response = String.class)
    @ApiImplicitParam(name = "password", value = "用户密码", required = true, dataType = "string")
    @RequestMapping(value = "/user/checkCurrentPassword", method = RequestMethod.GET)
    ResultMsg checkCurrentPassword(@RequestParam(value = "password") String password) throws BusinessException;

    /**
     * 修改密码
     *
     * @param tokenUpdatePasswordDTO the token update password dto
     * @return the result msg
     * @throws BusinessException the business exception
     */
    @ApiOperation(value = "根据手机验证码修改用户密码", notes = "根据手机验证码修改用户密码", response = Boolean.class)
    @RequestMapping(value = "/user/public/updatePwdByPhoneVerify", method = RequestMethod.POST, consumes = {"application/json"})
    ResultMsg<Boolean> updatePwdByPhoneVerify(@Valid @RequestBody @ApiParam(name = "修改密码实体", value = "修改密码实体", required = true) TokenUpdatePasswordDTO tokenUpdatePasswordDTO) throws BusinessException;

    /**
     * Gets oauth permissions.
     *
     * @return the oauth permissions
     * @throws BusinessException the business exception
     */
    @ApiOperation(value = "获取权限映射集合", notes = "获取权限映射集合", response = OauthPermissionDTO.class)
    @RequestMapping(value = "/user/sens/getOauthPermissions", method = RequestMethod.GET)
    ResultMsg<List<OauthPermissionDTO>> getOauthPermissions() throws BusinessException;

    /**
     * Page student result msg.
     *
     * @param pageUserDTO the page user dto
     * @return the result msg
     * @throws BusinessException the business exception
     */
    @ApiOperation(value = "分页查询用户集合", notes = "分页查询用户集合", response = GetUserDTO.class)
    @RequestMapping(value = "/user/pageUser", method = RequestMethod.POST, consumes = {"application/json"})
    ResultMsg<Page<GetPageUserDTO>> pageUser(@Valid @RequestBody @ApiParam(name = "分页查询用户集合", value = "分页查询用户集合", required = true) PageUserDTO pageUserDTO) throws BusinessException;

    /**
     * List user by role code result msg.
     *
     * @param roleCode the role code
     * @return the result msg
     * @throws BusinessException the business exception
     */
    @ApiOperation(value = "根据角色查询用户集合", notes = "根据角色查询用户集合", response = GetUserDTO.class)
    @ApiImplicitParam(name = "roleCode", value = "角色code", required = true, dataType = "string")
    @RequestMapping(value = "/user/listUserByRoleCode", method = RequestMethod.GET)
    ResultMsg<List<GetUserDTO>> listUserByRoleCode(@RequestParam(value = "roleCode") String roleCode) throws BusinessException;

    /**
     * List user by role and domain result msg.
     *
     * @param roleCodes the role codes
     * @param domainId  the domain id
     * @return the result msg
     * @throws BusinessException the business exception
     */
    @ApiOperation(value = "根据角色查询用户集合", notes = "根据角色查询用户集合", response = GetUserDTO.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "roleCodes", value = "角色code集合", required = true, dataType = "string", allowMultiple = true),
            @ApiImplicitParam(name = "domainId", value = "应用id", required = true, dataType = "long")
    })
    @RequestMapping(value = "/user/listUserByRoleAndDomain", method = RequestMethod.GET)
    ResultMsg<List<GetUserDTO>> listUserByRoleAndDomain(@RequestParam(value = "roleCodes") Set<String> roleCodes, @RequestParam(value = "domainId") Long domainId) throws BusinessException;

    /**
     * Activate or freeze customer result msg.
     *
     * @param userId the user id
     * @param state  the state
     * @return the result msg
     * @throws BusinessException the business exception
     */
    @ApiOperation(value = "激活停用用户", notes = "激活停用用户", response = GetUserDTO.class)
    @RequestMapping(value = "/user/updateUserState", method = RequestMethod.PUT)
    ResultMsg<Boolean> updateUserState(@RequestParam(value = "userId") @ApiParam(name = "userId", value = "用户ID", required = true) Long userId,
                                       @RequestParam(value = "state") @ApiParam(name = "state", value = "状态", required = true) String state) throws BusinessException;

    /**
     * Check phone num exist result msg.
     *
     * @param phoneNums the phone nums
     * @return the result msg
     * @throws BusinessException the business exception
     */
    @ApiOperation(value = "校验手机号是否有重复", notes = "校验手机号是否有重复", response = Integer.class)
    @ApiImplicitParam(name = "phoneNums", value = "手机号码集合", required = true, dataType = "string", allowMultiple = true)
    @RequestMapping(value = "/user/sens/checkPhoneNumExist", method = RequestMethod.GET)
    ResultMsg<List<Integer>> checkPhoneNumExist(@RequestParam(value = "phoneNums") List<String> phoneNums) throws BusinessException;

    /**
     * Check user name exist result msg.
     *
     * @param userNames the user names
     * @return the result msg
     * @throws BusinessException the business exception
     */
    @ApiOperation(value = "校验用户名是否有重复", notes = "校验用户名是否有重复", response = Integer.class)
    @ApiImplicitParam(name = "userNames", value = "用户名集合", required = true, dataType = "string", allowMultiple = true)
    @RequestMapping(value = "/user/sens/checkUserNameExist", method = RequestMethod.GET)
    ResultMsg<List<Integer>> checkUserNameExist(@RequestParam(value = "userNames") List<String> userNames) throws BusinessException;

    /**
     * Switch default role result msg.
     *
     * @param roleCode the role code
     * @return the result msg
     * @throws BusinessException the business exception
     */
    @ApiOperation(value = "切换当前用户角色", notes = "切换当前用户角色", response = Boolean.class)
    @ApiImplicitParam(name = "roleCode", value = "角色代号", required = true, dataType = "string")
    @RequestMapping(value = "/user/switchDefaultRole", method = RequestMethod.GET)
    ResultMsg<Boolean> switchDefaultRole(@RequestParam(value = "roleCode") String roleCode) throws BusinessException;

}