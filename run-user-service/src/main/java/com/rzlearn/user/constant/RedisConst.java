package com.rzlearn.user.constant;

/**
 * <p>ClassName:RedisConst</p>
 * <p>Description:Redis常量</p>
 *
 * @author JiPeigong
 * @date 2018 -06-14 15:33:28
 */
public interface RedisConst {

    /**
     * 用户信息
     */
    String REDIS_USER_INFO = "USER_INFO_";

    /**
     * 角色对应权限缓存（公开权限）
     */
    String REDIS_ROLE_PERMISSIONS = "ROLE_PERMISSIONS_";
    /**
     * 组织角色缓存（私有权限）
     */
    String REDIS_ORG_ROLE_PERMISSIONS = "ORG_ROLE_PERMISSIONS_";

    /**
     * 启动刷新缓存分布式锁
     */
    String REDIS_CACHE_REFRESH_USER_LOCK_KEY = "CACHE_REFRESH_USER_LOCK";

    /**
     * 已锁
     */
    String REDIS_CACHE_REFRESH_USER_STATUS_LOCK = "lock";

    /**
     * 未锁
     */
    String REDIS_CACHE_REFRESH_USER_STATUS_UNLOCK = "unlock";

    /**
     * 短信验证码
     */
    String SHORT_MSG_CODE = "SHORT_MSG_CODE_";

    /**
     * 修改密码token
     */
    String PASSWORD_TOKEN = "PASSWORD_TOKEN_";

    /**
     * 短信验证码
     */
    String EMAIL_CODE = "EMAIL_CODE_";

    /**
     * 查询统配
     */
    String REDIS_ALL_MATCH = "*";

    /**
     * Redis分隔符
     */
    String REDIS_SPLIT = "_";

}
