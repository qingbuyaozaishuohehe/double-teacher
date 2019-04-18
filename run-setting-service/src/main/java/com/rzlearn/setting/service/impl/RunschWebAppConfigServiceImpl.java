package com.rzlearn.setting.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.rzlearn.setting.entity.RunschWebAppConfig;
import com.rzlearn.setting.mapper.RunschWebAppConfigMapper;
import com.rzlearn.setting.service.IRunschWebAppConfigService;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

/**
 * <p>
 * WEB应用配置 服务实现类
 * </p>
 *
 * @author JiPeigong
 * @since 2018-08-06
 */
@Service
public class RunschWebAppConfigServiceImpl extends ServiceImpl<RunschWebAppConfigMapper, RunschWebAppConfig> implements IRunschWebAppConfigService {

    @Override
    @Cacheable(value = "SET_WEB_APP_CONFIG_", key = "'SET_WEB_APP_CONFIG_'+#domain+'_'+#language")
    public RunschWebAppConfig getRunschWebAppConfig(String domain, String language) {
        RunschWebAppConfig runschWebAppConfig = new RunschWebAppConfig();
        runschWebAppConfig.setDomainName(domain);
        runschWebAppConfig.setLanguage(language);
        runschWebAppConfig = this.selectOne(new EntityWrapper<>(runschWebAppConfig));
        return runschWebAppConfig;
    }

    @Override
    @Cacheable(value = "SET_WEB_APP_CONFIG_AGREEMENT_", key = "'SET_WEB_APP_CONFIG_AGREEMENT_'+#domain+'_'+#language")
    public String getAgreement(String domain, String language) {
        RunschWebAppConfig runschWebAppConfig = new RunschWebAppConfig();
        runschWebAppConfig.setDomainName(domain);
        runschWebAppConfig.setLanguage(language);
        runschWebAppConfig = this.selectOne(new EntityWrapper<>(runschWebAppConfig));
        return runschWebAppConfig == null ? "" : runschWebAppConfig.getAgreement();
    }

    @Override
    @CachePut(value = "SET_WEB_APP_CONFIG_AGREEMENT_", key = "'SET_WEB_APP_CONFIG_AGREEMENT_'+#domain+'_'+#language")
    public String saveAgreement(String domain, String language, String agreement) {
        return agreement;
    }

    @Override
    @CachePut(value = "SET_WEB_APP_CONFIG_", key = "'SET_WEB_APP_CONFIG_'+#runschWebAppConfig.domainName+'_'+#runschWebAppConfig.language")
    public RunschWebAppConfig saveRunschWebAppConfig(RunschWebAppConfig runschWebAppConfig) {
        this.insert(runschWebAppConfig);
        return this.selectById(runschWebAppConfig.getId());
    }

    @Override
    @CachePut(value = "SET_WEB_APP_CONFIG_", key = "'SET_WEB_APP_CONFIG_'+#runschWebAppConfig.domainName+'_'+#runschWebAppConfig.language")
    public RunschWebAppConfig updateRunschWebAppConfig(RunschWebAppConfig runschWebAppConfig) {
        this.updateById(runschWebAppConfig);
        return this.selectById(runschWebAppConfig.getId());
    }
}
