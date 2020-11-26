package com.dv.uni.commons.utils.ali;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @Author 张盼
 * @Email zhangpan_soft@163.com
 * @Blog zhangpan_soft
 * @Date 2020/7/30 0030
 */
@Data
@ConfigurationProperties(prefix = "ali")
public class AliProperties {
    private String accessKeySecret;
    private String accessKeyId;
    private String signatureMethod;
    private String signatureVersion;
    private String format;
}
