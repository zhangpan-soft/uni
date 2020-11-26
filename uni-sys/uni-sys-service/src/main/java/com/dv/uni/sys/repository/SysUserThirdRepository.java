package com.dv.uni.sys.repository;

import com.dv.uni.commons.repository.BaseRepository;
import com.dv.uni.sys.entity.SysUserThird;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * @Author 张盼
 * @Email zhangpan_soft@163.com
 * @Blog zhangpan_soft
 * @Date 2020/9/14 0014
 */
@Repository
public interface SysUserThirdRepository extends BaseRepository<SysUserThird, String> {

    @Query(nativeQuery = true, value = "SELECT user_id FROM sys_user_third WHERE union_id=?1 GROUP BY user_id")
    String findUserIdByUnionId(String unionid);

    @Query(nativeQuery = true, value = "SELECT user_id FROM sys_user_third WHERE open_id=?1 ")
    String findUserIdByOpenId(String openid);

    SysUserThird findByOpenId(String openId);
}
