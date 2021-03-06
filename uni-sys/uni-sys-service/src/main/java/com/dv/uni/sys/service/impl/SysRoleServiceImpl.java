package com.dv.uni.sys.service.impl;

import com.dv.uni.commons.repository.BaseRepository;
import com.dv.uni.commons.service.AbstractService;
import com.dv.uni.sys.entity.SysRole;
import com.dv.uni.sys.repository.SysRoleRepository;
import com.dv.uni.sys.service.SysRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author 张盼
 * @Email zhangpan_soft@163.com
 * @Blog zhangpan_soft
 * @Date 2020/9/14 0014
 */
@Service
public class SysRoleServiceImpl extends AbstractService<SysRole,String> implements SysRoleService {

    public SysRoleServiceImpl(SysRoleRepository repository) {
        super(repository);
    }
}
