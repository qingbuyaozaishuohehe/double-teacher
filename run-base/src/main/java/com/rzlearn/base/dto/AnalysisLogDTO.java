package com.rzlearn.base.dto;

import lombok.Data;

/**
 * 日志分析模型
 * @author JiPeigong
 */
@Data
public class AnalysisLogDTO {
    /**
     * 类别
     */
    private String logType;
    /**
     * 用户名
     */
    private String userName;
    /**
     * 用户id
     */
    private Long userId;
    /**
     * ip
     */
    private String ip;
    /**
     * 调用类名
     */
    private String clazz;
    /**
     * 调用方法名
     */
    private String method;
    /**
     * 调用参数
     */
    private String param;
    /**
     * 操作时间
     */
    private Long date;
    /**
     * 耗时
     */
    private Long cost;
    /**
     * 内容
     */
    private String content;
    /**
     * uri
     */
    private String uri;
}
