package com.rzlearn.base.exception;

import com.rzlearn.base.support.MessageCode;

/**
 * <p>ClassName:BusinessException</p>
 * <p>Description:业务异常</p>
 *
 * @author JiPeigong
 * @date 2018年4月11日
 */
@SuppressWarnings("serial")
public class BusinessException extends BaseException {

    public BusinessException(MessageCode messageCode) {
        super(messageCode);
    }

    public BusinessException(MessageCode messageCode, String param) {
        super(messageCode, param);
    }

    public BusinessException() {
    }

    public BusinessException(String message, Throwable ex) {
        super(message, ex);
    }

    public BusinessException(int code, String message) {
        super(code, message);
    }
}