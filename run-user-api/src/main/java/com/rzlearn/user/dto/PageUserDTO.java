package com.rzlearn.user.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * <p>ClassName:PageUserDTO</p>
 * <p>Description:分页查询用户实体描述</p>
 *
 * @author wurs
 * @date 2018 -06-19 14:03:00
 */


@Data
@ApiModel("分页查询用户实体描述")
public class PageUserDTO {
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

	/**
	 * 应用id
	 */
	@ApiModelProperty(value = "应用id")
	private Long domainId;

	/**
	 * 用户名
	 */
	@ApiModelProperty(value = "用户名")
	private String userName;

    /**
     * 用户角色集合
     */
    @ApiModelProperty(value = "用户角色集合")
	private List<String> roleCodes;

	/**
	 * 手机号码
	 */
	@ApiModelProperty(value = "手机号码")
	private String phoneNum;

	/**
	 * 姓名
	 */
	@ApiModelProperty(value = "姓名")
	private String name;

	/**
	 * 显示停用账号(true:停用)
	 */
	@ApiModelProperty(value = "显示停用账号(true:停用)")
	private Boolean disused;

	/**
	 * 排序字段
	 */
	@ApiModelProperty(value = "排序字段")
	private String sortField;

	/**
	 * 排序方式
	 */
	@ApiModelProperty(value = "排序方式（asc：正序，desc：倒序）")
	private String orderBy;

}
