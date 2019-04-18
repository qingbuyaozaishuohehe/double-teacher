package com.rzlearn.setting.feign;

import com.rzlearn.base.dto.ResultMsg;
import com.rzlearn.base.exception.BusinessException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * <p>ClassName:IVersionController</p>
 * <p>Description:版本管理</p>
 *
 * @author JiPeigong
 * @date 2018 -12-10 17:01:06
 */
@Api(tags = {"系统版本"})
@FeignClient("run-setting")
public interface IVersionController {

    /**
     * 获取当前系统版本号
     *
     * @return
     * @throws BusinessException
     */
    @ApiOperation(value = "获取当前系统版本号", notes = "获取当前系统版本号", response = String.class)
    @RequestMapping(value = "/version/public/getCurrentVersion", method = RequestMethod.GET)
    ResultMsg<String> getCurrentVersion() throws BusinessException;

    /**
     * 设置版本号
     *
     * @param value
     * @return
     * @throws BusinessException
     */
    @ApiOperation(value = "设置当前系统版本号", notes = "设置当前系统版本号", response = String.class)
    @ApiImplicitParam(name = "value", value = "value", dataType = "String")
    @RequestMapping(value = "/version/updateVersion", method = RequestMethod.PUT)
    ResultMsg<String> getCurrentVersion(@RequestParam(value = "value") String value) throws BusinessException;


}