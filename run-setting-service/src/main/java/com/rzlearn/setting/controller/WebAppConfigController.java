package com.rzlearn.setting.controller;

import com.rzlearn.base.basic.BaseController;
import com.rzlearn.base.basic.Page;
import com.rzlearn.base.dto.ResultMsg;
import com.rzlearn.base.exception.BusinessException;
import com.rzlearn.base.support.MessageCode;
import com.rzlearn.base.util.WebUtils;
import com.rzlearn.setting.dto.GetDomainUserDTO;
import com.rzlearn.setting.dto.GetPageWebAppConfigDTO;
import com.rzlearn.setting.dto.GetWebAppConfigDTO;
import com.rzlearn.setting.dto.PageWebAppConfigDTO;
import com.rzlearn.setting.feign.IWebAppConfigController;
import com.rzlearn.setting.manager.WebAppConfigManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Set;

/**
 * <p>ClassName:WebAppConfigController</p>
 * <p>Description:</p>
 *
 * @author JiPeigong
 * @date 2018-08-06 14:55
 **/
@RestController
@Slf4j
public class WebAppConfigController extends BaseController implements IWebAppConfigController {

    @Autowired
    private WebAppConfigManager webAppConfigManager;

    @Override
    public ResultMsg<Page<GetPageWebAppConfigDTO>> pageWebConfig(@RequestBody PageWebAppConfigDTO pageWebAppConfigDTO) throws BusinessException {
        return new ResultMsg(MessageCode.SUCCESS, webAppConfigManager.pageWebConfig(pageWebAppConfigDTO));
    }

    @Override
    public ResultMsg<List<GetWebAppConfigDTO>> listAllWebConfig() throws BusinessException {
        return new ResultMsg(MessageCode.SUCCESS, webAppConfigManager.listAllWebConfig());
    }

    @Override
    public ResultMsg<String> getAgreement(@RequestParam(value = "id") Long id) throws BusinessException {
        return new ResultMsg(MessageCode.SUCCESS, webAppConfigManager.getAgreement(id));
    }

    @Override
    public ResultMsg<Boolean> removeWebConfig(@RequestParam(value = "id") Long id) throws BusinessException {
        return new ResultMsg(MessageCode.SUCCESS, webAppConfigManager.removeWebConfig(id));
    }

    @Override
    public ResultMsg<String> getLoginTitle() throws BusinessException {
        return new ResultMsg(MessageCode.SUCCESS, webAppConfigManager.getLoginTitle(WebUtils.getDomain(), WebUtils.getCurrentUserLanguage()));
    }

    @Override
    public ResultMsg<String> getLoginBottomHint() throws BusinessException {
        return new ResultMsg(MessageCode.SUCCESS, webAppConfigManager.getLoginBottomHint(WebUtils.getDomain(), WebUtils.getCurrentUserLanguage()));
    }

    @Override
    public ResultMsg<String> getHomeTitle() throws BusinessException {
        return new ResultMsg(MessageCode.SUCCESS, webAppConfigManager.getHomeTitle(WebUtils.getDomain(), WebUtils.getCurrentUserLanguage()));
    }

    @Override
    public ResultMsg<String> getPoweredBy() throws BusinessException {
        return new ResultMsg(MessageCode.SUCCESS, webAppConfigManager.getPoweredBy(WebUtils.getDomain(), WebUtils.getCurrentUserLanguage()));
    }

    @Override
    public ResultMsg<String> getAgreement() throws BusinessException {
        return new ResultMsg(MessageCode.SUCCESS, webAppConfigManager.getAgreement(WebUtils.getDomain(), WebUtils.getCurrentUserLanguage()));
    }

    @Override
    public ResultMsg<String> getAboutUs() throws BusinessException {
        return new ResultMsg(MessageCode.SUCCESS, webAppConfigManager.getAboutUs(WebUtils.getDomain(), WebUtils.getCurrentUserLanguage()));
    }

    @Override
    public ResultMsg<List<GetDomainUserDTO>> listUserByRoleAndDomain(@RequestParam(value = "roleCodes") Set<String> roleCodes,
                                                                     @RequestParam(value = "domainId") Long domainId) throws BusinessException {
        return new ResultMsg(MessageCode.SUCCESS, webAppConfigManager.listUserByRoleAndDomain(roleCodes, domainId));
    }
}
