package com.dv.uni.sys.repository;

import com.dv.uni.commons.repository.BaseRepository;
import com.dv.uni.sys.entity.SysTemplate;

/**
 * @Author 张盼
 * @Email zhangpan_soft@163.com
 * @Blog zhangpan_soft
 * @Date 2020/10/22 0022
 */
public interface SysTemplateRepository extends BaseRepository<SysTemplate, String> {
    SysTemplate findByCode(String code);
}
