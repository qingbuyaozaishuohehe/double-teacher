package com.rzlearn.base.basic;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.netflix.client.ClientException;
import com.rzlearn.base.constant.BaseConsts;
import com.rzlearn.base.dto.ResultMsg;
import com.rzlearn.base.exception.BaseException;
import com.rzlearn.base.support.MessageCode;
import feign.RetryableException;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * <p>ClassName:AbstractController</p>
 * <p>Description: 控制器基类</p>
 *
 * @author JiPeigong
 * @date 2018年4月11日
 * @see
 * @since
 */
@Slf4j
public abstract class BaseController {

    /**
     * 异常处理
     *
     * @param request
     * @param response
     * @param ex
     * @throws Exception
     */
    @ExceptionHandler(Exception.class)
    public void exceptionHandler(HttpServletRequest request, HttpServletResponse response, Exception ex)
            throws Exception {
        ResultMsg resultMsg = new ResultMsg();
        if (ex instanceof BaseException) {
            BaseException baseException = (BaseException) ex;
            resultMsg.setResultCode(baseException.getCode());
            resultMsg.setResultMessage(baseException.getMessage());
            log.error(baseException.getMessage(), ex);
        } else if (ex instanceof RetryableException) {
            resultMsg.setResultCode(HttpStatus.SC_INTERNAL_SERVER_ERROR);
            resultMsg.setResultMessage(MessageCode.FEIGN_TIMEOUT.msg());
            log.error(ex.getMessage(), ex);
        } else if (ex instanceof ClientException) {
            resultMsg.setResultCode(HttpStatus.SC_INTERNAL_SERVER_ERROR);
            resultMsg.setResultMessage(MessageCode.CLIENT_SERVER_NOT_EXIT.msg());
            log.error(ex.getMessage(), ex);
        } else if (ex instanceof MethodArgumentNotValidException) {
            resultMsg.setResultCode(HttpStatus.SC_INTERNAL_SERVER_ERROR);
            int index = ex.getMessage().lastIndexOf(BaseConsts.DEFAULT_MESSAGE);
            if (index > 0) {
                String defaultMessage = ex.getMessage().substring(index + BaseConsts.DEFAULT_MESSAGE.length(), ex.getMessage().length() - 2);
                resultMsg.setResultMessage(MessageCode.PARAM_ILLEGAL.msg() + defaultMessage);
            } else {
                resultMsg.setResultMessage(MessageCode.PARAM_ILLEGAL.msg());
            }
            log.error(ex.getMessage(), ex);
        } else if (ex instanceof HttpMessageNotReadableException) {
            resultMsg.setResultCode(HttpStatus.SC_INTERNAL_SERVER_ERROR);
            resultMsg.setResultMessage(MessageCode.JSON_PARAM_FORMAT_EXCEPTION.msg());
            log.error(ex.getMessage(), ex);
        } else {
            resultMsg.setResultCode(HttpStatus.SC_INTERNAL_SERVER_ERROR);
            if (ex.getMessage() != null && ex.getMessage().contains(ClientException.class.getName())) {
                int index = ex.getMessage().lastIndexOf(":");
                String clientName = "";
                if (index > 0) {
                    clientName = ex.getMessage().substring(index);
                }
                resultMsg.setResultMessage(MessageCode.CLIENT_SERVER_NOT_EXIT.msg() + clientName);
            } else {
                resultMsg.setResultMessage(MessageCode.OPERATE_FAILED.msg());
            }
            log.error(ex.getMessage(), ex);
        }
        response.setContentType("application/json;charset=UTF-8");
        log.info("EXCEPTION : " + JSON.toJSON(resultMsg));
        byte[] bytes = JSON.toJSONBytes(resultMsg, SerializerFeature.DisableCircularReferenceDetect);
        response.getOutputStream().write(bytes);
    }
}
