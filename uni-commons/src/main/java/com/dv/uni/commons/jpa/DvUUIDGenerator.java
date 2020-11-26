package com.dv.uni.commons.jpa;

import com.dv.uni.commons.entity.BaseEntity;
import com.dv.uni.commons.utils.StringUtils;
import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.UUIDGenerator;

import java.io.Serializable;

/**
 * @Author 张盼
 * @Email zhangpan_soft@163.com
 * @Blog zhangpan_soft
 * @Date 2020/9/18 0018
 */
public class DvUUIDGenerator extends UUIDGenerator {
    @Override
    public Serializable generate(SharedSessionContractImplementor session, Object object) throws HibernateException {
        BaseEntity baseEntity = (BaseEntity) object;
        if (StringUtils.isEmpty((String) baseEntity.getId())) {
            return StringUtils.uuid();
        } else {
            return baseEntity.getId();
        }
    }
}
