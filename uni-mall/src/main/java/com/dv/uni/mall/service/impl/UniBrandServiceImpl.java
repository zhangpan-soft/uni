package com.dv.uni.mall.service.impl;

import com.dv.uni.commons.repository.BaseRepository;
import com.dv.uni.commons.service.AbstractService;
import com.dv.uni.mall.entity.UniBrand;
import com.dv.uni.mall.repository.UniBrandRepository;
import com.dv.uni.mall.service.UniBrandService;
import org.springframework.stereotype.Service;

/**
 * @Author 张盼
 * @Email zhangpan_soft@163.com
 * @Blog zhangpan_soft
 * @Date 2020/11/27 0027
 */
@Service
public class UniBrandServiceImpl extends AbstractService<UniBrand,String> implements UniBrandService {
    private UniBrandRepository repository;
    public UniBrandServiceImpl(UniBrandRepository repository) {
        super(repository);
        this.repository = repository;
    }

    @Override
    public UniBrandRepository getRepository() {
        return repository;
    }
}
