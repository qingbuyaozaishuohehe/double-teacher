package com.rzlearn.common.startup;

import com.rzlearn.common.constant.RedisConst;
import com.rzlearn.common.manager.DicManager;
import com.rzlearn.common.util.RedisUtil;
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
    private DicManager manager;

    @Override
    public void run(String... arg0) throws Exception {
        Object cacheLock = redisUtil.get(RedisConst.REDIS_CACHE_REFRESH_LOCK_KEY);
        if (cacheLock == null || !StringUtils.equals(cacheLock.toString(), RedisConst.REDIS_CACHE_REFRESH_STATUS_LOCK)) {
            redisUtil.set(RedisConst.REDIS_CACHE_REFRESH_LOCK_KEY, RedisConst.REDIS_CACHE_REFRESH_STATUS_LOCK);
            // 启动加载所有字典
            log.info("开始初始化所有字典信息");
            manager.initAllDic();
            log.info("初始化字典信息完成");
            redisUtil.set(RedisConst.REDIS_CACHE_REFRESH_LOCK_KEY, RedisConst.REDIS_CACHE_REFRESH_STATUS_UNLOCK);
        }
    }

}
