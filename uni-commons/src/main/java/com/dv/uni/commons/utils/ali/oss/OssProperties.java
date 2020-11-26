package com.dv.uni.commons.utils.ali.oss;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @Author 张盼
 * @Email zhangpan_soft@163.com
 * @Blog zhangpan_soft
 * @Date 2020/7/30 0030
 */
@ConfigurationProperties(prefix = "ali.oss")
@Data
public class OssProperties {
    private String bucketName;
    private String endpoint;
    private String baseUrl;
}
