package com.dv.uni.sys.repository;

import com.dv.uni.commons.repository.BaseRepository;
import com.dv.uni.sys.entity.SysUser;
import org.springframework.stereotype.Repository;

/**
 * @Author 张盼
 * @Email zhangpan_soft@163.com
 * @Blog zhangpan_soft
 * @Date 2020/9/14 0014
 */
@Repository
public interface SysUserRepository extends BaseRepository<SysUser, String> {

    SysUser findByUsername(String username);

    SysUser findByPhone(String phone);

    SysUser findByEmail(String email);
}
