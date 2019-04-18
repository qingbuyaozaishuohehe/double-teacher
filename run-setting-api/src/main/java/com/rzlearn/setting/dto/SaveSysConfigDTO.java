package com.rzlearn.setting.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * <p>ClassName:SaveSysConfigDTO</p>
 * <p>Description:保存系统配置实体</p>
 *
 * @author JiPeigong
 * @date 2018 -06-14 15:34:18
 */
@Data
@ApiModel("保存系统配置实体")
public class SaveSysConfigDTO {

    /**
     * key
     */
    @NotNull
    @ApiModelProperty(value = "key", required = true)
    private String key;

    /**
     * value
     */
    @NotNull
    @ApiModelProperty(value = "value", required = true)
    private String value;

}
