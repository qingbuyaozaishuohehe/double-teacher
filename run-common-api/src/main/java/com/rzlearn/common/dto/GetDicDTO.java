package com.rzlearn.common.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * <p>ClassName:SaveDicDTO</p>
 * <p>Description:</p>
 *
 * @author JiPeigong
 * @date 2018 -06-14 15:34:18
 */
@Data
@ApiModel("查询字典实体描述")
public class GetDicDTO {
    /**
     * 字典id
     */
    @ApiModelProperty(value="字典id")
    private Long id;

    /**
     * 字典code
     */
    @ApiModelProperty(value="字典code")
    private String code;

    /**
     * 字典名称
     */
    @ApiModelProperty(value="字典名称")
    private String name;

    /**
     * 父字典类别
     */
    @ApiModelProperty(value="父字典类别")
    private String parentType;

    /**
     * 父字典code
     */
    @ApiModelProperty(value="父字典code")
    private String parentCode;
}
