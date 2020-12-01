package com.dv.uni.mall.service.impl;

import com.dv.uni.commons.service.AbstractService;
import com.dv.uni.mall.entity.UniBrand;
import com.dv.uni.mall.entity.UniCommodity;
import com.dv.uni.mall.repository.UniBrandRepository;
import com.dv.uni.mall.repository.UniCommodityRepository;
import com.dv.uni.mall.service.UniBrandService;
import com.dv.uni.mall.service.UniCommodityService;
import org.springframework.stereotype.Service;

/**
 * @Author 张盼
 * @Email zhangpan_soft@163.com
 * @Blog zhangpan_soft
 * @Date 2020/11/27 0027
 */
@Service
public class UniCommodityServiceImpl extends AbstractService<UniCommodity,String> implements UniCommodityService {
    private UniCommodityRepository repository;
    public UniCommodityServiceImpl(UniCommodityRepository repository) {
        super(repository);
        this.repository = repository;
    }

    @Override
    public UniCommodityRepository getRepository() {
        return repository;
    }
}
