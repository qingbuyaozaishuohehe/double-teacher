package com.rzlearn.setting.common.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * <p>ClassName:RedisUtil</p>
 * <p>Description:redis 工具类</p>
 * @author JiPeigong	
 * @date 2018年5月10日
 * @since
 * @see
 */
@SuppressWarnings("unchecked")
@Component
public class RedisUtil {

	@SuppressWarnings("rawtypes")
	@Autowired
	private RedisTemplate redisTemplate;

	/**
	 * 批量删除对应的value
	 * 
	 * @param keys
	 */
	public void remove(final String... keys) {
		for (String key : keys) {
			remove(key);
		}
	}

	/**
	 * 批量删除key（大数据量禁用）
	 * 
	 * @param pattern
	 */
	public void removePattern(final String pattern) {
		Set<Serializable> keys = redisTemplate.keys(pattern);
		if (keys.size() > 0) {
			redisTemplate.delete(keys);
		}
	}

	/**
	 * 删除对应的value
	 * 
	 * @param key
	 */
	public void remove(final String key) {
		if (exists(key)) {
			redisTemplate.delete(key);
		}
	}

	/**
	 * 判断缓存中是否有对应的value
	 * 
	 * @param key
	 * @return
	 */
	public boolean exists(final String key) {
		return redisTemplate.hasKey(key);
	}

	/**
	 * 读取缓存
	 * 
	 * @param key
	 * @return
	 */
	public Object get(final String key) {
		Object result = null;
		result = redisTemplate.opsForValue().get(key);
		return result;
	}

	/**
	 * 读取缓存
	 * 
	 * @param key
	 * @return
	 */
	public <T> T get(final String key, Class<T> clazz) {
		T result = null;
		result = (T) redisTemplate.opsForValue().get(key);
		return result;
	}

	/**
	 * 条件过滤（大数据量禁用）
	 * @param pattern
	 * @return
	 */
	/*public List<String> getByPattern(String pattern) {
		List<String> results = new ArrayList<String>();
		Set<String> s = redisTemplate.keys(pattern);
		Iterator<String> it = s.iterator();
		while (it.hasNext()) {
			String key = (String) it.next();

			results.add(key);
		}

		return results;
	}*/

	/**
	 * 根据dbIndex写入缓存
	 * 
	 * @param key
	 * @param value
	 * @return
	 */
	public boolean set(final String key, Object value, int dbIndex) {
		boolean result = false;
		try {
			JedisConnectionFactory jedis = (JedisConnectionFactory) redisTemplate.getConnectionFactory();
			jedis.setDatabase(dbIndex);
			ValueOperations<Serializable, Object> operations = redisTemplate.opsForValue();
			operations.set(key, value);
			result = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	/**
	 * 写入缓存
	 * 
	 * @param key
	 * @param value
	 * @return
	 */
	public boolean set(final String key, Object value) {
		boolean result = false;
		try {
			ValueOperations<Serializable, Object> operations = redisTemplate.opsForValue();
			operations.set(key, value);
			result = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 写入缓存
	 * @param key
	 * @param value
	 * @param expireTime 单位秒（TimeUnit.SECONDS）
	 * @return
	 */
	public boolean set(final String key, Object value, Long expireTime) {
		boolean result = false;
		try {
			ValueOperations<Serializable, Object> operations = redisTemplate.opsForValue();
			operations.set(key, value);
			redisTemplate.expire(key, expireTime, TimeUnit.SECONDS);
			result = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	/**
	 * 写入缓存
	 * @param key
	 * @param value
	 * @param timeUnit TimeUnit.SECONDS
	 * @param expireTime 根据上述时间单位的数量
	 * @return
	 */
	public boolean set(final String key, Object value, TimeUnit timeUnit, Long expireTime) {
		boolean result = false;
		try {
			ValueOperations<Serializable, Object> operations = redisTemplate.opsForValue();
			operations.set(key, value);
			redisTemplate.expire(key, expireTime, timeUnit);
			result = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
}