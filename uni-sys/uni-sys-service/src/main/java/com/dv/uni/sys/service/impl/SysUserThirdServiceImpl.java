package com.dv.uni.sys.service.impl;

import com.dv.uni.commons.repository.BaseRepository;
import com.dv.uni.commons.service.AbstractService;
import com.dv.uni.sys.entity.SysUserThird;
import com.dv.uni.sys.repository.SysUserRepository;
import com.dv.uni.sys.repository.SysUserThirdRepository;
import com.dv.uni.sys.service.SysUserThirdService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author 张盼
 * @Email zhangpan_soft@163.com
 * @Blog zhangpan_soft
 * @Date 2020/9/14 0014
 */
@Service
public class SysUserThirdServiceImpl extends AbstractService<SysUserThird,String> implements SysUserThirdService {
    private SysUserThirdRepository repository;
    public SysUserThirdServiceImpl(SysUserThirdRepository repository) {
        super(repository);
        this.repository = repository;
    }

    @Override
    public SysUserThirdRepository getRepository() {
        return repository;
    }

    @Override
    public String findUserIdByUnionId(String unionid) {
        return getRepository().findUserIdByUnionId(unionid);
    }

    @Override
    public String findUserIdByOpenId(String openid) {
        return getRepository().findUserIdByOpenId(openid);
    }

    @Override
    public SysUserThird findByOpenId(String openid) {
        return this.getRepository().findByOpenId(openid);
    }
}
