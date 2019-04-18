package com.rzlearn.user.common.config;

import com.baomidou.mybatisplus.mapper.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;

import java.sql.Timestamp;

/**
 * <p>ClassName:RunMetaObjectHandler</p>
 * <p>Description:mybatis-plus 注入公共字段自动填充,任选注入方式即可</p>
 * @author JiPeigong	
 * @date 2018年5月5日
 * @since
 * @see
 */
public class RunMetaObjectHandler extends MetaObjectHandler {

	@Override
	public void insertFill(MetaObject metaObject) {
		Timestamp currentTimesmap = new Timestamp(System.currentTimeMillis());
		setFieldValByName("gmtCreate", currentTimesmap, metaObject);
		setFieldValByName("gmtModified", currentTimesmap, metaObject);
	}

    @Override
    public void updateFill(MetaObject metaObject) {
    	setFieldValByName("gmtModified", new Timestamp(System.currentTimeMillis()), metaObject);
    }
}