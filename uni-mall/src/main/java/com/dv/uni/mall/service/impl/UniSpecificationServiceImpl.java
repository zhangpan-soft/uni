package com.dv.uni.mall.service.impl;

import com.dv.uni.commons.service.AbstractService;
import com.dv.uni.mall.entity.UniBrand;
import com.dv.uni.mall.entity.UniSpecification;
import com.dv.uni.mall.repository.UniBrandRepository;
import com.dv.uni.mall.repository.UniSpecificationRepository;
import com.dv.uni.mall.service.UniBrandService;
import com.dv.uni.mall.service.UniSpecificationService;
import org.springframework.stereotype.Service;

/**
 * @Author 张盼
 * @Email zhangpan_soft@163.com
 * @Blog zhangpan_soft
 * @Date 2020/11/27 0027
 */
@Service
public class UniSpecificationServiceImpl extends AbstractService<UniSpecification,String> implements
        UniSpecificationService {
    private UniSpecificationRepository repository;
    public UniSpecificationServiceImpl(UniSpecificationRepository repository) {
        super(repository);
        this.repository = repository;
    }

    @Override
    public UniSpecificationRepository getRepository() {
        return repository;
    }
}
