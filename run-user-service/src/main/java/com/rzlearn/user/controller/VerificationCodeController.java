package com.rzlearn.user.controller;

import com.rzlearn.base.basic.BaseController;
import com.rzlearn.base.dto.ResultMsg;
import com.rzlearn.base.support.MessageCode;
import com.rzlearn.user.constant.BusinessConst;
import com.rzlearn.user.feign.IVerificationCodeController;
import com.rzlearn.user.manager.VerificationCodeManager;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * <p>ClassName:VerificationCodeController</p>
 * <p>Description:验证码管理</p>
 *
 * @author ZhangWenbing
 * @date 2018 -06-15 11:20:15
 */
@RestController
@Slf4j
public class VerificationCodeController extends BaseController implements IVerificationCodeController {

    @Autowired
    private VerificationCodeManager verificationCodeManager;

    @Override
    public ResultMsg getCode(HttpServletResponse response, @PathVariable String key) {
        String code = "";
        try {
            response.setContentType(BusinessConst.VERIFY_TYPE);
            code = verificationCodeManager.outputVerifyImage(BusinessConst.WIDTH, BusinessConst.HEIGHT, response.getOutputStream(), 4);
            if (StringUtils.isNotEmpty(code)) {
                if(!verificationCodeManager.saveCode(key, code)){
                    return new ResultMsg(MessageCode.FAILED,false);
                }
            }
        } catch (IOException e) {
            return new ResultMsg(MessageCode.FAILED,false);
        }
        return new ResultMsg(MessageCode.SUCCESS,true);
    }

    @Override
    public ResultMsg checkCode(@RequestParam String key, @RequestParam String code) {
        String saveCode = verificationCodeManager.getCode(key);
        if (saveCode == null) {
            return new ResultMsg(MessageCode.VERIFICATION_CODE_TIME_OUT);
        }
        if (saveCode.toLowerCase().equals(code.toLowerCase())) {
            return new ResultMsg(MessageCode.SUCCESS);
        }
        return new ResultMsg(MessageCode.VERIFICATION_CODE_ERROR);
    }
}
