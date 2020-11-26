package com.dv.uni.sys.service;

import com.dv.uni.commons.service.BaseService;
import com.dv.uni.sys.entity.SysUser;

/**
 * @Author 张盼
 * @Email zhangpan_soft@163.com
 * @Blog zhangpan_soft
 * @Date 2020/9/14 0014
 */
public interface SysUserService extends BaseService<SysUser, String> {
    SysUser findByUsername(String username);

    SysUser findByPhone(String phone);

    SysUser findByEmail(String username);
}
