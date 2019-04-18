package com.rzlearn.base.dto;

import com.rzlearn.base.exception.BusinessException;
import com.rzlearn.base.support.MessageCode;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <p>ClassName:ResultMsg</p>
 * <p>Description: 返回结果封装</p>
 *
 * @param <T> the type parameter
 * @author zhangwb
 * @date 2018 -06-11 08:54:44
 */
@Data
@NoArgsConstructor
public class ResultMsg<T> {

    /**
     * 成功code
     */
    private static int SUCCESS_CODE = 200;

    /**
     * 结果code
     */
    private int resultCode;

    /**
     * 结果提示信息
     */
    private String resultMessage;

    /**
     * 结果对象
     */
    private T resultObject;

    /**
     * Instantiates a new Result msg.
     *
     * @param resultCode    the result code
     * @param resultMessage the result message
     * @param resultObject  the result object
     */
    public ResultMsg(int resultCode, String resultMessage, T resultObject) {
        this.resultCode = resultCode;
        this.resultMessage = resultMessage;
        this.resultObject = resultObject;
    }

    /**
     * Instantiates a new Result msg.
     *
     * @param messageCode  the message code
     * @param resultObject the result object
     */
    public ResultMsg(MessageCode messageCode, T resultObject) {
        this.resultCode = messageCode.code();
        this.resultMessage = messageCode.msg();
        this.resultObject = resultObject;
    }

    /**
     * Instantiates a new Result msg.
     *
     * @param messageCode the message code
     */
    public ResultMsg(MessageCode messageCode) {
        this.resultCode = messageCode.code();
        this.resultMessage = messageCode.msg();
    }

    /**
     * Check business exception.
     */
    public void checkBusinessException() {
        if (this.resultCode != SUCCESS_CODE) {
            throw new BusinessException(this.resultCode, this.resultMessage);
        }
    }

    /**
     * 带有check exception的获取结果方法.
     *
     * @return the t
     */
    public T resultObject(){
       if (this.resultCode != SUCCESS_CODE) {
           throw new BusinessException(this.resultCode, this.resultMessage);
       }
       return this.resultObject;
   }
}
