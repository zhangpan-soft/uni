package com.dv.uni.commons.entity;

import lombok.Data;

/**
 * @Author 张盼
 * @Email zhangpan_soft@163.com
 * @Blog zhangpan_soft
 * @Date 2020/9/14 0014
 */
@Data
public class SortEntity implements BaseModel {
    private String  property;
    private Boolean isAsc;
}
