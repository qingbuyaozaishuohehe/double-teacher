package com.rzlearn.setting.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * <p>ClassName:PageWebAppConfigDTO</p>
 * <p>Description:分页查询web配置信息实体描述</p>
 *
 * @author wurs
 * @date 2018 -06-19 14:03:00
 */


@Data
@ApiModel("分页查询web配置信息实体描述")
public class PageWebAppConfigDTO {
    /**
     * 当前页码
     */
    @NotNull
    @ApiModelProperty(value = "当前页码", required = true)
    private Integer pageNo;

    /**
     * 分页大小
     */
    @NotNull
    @ApiModelProperty(value = "分页大小", required = true)
    private Integer pageSize;

}
