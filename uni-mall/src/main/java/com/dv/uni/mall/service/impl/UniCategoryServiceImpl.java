package com.dv.uni.mall.service.impl;

import com.dv.uni.commons.service.AbstractService;
import com.dv.uni.mall.entity.UniBrand;
import com.dv.uni.mall.entity.UniCategory;
import com.dv.uni.mall.repository.UniBrandRepository;
import com.dv.uni.mall.repository.UniCategoryRepository;
import com.dv.uni.mall.service.UniBrandService;
import com.dv.uni.mall.service.UniCategoryService;
import org.springframework.stereotype.Service;

/**
 * @Author 张盼
 * @Email zhangpan_soft@163.com
 * @Blog zhangpan_soft
 * @Date 2020/11/27 0027
 */
@Service
public class UniCategoryServiceImpl extends AbstractService<UniCategory,String> implements UniCategoryService {
    private UniCategoryRepository repository;
    public UniCategoryServiceImpl(UniCategoryRepository repository) {
        super(repository);
        this.repository = repository;
    }

    @Override
    public UniCategoryRepository getRepository() {
        return repository;
    }
}
