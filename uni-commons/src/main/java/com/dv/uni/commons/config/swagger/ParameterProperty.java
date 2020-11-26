package com.dv.uni.commons.config.swagger;

import lombok.Data;

/**
 * @Author 张盼
 * @Email zhangpan_soft@163.com
 * @Blog zhangpan_soft
 * @Date 2020/10/22 0022
 */
@Data
public class ParameterProperty {
    private String name;
    private String description;
    private String modelRef;
    private String parameterType;
    private Boolean required;
}
