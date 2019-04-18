package com.rzlearn.setting.feign;

import com.rzlearn.base.dto.ResultMsg;
import com.rzlearn.base.exception.BusinessException;
import com.rzlearn.setting.dto.SaveSysConfigDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;

/**
 * <p>ClassName:IDicController</p>
 * <p>Description:字典统一接口api</p>
 *
 * @author JiPeigong
 * @date 2018 -06-08 17:01:06
 */
@Api(tags = {"系统配置接口"})
@FeignClient("run-setting")
public interface ISysConfigController {

    /**
     * Gets sys config.
     *
     * @param key the key
     * @return the sys config
     * @throws BusinessException the business exception
     */
    @ApiOperation(value = "获取配置信息", notes = "获取配置信息", response = String.class)
    @ApiImplicitParam(name = "key", value = "key", dataType = "String")
    @RequestMapping(value = "/sysConfig/getSysConfig", method = RequestMethod.GET)
    ResultMsg<String> getSysConfig(@RequestParam(value = "key") String key) throws BusinessException;

    /**
     * Save sys config result msg.
     *
     * @param saveSysConfigDTO the save sys config dto
     * @return the result msg
     * @throws BusinessException the business exception
     */
    @ApiOperation(value = "保存配置信息", notes = "保存配置信息", response = Boolean.class)
    @RequestMapping(value = "/sysConfig/saveSysConfig", method = RequestMethod.POST, consumes = {"application/json"})
    ResultMsg<Boolean> saveSysConfig(@Valid @RequestBody SaveSysConfigDTO saveSysConfigDTO) throws BusinessException;
}