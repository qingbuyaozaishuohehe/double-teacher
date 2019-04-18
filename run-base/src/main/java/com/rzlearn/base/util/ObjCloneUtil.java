package com.rzlearn.base.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.lang.reflect.Field;

/**
 * <p>ClassName:ObjCloneUtil</p>
 * <p>Description:对象属性克隆</p>
 *
 * @author JiPeigong
 * @date 2019-01-24 11:00
 */
public class ObjCloneUtil {

    /**
     * Obj clone t.
     * 对象属性克隆
     *
     * @param <T>            the type parameter
     * @param sourceObj      the source obj
     * @param targetObjClass the target obj class
     * @return the t
     * @throws IllegalAccessException the illegal access exception
     * @throws InstantiationException the instantiation exception
     */
    public static <T> T objClone(Object sourceObj, Class<T> targetObjClass){
        JSONObject sourceJsonObj = (JSONObject) JSON.toJSON(sourceObj);
        JSONObject targetJsonObj = new JSONObject();

        Field[] fields = targetObjClass.getDeclaredFields();
        for (Field field : fields) {
            String key = field.getName();
            if (sourceJsonObj.containsKey(key)) {
                targetJsonObj.put(key, sourceJsonObj.get(key));
            } else {
                targetJsonObj.put(key, null);
            }
        }
        return JSON.parseObject(targetJsonObj.toJSONString(), targetObjClass);
    }
}
