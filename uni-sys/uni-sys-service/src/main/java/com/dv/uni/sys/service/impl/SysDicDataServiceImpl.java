package com.dv.uni.sys.service.impl;

import com.dv.uni.commons.repository.BaseRepository;
import com.dv.uni.commons.service.AbstractService;
import com.dv.uni.sys.entity.SysDicData;
import com.dv.uni.sys.repository.SysDicDataRepository;
import com.dv.uni.sys.service.SysDicDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author 张盼
 * @Email zhangpan_soft@163.com
 * @Blog zhangpan_soft
 * @Date 2020/9/18 0018
 */
@Service
public class SysDicDataServiceImpl extends AbstractService<SysDicData,String> implements SysDicDataService {

    public SysDicDataServiceImpl(SysDicDataRepository repository) {
        super(repository);
    }

}
