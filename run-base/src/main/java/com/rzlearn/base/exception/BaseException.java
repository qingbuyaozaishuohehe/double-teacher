/**
 *
 */
package com.rzlearn.base.exception;

import com.rzlearn.base.support.MessageCode;
import lombok.Data;

/**
 * <p>ClassName:BaseException</p>
 * <p>Description:基础异常</p>
 *
 * @author JiPeigong
 * @date 2018年4月11日
 */
@SuppressWarnings("serial")
@Data
public abstract class BaseException extends RuntimeException {

    protected int code;

    protected String message;

    public BaseException() {
    }

    public BaseException(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public BaseException(String message, Throwable ex) {
        super(message, ex);
    }

    public BaseException(MessageCode messageCode) {
        super(messageCode.msg());
        this.code = messageCode.code();
        this.message = messageCode.msg();
    }

    public BaseException(MessageCode messageCode, String param) {
        super(messageCode.msg() + ":" + param);
        this.code = messageCode.code();
        this.message = messageCode.msg() + ":" + param;
    }
}
