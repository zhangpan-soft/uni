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
@Target(ElementType.ANNOTATION_TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Deprecated
public @interface ApiJsonProperty {

    String key();  //key

    String example() default "";

    String type() default "string";  //支持string 和 int

    String description() default "";

}