package com.rzlearn.base.util;

import com.alibaba.fastjson.JSON;
import com.rzlearn.base.constant.BaseConsts;
import com.rzlearn.base.dto.AnalysisLogDTO;
import lombok.extern.slf4j.Slf4j;

/**
 * <p>ClassName:LogRecordUtil</p>
 * <p>Description:</p>
 *
 * @author JiPeigong
 * @date 2018 -07-26 16:57
 */
@Slf4j
public class LogRecordUtil {

    /**
     * Record.
     *
     * @param analysisLogDTO the analysis log dto
     */
    public static void recordAnalysis(AnalysisLogDTO analysisLogDTO) {
        analysisLogDTO.setLogType(BaseConsts.RECORD_LOG_TYPE_ALL);
        analysisLogDTO.setUserName(WebUtils.getCurrentUserName());
        analysisLogDTO.setUserId(WebUtils.getCurrentUserId());
        analysisLogDTO.setDate(System.currentTimeMillis());
        analysisLogDTO.setIp(WebUtils.getRemoteIp());
        log.info(JSON.toJSONString(analysisLogDTO));
    }

    /**
     * Record.
     *
     * @param type     the type
     * @param userId   the user id
     * @param userName the user name
     */
    public static void record(String type, Long userId, String userName) {
        AnalysisLogDTO analysisLogDTO = new AnalysisLogDTO();
        analysisLogDTO.setLogType(type);
        analysisLogDTO.setUserId(userId);
        analysisLogDTO.setUserName(userName);
        analysisLogDTO.setIp(WebUtils.getRemoteIp());
        analysisLogDTO.setDate(System.currentTimeMillis());
        log.info(JSON.toJSONString(analysisLogDTO));
    }

    /**
     * Record.
     *
     * @param type the type
     */
    public static void record(String type) {
        AnalysisLogDTO analysisLogDTO = new AnalysisLogDTO();
        analysisLogDTO.setLogType(type);
        analysisLogDTO.setIp(WebUtils.getRemoteIp());
        analysisLogDTO.setDate(System.currentTimeMillis());
        log.info(JSON.toJSONString(analysisLogDTO));
    }

    /**
     * Record.
     *
     * @param str the str
     */
    public static void recordStr(String str) {
        log.info(str);
    }

}
