package com.dv.uni.mall.service.impl;

import com.dv.uni.commons.service.AbstractService;
import com.dv.uni.mall.entity.UniBrand;
import com.dv.uni.mall.entity.UniUsp;
import com.dv.uni.mall.repository.UniBrandRepository;
import com.dv.uni.mall.repository.UniUspRepository;
import com.dv.uni.mall.service.UniBrandService;
import com.dv.uni.mall.service.UniUspService;
import org.springframework.stereotype.Service;

/**
 * @Author 张盼
 * @Email zhangpan_soft@163.com
 * @Blog zhangpan_soft
 * @Date 2020/11/27 0027
 */
@Service
public class UniUspServiceImpl extends AbstractService<UniUsp,String> implements UniUspService {
    private UniUspRepository repository;
    public UniUspServiceImpl(UniUspRepository repository) {
        super(repository);
        this.repository = repository;
    }

    @Override
    public UniUspRepository getRepository() {
        return repository;
    }
}
