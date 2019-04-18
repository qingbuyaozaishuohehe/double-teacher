package com.rzlearn.user.common.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.baomidou.mybatisplus.mapper.ISqlInjector;
import com.baomidou.mybatisplus.mapper.LogicSqlInjector;
import com.baomidou.mybatisplus.mapper.MetaObjectHandler;
import com.baomidou.mybatisplus.plugins.PaginationInterceptor;
import com.baomidou.mybatisplus.plugins.PerformanceInterceptor;
import org.springframework.context.annotation.Profile;

/**
 * <p>ClassName:MybatisPlusConfig</p>
 * <p>Description:mybatis-plus 主配置文件</p>
 * @author JiPeigong	
 * @date 2018年5月5日
 * @since
 * @see
 */
@Configuration
@MapperScan("com.rzlearn.**.mapper*")
public class MybatisPlusConfig {

	/**
	 * mybatis-plus SQL执行效率插件【生产环境可以关闭】
	 */
	@Bean
	@Profile({"dev"})
	public PerformanceInterceptor performanceInterceptor() {
		return new PerformanceInterceptor();
	}

	/**
	 * mybatis-plus分页插件<br>
	 * 文档：http://mp.baomidou.com<br>
	 */
	@Bean
	public PaginationInterceptor paginationInterceptor() {
		PaginationInterceptor paginationInterceptor = new PaginationInterceptor();
		// 开启 PageHelper 的支持
		paginationInterceptor.setLocalPage(true);
		return paginationInterceptor;
	}

	/**
	 *  注入公共字段自动填充
	 */
	@Bean
	public MetaObjectHandler metaObjectHandler() {
		return new RunMetaObjectHandler();
	}

	/**
	 * 注入主键生成器
	 */
//	@Bean
//	public IKeyGenerator keyGenerator() {
//		return new H2KeyGenerator();
//	}

	/**
	 * 注入sql注入器 支持逻辑删除等
	 */
	@Bean
	public ISqlInjector sqlInjector() {
		return new LogicSqlInjector();
	}
}
