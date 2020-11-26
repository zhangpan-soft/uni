package com.dv.uni.commons.utils;

import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * @Author 张盼
 * @Email zhangpan_soft@163.com
 * @Blog zhangpan_soft
 * @Date 2020/10/27 0027
 */
public class ScopeUtils {
    /**
     * 是否有scope
     * @param scopes
     * @param scope
     * @return
     */
    public static boolean contains(String scopes,String scope){
        if (StringUtils.isEmpty(scopes)){
            return false;
        }else {
            return Arrays.asList(scopes.split(",")).contains(scope);
        }
    }

    /**
     * 添加scope
     * @param scopes
     * @param scope
     * @return
     */
    public static String add(String scopes,String scope){
        if (StringUtils.isEmpty(scopes)){
            return scope;
        }else {
            return scopes+","+scope;
        }
    }

    /**
     * 移除scope
     */
    public static String remove(String scopes,String scope){
        if (StringUtils.isEmpty(scopes)){
            return scopes;
        }else {
            return StringUtils.collectionToString(Arrays.asList(scopes.split(",")).stream().filter(s -> {
                return !s.equals(scope);
            }).collect(Collectors.toList()));
        }
    }
}
