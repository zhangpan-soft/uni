package com.dv.uni.sys.service;

import com.dv.uni.commons.service.BaseService;
import com.dv.uni.sys.entity.SysConfig;

/**
 * @Author 张盼
 * @Email zhangpan_soft@163.com
 * @Blog zhangpan_soft
 * @Date 2020/9/14 0014
 */
public interface SysConfigService extends BaseService<SysConfig,String> {
    SysConfig findByKey(String key);
}
