package com.rzlearn.setting.controller;

import com.rzlearn.base.basic.BaseController;
import com.rzlearn.base.dto.ResultMsg;
import com.rzlearn.base.exception.BusinessException;
import com.rzlearn.base.support.MessageCode;
import com.rzlearn.setting.constant.BusinessConst;
import com.rzlearn.setting.dto.SaveSysConfigDTO;
import com.rzlearn.setting.feign.IVersionController;
import com.rzlearn.setting.manager.SysConfigManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>ClassName:VersionController</p>
 * <p>Description:系统版本</p>
 *
 * @author JiPeigong
 * @date 2018-12-10 11:40:03
 **/
@RestController
@Slf4j
public class VersionController extends BaseController implements IVersionController {

    @Autowired
    private SysConfigManager sysConfigManager;

    @Override
    public ResultMsg<String> getCurrentVersion() throws BusinessException {
        return new ResultMsg<>(MessageCode.SUCCESS, sysConfigManager.getSysConfig(BusinessConst.VERSION));
    }

    @Override
    public ResultMsg<String> getCurrentVersion(@RequestParam(value = "value") String value) throws BusinessException {
        SaveSysConfigDTO sysConfigDTO = new SaveSysConfigDTO();
        sysConfigDTO.setKey(BusinessConst.VERSION);
        sysConfigDTO.setValue(value);
        return new ResultMsg<>(MessageCode.SUCCESS, sysConfigManager.saveSysConfig(sysConfigDTO));
    }
}