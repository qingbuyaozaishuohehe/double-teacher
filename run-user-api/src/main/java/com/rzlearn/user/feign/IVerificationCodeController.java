package com.rzlearn.user.feign;

import com.rzlearn.base.dto.ResultMsg;
import com.rzlearn.base.exception.BusinessException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletResponse;

/**
 * <p>ClassName:IVerificationCodeController</p>
 * <p>Description:验证码管理</p>
 *
 * @author ZhangWenbing
 * @date 2018-06-15 11:20:15
 **/
@Api(tags = {"验证码管理"})
@FeignClient("run-user")
public interface IVerificationCodeController {

    /**
     * 生成验证码
     *
     * @param response
     * @param key
     * @return
     * @throws BusinessException
     */
    @ApiOperation(value = "生成验证码", notes = "生成验证码")
    @ApiImplicitParam(name = "key", value = "验证码对应唯一key", required = true, dataType = "string")
    @RequestMapping(value = "/user/generateCode/{key}", method = RequestMethod.GET)
    ResultMsg getCode(HttpServletResponse response, @PathVariable String key) throws BusinessException;


    /**
     * 验证验证码
     *
     * @param key
     * @param code
     * @return
     * @throws BusinessException
     */
    @ApiOperation(value = "验证验证码", notes = "验证验证码")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "key", value = "验证码对应唯一key", required = true, dataType = "string"),
            @ApiImplicitParam(name = "code", value = "验证码", required = true, dataType = "string")
    })
    @RequestMapping(value = "/user/checkCode", method = RequestMethod.GET)
    ResultMsg<String> checkCode(@RequestParam(value = "key") String key, @RequestParam(value = "code") String code) throws BusinessException;
}
