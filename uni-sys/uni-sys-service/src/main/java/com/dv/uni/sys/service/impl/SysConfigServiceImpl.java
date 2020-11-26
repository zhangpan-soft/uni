package com.dv.uni.sys.service.impl;

import com.dv.uni.commons.repository.BaseRepository;
import com.dv.uni.commons.service.AbstractService;
import com.dv.uni.sys.entity.SysConfig;
import com.dv.uni.sys.repository.SysConfigRepository;
import com.dv.uni.sys.service.SysConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author 张盼
 * @Email zhangpan_soft@163.com
 * @Blog zhangpan_soft
 * @Date 2020/9/14 0014
 */
@Service
public class SysConfigServiceImpl extends AbstractService<SysConfig,String> implements SysConfigService {
    private SysConfigRepository repository;
    public SysConfigServiceImpl(SysConfigRepository repository) {
        super(repository);
        this.repository = repository;
    }

    @Override
    public SysConfigRepository getRepository() {
        return repository;
    }

    @Override
    public SysConfig findByKey(String key) {
        return getRepository().findByKey(key);
    }
}
