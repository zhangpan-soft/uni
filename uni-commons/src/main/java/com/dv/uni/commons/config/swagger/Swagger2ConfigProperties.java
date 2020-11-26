package com.dv.uni.commons.config.swagger;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

/**
 * @Author 张盼
 * @Email zhangpan_soft@163.com
 * @Blog zhangpan_soft
 * @Date 2020/10/22 0022
 */
@Data
@ConfigurationProperties(prefix = "swagger2")
public class Swagger2ConfigProperties {
    private List<Swagger2ConfigProperty> properties;
}
