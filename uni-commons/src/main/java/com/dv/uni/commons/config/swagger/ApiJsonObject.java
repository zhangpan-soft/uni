package com.dv.uni.commons.config.swagger;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Author 张盼
 * @Email zhangpan_soft@163.com
 * @Blog zhangpan_soft
 * @Date 2020/8/20 0020
 */
@Target({
        ElementType.PARAMETER,
        ElementType.FIELD,
        ElementType.METHOD
})
@Retention(RetentionPolicy.RUNTIME)
@Deprecated
public @interface ApiJsonObject {
    ApiJsonProperty[] value(); //对象属性值

    String name();  //对象名称
}
