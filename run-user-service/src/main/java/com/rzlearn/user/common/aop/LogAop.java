package com.rzlearn.user.common.aop;


import com.rzlearn.base.aop.AbstractLogAop;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

/**
 * <p>ClassName:aop</p>
 * <p>Description:统一日志打印</p>
 * @author JiPeigong
 * @date 2018-06-08 16:04:11
 **/
@Slf4j
@Aspect
@Component
public class LogAop extends AbstractLogAop {

}
