package com.dv.uni.sys.service.impl;

import com.dv.uni.commons.repository.BaseRepository;
import com.dv.uni.commons.service.AbstractService;
import com.dv.uni.sys.entity.SysDicType;
import com.dv.uni.sys.repository.SysDicTypeRepository;
import com.dv.uni.sys.service.SysDicTypeService;
import org.springframework.stereotype.Service;

/**
 * @Author 张盼
 * @Email zhangpan_soft@163.com
 * @Blog zhangpan_soft
 * @Date 2020/9/18 0018
 */
@Service
public class SysDicTypeServiceImpl extends AbstractService<SysDicType,String> implements SysDicTypeService {

    public SysDicTypeServiceImpl(SysDicTypeRepository repository) {
        super(repository);
    }
}
