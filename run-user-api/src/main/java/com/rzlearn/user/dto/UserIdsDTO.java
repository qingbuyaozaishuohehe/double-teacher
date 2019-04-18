package com.rzlearn.user.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * <p>ClassName:UserIdsDTO</p>
 * <p>Description:</p>
 *
 * @author JiPeigong
 * @date 2018-06-21 15:55
 **/
@Data
@ApiModel("用户id集合")
public class UserIdsDTO {

    /**
     * 用户名集合
     */
    @ApiModelProperty(value = "用户id集合", required = true)
    @NotNull
    private List<Long> userIds;
}
