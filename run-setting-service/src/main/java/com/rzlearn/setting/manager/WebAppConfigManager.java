package com.rzlearn.setting.manager;

import com.baomidou.mybatisplus.mapper.Condition;
import com.rzlearn.base.basic.Page;
import com.rzlearn.base.dto.ResultMsg;
import com.rzlearn.base.util.ListUtil;
import com.rzlearn.setting.dto.GetDomainUserDTO;
import com.rzlearn.setting.dto.GetPageWebAppConfigDTO;
import com.rzlearn.setting.dto.GetWebAppConfigDTO;
import com.rzlearn.setting.dto.PageWebAppConfigDTO;
import com.rzlearn.setting.entity.RunschWebAppConfig;
import com.rzlearn.setting.service.IRunschWebAppConfigService;
import com.rzlearn.user.dto.GetUserDTO;
import com.rzlearn.user.feign.IUserController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * <p>ClassName:WebAppConfigManager</p>
 * <p>Description:</p>
 *
 * @author JiPeigong
 * @date 2018 -08-07 15:08
 */
@Service
public class WebAppConfigManager {

    @Autowired
    private IRunschWebAppConfigService runschWebAppConfigService;

    @Autowired
    private IUserController userController;

    /**
     * List user by role and domain list.
     *
     * @param roleCodes the role codes
     * @param domainId  the domain id
     * @return the list
     */
    public List<GetDomainUserDTO> listUserByRoleAndDomain(Set<String> roleCodes, Long domainId) {
        List<GetDomainUserDTO> list = new ArrayList<>();
        ResultMsg<List<GetUserDTO>> rsg = userController.listUserByRoleAndDomain(roleCodes, domainId);
        rsg.checkBusinessException();
        for (GetUserDTO getUserDTO : rsg.getResultObject()) {
            GetDomainUserDTO getDomainUserDTO = new GetDomainUserDTO();
            getDomainUserDTO.setDomainId(getUserDTO.getDomainId());
            getDomainUserDTO.setId(getUserDTO.getId());
            getDomainUserDTO.setUserName(getUserDTO.getUserName());
            list.add(getDomainUserDTO);
        }
        return list;
    }

    /**
     * Page web config page.
     *
     * @param pageWebAppConfigDTO the page web app config dto
     * @return the page
     */
    public Page<GetPageWebAppConfigDTO> pageWebConfig(PageWebAppConfigDTO pageWebAppConfigDTO) {
        Page<GetPageWebAppConfigDTO> result = new Page<>();
        com.baomidou.mybatisplus.plugins.Page<RunschWebAppConfig> page = new com.baomidou.mybatisplus.plugins.Page();
        page.setCurrent(pageWebAppConfigDTO.getPageNo());
        page.setSize(pageWebAppConfigDTO.getPageSize());
        page = runschWebAppConfigService.selectPage(page);
        result.setCurrent(page.getCurrent());
        result.setSize(page.getSize());
        result.setPages(page.getPages());
        result.setTotal(page.getTotal());

        List<GetPageWebAppConfigDTO> getWebAppConfigDTOList = new ArrayList<>();
        for (RunschWebAppConfig runschWebAppConfig : page.getRecords()) {
            GetPageWebAppConfigDTO getWebAppConfigDTO = new GetPageWebAppConfigDTO();
            getWebAppConfigDTO.setId(runschWebAppConfig.getId());
            getWebAppConfigDTO.setName(runschWebAppConfig.getName());
            getWebAppConfigDTO.setDomainName(runschWebAppConfig.getDomainName());
            getWebAppConfigDTO.setGmtCreate(runschWebAppConfig.getGmtCreate());
            getWebAppConfigDTO.setGmtModified(runschWebAppConfig.getGmtModified());
            getWebAppConfigDTOList.add(getWebAppConfigDTO);
        }
        result.setRecords(getWebAppConfigDTOList);
        return result;
    }

    /**
     * List all web config list.
     *
     * @return the list
     */
    public List<GetWebAppConfigDTO> listAllWebConfig() {
        List<GetWebAppConfigDTO> resultList = new ArrayList<>();
        List<RunschWebAppConfig> list = runschWebAppConfigService.selectList(new Condition());
        if (ListUtil.isNotEmpty(list)) {
            for (RunschWebAppConfig runschWebAppConfig : list) {
                GetWebAppConfigDTO getWebAppConfigDTO = new GetWebAppConfigDTO();
                getWebAppConfigDTO.setId(runschWebAppConfig.getId());
                getWebAppConfigDTO.setName(runschWebAppConfig.getName());
                getWebAppConfigDTO.setDomainName(runschWebAppConfig.getDomainName());
                resultList.add(getWebAppConfigDTO);
            }
        }
        return resultList;
    }

    /**
     * Gets login title.
     *
     * @param domain   the domain
     * @param language the language
     * @return the login title
     */
    public String getLoginTitle(String domain, String language) {
        String result = null;
        RunschWebAppConfig runschWebAppConfig = runschWebAppConfigService.getRunschWebAppConfig(domain, language);
        if (runschWebAppConfig != null) {
            result = runschWebAppConfig.getLoginTitle();
        }
        return result;
    }

    /**
     * Gets login bottom hint.
     *
     * @param domain   the domain
     * @param language the language
     * @return the login bottom hint
     */
    public String getLoginBottomHint(String domain, String language) {
        String result = null;
        RunschWebAppConfig runschWebAppConfig = runschWebAppConfigService.getRunschWebAppConfig(domain, language);
        if (runschWebAppConfig != null) {
            result = runschWebAppConfig.getLoginBottomHint();
        }
        return result;
    }

    /**
     * Gets home title.
     *
     * @param domain   the domain
     * @param language the language
     * @return the home title
     */
    public String getHomeTitle(String domain, String language) {
        String result = null;
        RunschWebAppConfig runschWebAppConfig = runschWebAppConfigService.getRunschWebAppConfig(domain, language);
        if (runschWebAppConfig != null) {
            result = runschWebAppConfig.getHomeTitle();
        }
        return result;
    }

    /**
     * Gets powered by.
     *
     * @param domain   the domain
     * @param language the language
     * @return the powered by
     */
    public String getPoweredBy(String domain, String language) {
        String result = null;
        RunschWebAppConfig runschWebAppConfig = runschWebAppConfigService.getRunschWebAppConfig(domain, language);
        if (runschWebAppConfig != null) {
            result = runschWebAppConfig.getPoweredBy();
        }
        return result;
    }

    /**
     * Gets agreement.
     *
     * @param domain   the domain
     * @param language the language
     * @return the agreement
     */
    public String getAgreement(String domain, String language) {
        String result = null;
        RunschWebAppConfig runschWebAppConfig = runschWebAppConfigService.getRunschWebAppConfig(domain, language);
        if (runschWebAppConfig != null) {
            result = runschWebAppConfig.getAgreement();
        }
        return result;
    }

    /**
     * Gets about us.
     *
     * @param domain   the domain
     * @param language the language
     * @return the about us
     */
    public String getAboutUs(String domain, String language) {
        String result = null;
        RunschWebAppConfig runschWebAppConfig = runschWebAppConfigService.getRunschWebAppConfig(domain, language);
        if (runschWebAppConfig != null) {
            result = runschWebAppConfig.getAboutUs();
        }
        return result;
    }

    /**
     * Gets agreement.
     *
     * @param id the id
     * @return the agreement
     */
    public String getAgreement(Long id) {
        String result = null;
        RunschWebAppConfig runschWebAppConfig = runschWebAppConfigService.selectById(id);
        if (runschWebAppConfig != null) {
            result = runschWebAppConfig.getAgreement();
        }
        return result;
    }

    /**
     * Remove web config boolean.
     *
     * @param id the id
     * @return the boolean
     */
    public Boolean removeWebConfig(Long id) {
        return runschWebAppConfigService.deleteById(id);
    }
}
