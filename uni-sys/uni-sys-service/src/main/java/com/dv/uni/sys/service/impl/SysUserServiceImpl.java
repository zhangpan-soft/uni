package com.dv.uni.sys.service.impl;

import com.dv.uni.commons.entity.BaseEntity;
import com.dv.uni.commons.repository.BaseRepository;
import com.dv.uni.commons.service.AbstractService;
import com.dv.uni.commons.utils.SecurityUtils;
import com.dv.uni.commons.utils.StringUtils;
import com.dv.uni.commons.utils.md5.MD5Util;
import com.dv.uni.sys.entity.SysUser;
import com.dv.uni.sys.repository.SysUserRepository;
import com.dv.uni.sys.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotEmpty;
import java.util.Date;
import java.util.List;

/**
 * @Author 张盼
 * @Email zhangpan_soft@163.com
 * @Blog zhangpan_soft
 * @Date 2020/9/14 0014
 */
@Service
public class SysUserServiceImpl extends AbstractService<SysUser,String> implements SysUserService {
    private SysUserRepository repository;

    public SysUserServiceImpl(SysUserRepository repository) {
        super(repository);
        this.repository = repository;
    }

    @Override
    public SysUserRepository getRepository() {
        return repository;
    }

    @Override
    public SysUser findByUsername(String username) {
        return getRepository().findByUsername(username);
    }

    @Override
    public SysUser findByPhone(String phone) {
        return getRepository().findByPhone(phone);
    }

    @Override
    public SysUser findByEmail(String email) {
        return getRepository().findByEmail(email);
    }

    @Override
    public SysUser insert(SysUser entity) {
        entity.setId(StringUtils.uuid());
        entity.setCreateDate(new Date());
        entity.setUpdateDate(new Date());
        BaseEntity authentication = (BaseEntity) SecurityUtils.getClaim(SecurityUtils.get(), "authentication");
        if (authentication != null) {
            entity.setUpdateBy((String) authentication.getId());
            entity.setCreateBy((String) authentication.getId());
        }
        if (!StringUtils.isEmpty(entity.getUsername())) {
            if (StringUtils.isEmpty(entity.getPassword())) {
                entity.setPassword("123456");
            }
        }
        if (!StringUtils.isEmpty(entity.getPassword())){
            entity.setSalt(StringUtils.salt(8));
            entity.setPassword(MD5Util.md5(entity.getPassword(), entity.getSalt()));
        }
        return getRepository().saveAndFlush(entity);
    }

    @Override
    public List<SysUser> updates(@NotEmpty List<SysUser> collect) {
        BaseEntity authentication = (BaseEntity) SecurityUtils.getClaim(SecurityUtils.get(), "authentication");
        collect.forEach(entity->{
            if (authentication != null) {
                entity.setUpdateBy((String) authentication.getId());
            }
            entity.setUpdateDate(new Date());
            if (!StringUtils.isEmpty(entity.getPassword())){
                entity.setSalt(StringUtils.salt(8));
                entity.setPassword(MD5Util.md5(entity.getPassword(), entity.getSalt()));
            }
        });
        return getRepository().saveAll(collect);
    }

    @Override
    public SysUser update(SysUser entity) {
        BaseEntity authentication = (BaseEntity) SecurityUtils.getClaim(SecurityUtils.get(), "authentication");
        if (authentication != null) {
            entity.setUpdateBy((String) authentication.getId());
        }
        entity.setUpdateDate(new Date());
        if (!StringUtils.isEmpty(entity.getPassword())){
            entity.setSalt(StringUtils.salt(8));
            entity.setPassword(MD5Util.md5(entity.getPassword(), entity.getSalt()));
        }
        return getRepository().saveAndFlush(entity);
    }
}
