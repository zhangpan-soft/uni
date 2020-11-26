package com.dv.uni.commons.authc;

import java.io.Serializable;

/**
 * @Author 张盼
 * @Email zhangpan_soft@163.com
 * @Blog zhangpan_soft
 * @Date 2020/9/11 0011
 */
public interface AuthcToken extends Serializable {
    String getName();

    Object principal();

    String getScope();
}
