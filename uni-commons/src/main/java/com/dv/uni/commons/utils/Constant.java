package com.dv.uni.commons.utils;

/**
 * @Author 张盼
 * @Email zhangpan_soft@163.com
 * @Blog zhangpan_soft
 * @Date 2020/11/17 0017
 */
public interface Constant {
    interface Base{

    }

    interface Redis{
        String ORDER_CREATED_PREFIX = "orderCreated:";
        String ORDER_CREATED_CACHE = "orderCreatedCache";
    }

    interface ThreadPool{
        String orderCreated = "com.dv.universal.order#created";
    }
}
