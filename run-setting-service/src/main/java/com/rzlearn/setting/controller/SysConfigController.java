package com.rzlearn.setting.controller;

import com.rzlearn.base.basic.BaseController;
import com.rzlearn.base.dto.ResultMsg;
import com.rzlearn.base.exception.BusinessException;
import com.rzlearn.base.support.MessageCode;
import com.rzlearn.setting.dto.SaveSysConfigDTO;
import com.rzlearn.setting.feign.ISysConfigController;
import com.rzlearn.setting.manager.SysConfigManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * <p>ClassName:SysConfigController</p>
 * <p>Description:系统配置信息</p>
 *
 * @author JiPeigong
 * @date 2018-08-02 11:40:03
 **/
@RestController
@Slf4j
public class SysConfigController extends BaseController implements ISysConfigController {

    @Autowired
    private SysConfigManager sysConfigManager;

    @Override
    public ResultMsg<String> getSysConfig(@RequestParam(value = "key") String key) throws BusinessException {
        return new ResultMsg(MessageCode.SUCCESS, sysConfigManager.getSysConfig(key));
    }

    @Override
    public ResultMsg<Boolean> saveSysConfig(@Valid @RequestBody SaveSysConfigDTO saveSysConfigDTO) throws BusinessException {
        String value = sysConfigManager.saveSysConfig(saveSysConfigDTO);
        return new ResultMsg(MessageCode.SUCCESS, value != null);
    }
}