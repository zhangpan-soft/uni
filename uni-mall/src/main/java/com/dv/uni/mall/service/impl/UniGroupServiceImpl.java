package com.dv.uni.mall.service.impl;

import com.dv.uni.commons.service.AbstractService;
import com.dv.uni.mall.entity.UniBrand;
import com.dv.uni.mall.entity.UniGroup;
import com.dv.uni.mall.repository.UniBrandRepository;
import com.dv.uni.mall.repository.UniGroupRepository;
import com.dv.uni.mall.service.UniBrandService;
import com.dv.uni.mall.service.UniGroupService;
import org.springframework.stereotype.Service;

/**
 * @Author 张盼
 * @Email zhangpan_soft@163.com
 * @Blog zhangpan_soft
 * @Date 2020/11/27 0027
 */
@Service
public class UniGroupServiceImpl extends AbstractService<UniGroup,String> implements UniGroupService {
    private UniGroupRepository repository;
    public UniGroupServiceImpl(UniGroupRepository repository) {
        super(repository);
        this.repository = repository;
    }

    @Override
    public UniGroupRepository getRepository() {
        return repository;
    }
}
