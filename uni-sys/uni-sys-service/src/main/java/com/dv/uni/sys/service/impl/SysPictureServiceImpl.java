package com.dv.uni.sys.service.impl;

import com.dv.uni.commons.repository.BaseRepository;
import com.dv.uni.commons.service.AbstractService;
import com.dv.uni.sys.entity.SysPicture;
import com.dv.uni.sys.repository.SysPictureRepository;
import com.dv.uni.sys.service.SysPictureService;
import org.springframework.stereotype.Service;

/**
 * @Author 张盼
 * @Email zhangpan_soft@163.com
 * @Blog zhangpan_soft
 * @Date 2020/10/23 0023
 */
@Service
public class SysPictureServiceImpl extends AbstractService<SysPicture,String> implements SysPictureService {

    public SysPictureServiceImpl(SysPictureRepository repository) {
        super(repository);
    }
}
