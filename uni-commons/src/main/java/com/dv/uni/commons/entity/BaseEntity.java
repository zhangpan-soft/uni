package com.dv.uni.commons.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;


/**
 * @Author 张盼
 * @Email zhangpan_soft@163.com
 * @Blog zhangpan_soft
 * @Date 2020/9/10 0010
 */
public interface BaseEntity<ID extends Serializable> extends BaseModel {
    ID getId();

    void setId(ID id);

    Date getCreateDate();

    void setCreateDate(Date createDate);

    Date getUpdateDate();

    void setUpdateDate(Date updateDate);

    ID getCreateBy();

    void setCreateBy(ID createBy);

    ID getUpdateBy();

    void setUpdateBy(ID updateBy);

    List<SortEntity> getSortBy();

    void setSortBy(List<SortEntity> sortBy);

    String getScope();

    void setScope(String scope);

    Integer getDeleted();

    void setDeleted(Integer deleted);
}
