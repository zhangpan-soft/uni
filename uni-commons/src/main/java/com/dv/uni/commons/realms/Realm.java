package com.dv.uni.commons.realms;

import com.dv.uni.commons.authc.AuthcToken;
import com.dv.uni.commons.entity.BaseEntity;

import java.util.Set;

/**
 * @Author 张盼
 * @Email zhangpan_soft@163.com
 * @Blog zhangpan_soft
 * @Date 2020/9/11 0011
 */
public interface Realm {
    /**
     * 认证
     *
     * @param token
     * @return
     */
    BaseEntity authentication(AuthcToken token);

    /**
     * 授权
     *
     * @param token
     * @return
     */
    Set<String> authorization(AuthcToken token);
}
