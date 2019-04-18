package com.rzlearn.user.dto;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 * 修改密码请求.
 *
 * @author zhangwb
 */
@Data
@ApiModel("token修改密码")
public class TokenUpdatePasswordDTO {

    @NotBlank
    @Pattern(regexp = "^(13[0-9]|14[579]|15[0-3,5-9]|16[6]|17[0135678]|18[0-9]|19[89])\\d{8}$", message = "管理员手机号不符合规则")
    private String phoneNum;

    @NotNull
    private String token;

    @NotNull
    private String password;
}
