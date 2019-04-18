package com.rzlearn.base.aop;


import com.rzlearn.base.dto.AnalysisLogDTO;
import com.rzlearn.base.util.LogRecordUtil;
import com.rzlearn.base.util.WebUtils;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Pointcut;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

/**
 * <p>ClassName:LogAopConfig</p>
 * <p>Description:用户操作日志统一处理</p>
 *
 * @author JiPeigong
 * @date 2018年5月9日
 * @see
 * @since
 */
@Slf4j
public abstract class AbstractLogAop {

    @Pointcut("execution(public * com.rzlearn.*.controller.*.*(..))")
    public void webLog() {
    }

    /**
     * 用户控制层操作日志统一打印
     *
     * @param pjp
     * @throws Throwable
     */
    @Around("webLog()")
    public Object analysisArround(ProceedingJoinPoint pjp) throws Throwable {
        Long currentTimeMillis = System.currentTimeMillis();
        Object o = pjp.proceed();
        HttpServletRequest request = WebUtils.getCurrentRequest();
        Long cost = System.currentTimeMillis() - currentTimeMillis;
        AnalysisLogDTO analysisLogDTO = new AnalysisLogDTO();
        analysisLogDTO.setClazz(pjp.getSignature().getDeclaringTypeName());
        analysisLogDTO.setMethod(pjp.getSignature().getName());
        analysisLogDTO.setCost(cost);
        analysisLogDTO.setUri(request.getRequestURI());
        analysisLogDTO.setParam(Arrays.toString(pjp.getArgs()));
        LogRecordUtil.recordAnalysis(analysisLogDTO);
        return o;
    }
}
