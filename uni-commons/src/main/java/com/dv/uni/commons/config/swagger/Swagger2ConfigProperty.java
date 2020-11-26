package com.dv.uni.commons.config.swagger;

import lombok.Data;

import java.util.List;

/**
 * @Author 张盼
 * @Email zhangpan_soft@163.com
 * @Blog zhangpan_soft
 * @Date 2020/10/22 0022
 */
@Data
public class Swagger2ConfigProperty {
    private String                  groupName;
    private String                  title;
    private String                  termsOfServiceUrl;
    private String                  contact;
    private String                  version;
    private String                  basicPackage;
    private List<ParameterProperty> parameters;
}
