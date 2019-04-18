package com.rzlearn.user.startup;

import com.rzlearn.user.common.util.RedisUtil;
import com.rzlearn.user.constant.RedisConst;
import com.rzlearn.user.manager.UserManager;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * <p>ClassName:SpringStartupRunner</p>
 * <p>Description: 初始化基础类</p>
 *
 * @author JIPEIGONG
 * @date 2018年5月23日
 * @see
 * @since
 */
@Component
@Slf4j
public class SpringStartupRunner implements CommandLineRunner {

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private UserManager userManager;

    @Override
    public void run(String... arg0) {
        Object cacheLock = redisUtil.get(RedisConst.REDIS_CACHE_REFRESH_USER_LOCK_KEY);
        if (cacheLock == null || !StringUtils.equals(cacheLock.toString(), RedisConst.REDIS_CACHE_REFRESH_USER_STATUS_LOCK)) {
            redisUtil.set(RedisConst.REDIS_CACHE_REFRESH_USER_LOCK_KEY, RedisConst.REDIS_CACHE_REFRESH_USER_STATUS_LOCK);
            // 启动加载用户、角色、组织、权限信息缓存
            log.info("开始初始化所有用户、角色、组织、权限信息缓存");
            userManager.initAllPermission();
            log.info("初始化用户、角色、组织、权限信息缓存完成");
            redisUtil.set(RedisConst.REDIS_CACHE_REFRESH_USER_LOCK_KEY, RedisConst.REDIS_CACHE_REFRESH_USER_STATUS_UNLOCK);
        }
    }

}
