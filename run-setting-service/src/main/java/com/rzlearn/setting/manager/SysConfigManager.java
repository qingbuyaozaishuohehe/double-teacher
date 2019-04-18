package com.rzlearn.setting.manager;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.rzlearn.base.exception.BusinessException;
import com.rzlearn.base.support.MessageCode;
import com.rzlearn.setting.dto.SaveSysConfigDTO;
import com.rzlearn.setting.entity.RunschSysConfig;
import com.rzlearn.setting.service.IRunschSysConfigService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

/**
 * <p>ClassName:SysConfigManager</p>
 * <p>Description:</p>
 *
 * @author JiPeigong
 * @date 2018 -08-02 11:40
 */
@Service
public class SysConfigManager {

    @Autowired
    private IRunschSysConfigService runschSysConfigService;

    /**
     * Gets sys config.
     *
     * @param key the key
     * @return the sys config
     */
    @Cacheable(value = "SYS_CONFIG_", key = "'SYS_CONFIG_'+#key")
    public String getSysConfig(String key) {
        String result = null;
        if (StringUtils.isEmpty(key)) {
            throw new BusinessException(MessageCode.PARAM_ILLEGAL);
        }
        RunschSysConfig runschSysConfig = new RunschSysConfig();
        runschSysConfig.setKey(key);
        runschSysConfig = runschSysConfigService.selectOne(new EntityWrapper<>(runschSysConfig));
        if (runschSysConfig != null) {
            result = runschSysConfig.getValue();
        }
        return result;
    }

    /**
     * Save sys config boolean.
     *
     * @param saveSysConfigDTO the save sys config dto
     * @return the boolean
     */
    @CachePut(value = "SYS_CONFIG_", key = "'SYS_CONFIG_'+#saveSysConfigDTO.key")
    public String saveSysConfig(SaveSysConfigDTO saveSysConfigDTO) {
        RunschSysConfig runschSysConfig = new RunschSysConfig();
        runschSysConfig.setKey(saveSysConfigDTO.getKey());
        RunschSysConfig existSysConfig = runschSysConfigService.selectOne(new EntityWrapper<>(runschSysConfig));
        if (existSysConfig != null) {
            runschSysConfig.setId(existSysConfig.getId());
        }
        runschSysConfig.setValue(saveSysConfigDTO.getValue());
        Boolean insertSuccess = runschSysConfigService.insertOrUpdate(runschSysConfig);
        if (!insertSuccess) {
            throw new BusinessException(MessageCode.CRAETE_FAILED);
        }
        return runschSysConfig.getValue();
    }
}
