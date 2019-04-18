package com.rzlearn.user.common.thirdparty;

import lombok.Data;

/**
 * <p>ClassName:ThirdPartyUser</p>
 * <p>Description:第三方用户实体类</p>
 * @author JiPeigong
 * @date 2018-08-11 17:06:57
 **/
@Data
public class ThirdPartyUser {

    /**
     * 用户
     */
    private String account;
    /**
     * 用户昵称
     */
    private String userName;
    /**
     * 用户头像地址
     */
    private String avatarUrl;
    /**
     * 用户性别
     */
    private String gender;
    /**
     * 用户认证
     */
    private String token;
    /**
     * 用户第三方id
     */
    private String openid;
    /**
     * 用户类型
     */
    private String provider;
    /**
     * 用户id
     */
    private Integer userId;

}
