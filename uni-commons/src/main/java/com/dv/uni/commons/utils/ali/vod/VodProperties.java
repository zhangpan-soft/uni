package com.dv.uni.commons.utils.ali.vod;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @Author 张盼
 * @Email zhangpan_soft@163.com
 * @Blog zhangpan_soft
 * @Date 2020/7/30 0030
 */
@Data
@ConfigurationProperties(prefix = "ali.vod")
public class VodProperties {
    private String storageLocation;
    private String regionId;
}
