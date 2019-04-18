package com.rzlearn.setting.feign;

import com.rzlearn.base.basic.Page;
import com.rzlearn.base.dto.ResultMsg;
import com.rzlearn.base.exception.BusinessException;
import com.rzlearn.setting.dto.*;
import io.swagger.annotations.*;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;
import java.util.Set;

/**
 * <p>ClassName:IWebAppConfigController</p>
 * <p>Description:</p>
 *
 * @author JiPeigong
 * @date 2018 -08-06 14:47
 */
@Api(tags = {"web应用配置信息"})
@FeignClient("run-setting")
public interface IWebAppConfigController {

    /**
     * Page web config result msg.
     *
     * @param pageWebAppConfigDTO the page web app config dto
     * @return the result msg
     * @throws BusinessException the business exception
     */
    @ApiOperation(value = "分页查询web应用配置", notes = "分页查询web应用配置", response = GetPageWebAppConfigDTO.class)
    @RequestMapping(value = "/webApp/pageWebConfig", method = RequestMethod.POST, consumes = {"application/json"})
    ResultMsg<Page<GetPageWebAppConfigDTO>> pageWebConfig(@Valid @RequestBody @ApiParam(name = "分页查询web应用配置", value = "分页查询web应用配置集合", required = true) PageWebAppConfigDTO pageWebAppConfigDTO) throws BusinessException;


    /**
     * List all web config result msg.
     *
     * @return the result msg
     * @throws BusinessException the business exception
     */
    @ApiOperation(value = "查询所有web应用配置", notes = "查询所有web应用配置", response = GetWebAppConfigDTO.class)
    @RequestMapping(value = "/webApp/listAllWebConfig", method = RequestMethod.GET)
    ResultMsg<List<GetWebAppConfigDTO>> listAllWebConfig() throws BusinessException;

    /**
     * Gets agreemen.
     *
     * @param id the id
     * @return the agreemen
     * @throws BusinessException the business exception
     */
    @ApiOperation(value = "获取协议条款", notes = "获取协议条款", response = Boolean.class)
    @ApiImplicitParam(name = "id", value = "id", required = true, dataType = "long")
    @RequestMapping(value = "/webApp/getAgreement", method = RequestMethod.GET)
    ResultMsg<String> getAgreement(@RequestParam(value = "id") Long id) throws BusinessException;

    /**
     * Remove web config result msg.
     *
     * @param id the id
     * @return the result msg
     * @throws BusinessException the business exception
     */
    @ApiOperation(value = "删除应用配置", notes = "删除应用配置", response = Boolean.class)
    @ApiImplicitParam(name = "id", value = "id", required = true, dataType = "long")
    @RequestMapping(value = "/webApp/removeWebConfig", method = RequestMethod.DELETE)
    ResultMsg<Boolean> removeWebConfig(@RequestParam(value = "id") Long id) throws BusinessException;

    /**
     * Gets login title.
     *
     * @return the login title
     * @throws BusinessException the business exception
     */
    @ApiOperation(value = "获取登陆提示语", notes = "获取登陆提示语", response = String.class)
    @RequestMapping(value = "/webApp/public/getLoginTitle", method = RequestMethod.GET)
    ResultMsg<String> getLoginTitle() throws BusinessException;

    /**
     * Gets login bottom hint.
     *
     * @return the login bottom hint
     * @throws BusinessException the business exception
     */
    @ApiOperation(value = "获取登陆底部提示语", notes = "获取登陆底部提示语", response = String.class)
    @RequestMapping(value = "/webApp/public/getLoginBottomHint", method = RequestMethod.GET)
    ResultMsg<String> getLoginBottomHint() throws BusinessException;

    /**
     * Gets home title.
     *
     * @return the home title
     * @throws BusinessException the business exception
     */
    @ApiOperation(value = "获取首页标题", notes = "获取首页标题", response = String.class)
    @RequestMapping(value = "/webApp/public/getHomeTitle", method = RequestMethod.GET)
    ResultMsg<String> getHomeTitle() throws BusinessException;

    /**
     * Gets powered by.
     *
     * @return the powered by
     * @throws BusinessException the business exception
     */
    @ApiOperation(value = "获取PoweredBy", notes = "获取PoweredBy", response = String.class)
    @RequestMapping(value = "/webApp/public/getPoweredBy", method = RequestMethod.GET)
    ResultMsg<String> getPoweredBy() throws BusinessException;

    /**
     * Gets agreement.
     *
     * @return the agreement
     * @throws BusinessException the business exception
     */
    @ApiOperation(value = "获取Agreement", notes = "获取Agreement", response = String.class)
    @RequestMapping(value = "/webApp/public/getAgreement", method = RequestMethod.GET)
    ResultMsg<String> getAgreement() throws BusinessException;

    /**
     * Gets about us.
     *
     * @return the about us
     * @throws BusinessException the business exception
     */
    @ApiOperation(value = "获取AboutU", notes = "获取AboutU", response = String.class)
    @RequestMapping(value = "/webApp/public/getAboutUs", method = RequestMethod.GET)
    ResultMsg<String> getAboutUs() throws BusinessException;

    /**
     * List user by role and domain result msg.
     *
     * @param roleCodes the role codes
     * @param domainId  the domain id
     * @return the result msg
     * @throws BusinessException the business exception
     */
    @ApiOperation(value = "根据角色查询用户集合", notes = "根据角色查询用户集合", response = GetDomainUserDTO.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "roleCodes", value = "角色code集合", required = true, dataType = "string", allowMultiple = true),
            @ApiImplicitParam(name = "domainId", value = "应用id", required = true, dataType = "long")
    })
    @RequestMapping(value = "/user/listUserByRoleAndDomain", method = RequestMethod.GET)
    ResultMsg<List<GetDomainUserDTO>> listUserByRoleAndDomain(@RequestParam(value = "roleCodes") Set<String> roleCodes, @RequestParam(value = "domainId") Long domainId) throws BusinessException;
}
