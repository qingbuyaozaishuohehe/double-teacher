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
@ApiModel("新增字典实体描述")
public class SaveDicDTO {

    /**
     * 字典类型
     */
    @ApiModelProperty(value="字典类型",required = true)
    @NotNull
    private String type;

    /**
     * 字典code
     */
    @ApiModelProperty(value="字典code",required = true)
    @NotNull
    private String code;

    /**
     * 字典名称
     */
    @ApiModelProperty(value="字典名称",required = true)
    @NotNull
    private String name;

    /**
     * 字典语言
     */
    @ApiModelProperty(value="字典语言",required = true)
    @NotNull
    private String language;

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

    /**
     * 排序
     */
    @ApiModelProperty(value="排序")
    private Integer sortNum;

    /**
     * 描述
     */
    @ApiModelProperty(value="描述")
    private String description;

}
