package com.dv.uni.commons.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;

import java.util.Map;

/**
 * @Author 张盼
 * @Email zhangpan_soft@163.com
 * @Blog zhangpan_soft
 * @Date 2020/10/26 0026
 */
public class ObjectUtils {
    public static <K, V> Map<K, V> obj2Map(Object target, Class<K> kClass, Class<V> vClass) {
        Assert.notNull(target,"target为空");
        String json = JSON.toJSONString(target);
        return JSON.parseObject(json, new TypeReference<Map<K, V>>() {
        });
    }

    public static Map<String, Object> obj2Map(Object target) {
        Assert.notNull(target,"target为空");
        return obj2Map(target, String.class, Object.class);
    }

    public static <T,K,V> T map2Obj(Map<K,V> target,Class<T> clazz){
        Assert.notNull(target,"target为空");
        return JSON.parseObject(JSON.toJSONString(target), clazz);
    }
}
