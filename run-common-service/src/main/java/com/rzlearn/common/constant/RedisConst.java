package com.rzlearn.common.constant;

/**
 * <p>ClassName:RedisConst</p>
 * <p>Description:Redis常量</p>
 * @author JiPeigong
 * @date 2018-06-14 15:33:07
 **/
public interface RedisConst {

    /**
     * 字典缓存
     */
    public static final String REDIS_DIC = "DIC_";

    /**
     * 启动刷新缓存分布式锁
     */
    public static final String REDIS_CACHE_REFRESH_LOCK_KEY = "CACHE_REFRESH_LOCK";

    /**
     * 已锁
     */
    public static final String REDIS_CACHE_REFRESH_STATUS_LOCK = "lock";

    /**
     * 未锁
     */
    public static final String REDIS_CACHE_REFRESH_STATUS_UNLOCK = "unlock";
}
