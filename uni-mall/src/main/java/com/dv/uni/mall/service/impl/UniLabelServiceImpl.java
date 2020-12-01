package com.dv.uni.mall.service.impl;

import com.dv.uni.commons.service.AbstractService;
import com.dv.uni.mall.entity.UniBrand;
import com.dv.uni.mall.entity.UniLabel;
import com.dv.uni.mall.repository.UniBrandRepository;
import com.dv.uni.mall.repository.UniLabelRepository;
import com.dv.uni.mall.service.UniBrandService;
import com.dv.uni.mall.service.UniLabelService;
import org.springframework.stereotype.Service;

/**
 * @Author 张盼
 * @Email zhangpan_soft@163.com
 * @Blog zhangpan_soft
 * @Date 2020/11/27 0027
 */
@Service
public class UniLabelServiceImpl extends AbstractService<UniLabel,String> implements UniLabelService {
    private UniLabelRepository repository;
    public UniLabelServiceImpl(UniLabelRepository repository) {
        super(repository);
        this.repository = repository;
    }

    @Override
    public UniLabelRepository getRepository() {
        return repository;
    }
}
