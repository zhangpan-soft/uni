package com.dv.uni.commons.utils.ali.sms;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @Author 张盼
 * @Email zhangpan_soft@163.com
 * @Blog zhangpan_soft
 * @Date 2020/7/30 0030
 */
@ConfigurationProperties(prefix = "ali.sms")
@Data
public class SmsProperties {
    private String               signName;
    private String               version;
    private String               regionId;
    private String               sendUrl;
}
