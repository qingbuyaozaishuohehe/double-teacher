package com.rzlearn.setting.service;

import com.baomidou.mybatisplus.service.IService;
import com.rzlearn.setting.entity.RunschWebAppConfig;

/**
 * <p>
 * WEB应用配置 服务类
 * </p>
 *
 * @author JiPeigong
 * @since 2018 -08-06
 */
public interface IRunschWebAppConfigService extends IService<RunschWebAppConfig> {

    /**
     * Gets runsch web app config.
     *
     * @param domain   the domain
     * @param language the language
     * @return the runsch web app config
     */
    RunschWebAppConfig getRunschWebAppConfig(String domain, String language);

    /**
     * Gets agreement.
     *
     * @param domain   the domain
     * @param language the language
     * @return the agreement
     */
    String getAgreement(String domain, String language);

    /**
     * Save agreement string.
     *
     * @param domain    the domain
     * @param language  the language
     * @param agreement the agreement
     * @return the string
     */
    String saveAgreement(String domain, String language, String agreement);

    /**
     * Save runsch web app config runsch web app config.
     *
     * @param runschWebAppConfig the runsch web app config
     * @return the runsch web app config
     */
    RunschWebAppConfig saveRunschWebAppConfig(RunschWebAppConfig runschWebAppConfig);

    /**
     * Updat runsch web app config runsch web app config.
     *
     * @param runschWebAppConfig the runsch web app config
     * @return the runsch web app config
     */
    RunschWebAppConfig updateRunschWebAppConfig(RunschWebAppConfig runschWebAppConfig);
}
