package com.dv.uni.commons.utils.ali.oss;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.ObjectMetadata;
import com.aliyun.oss.model.PutObjectRequest;
import com.aliyun.oss.model.PutObjectResult;
import com.dv.uni.commons.enums.Status;
import com.dv.uni.commons.exceptions.BaseException;
import com.dv.uni.commons.utils.StringUtils;
import com.dv.uni.commons.utils.ali.AliUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;

/**
 * @Author 张盼
 * @Email zhangpan_soft@163.com
 * @Blog zhangpan_soft
 * @Date 2020/7/30 0030
 */
@Configuration
@EnableConfigurationProperties(OssProperties.class)
public class OssUtil {
    private static OssProperties properties;

    public static OssProperties getProperties() {
        return properties;
    }

    @Autowired
    public OssUtil(OssProperties properties) {
        OssUtil.properties = properties;
    }

    /**
     * 文件上传
     *
     * @param multipartFile
     * @return
     */
    public static final String upload(MultipartFile multipartFile) {
        String name = multipartFile.getOriginalFilename();
        String[] split = name.split("[.]");
        OSS ossClient = new OSSClientBuilder().build(properties.getEndpoint(), AliUtil.getProperties()
                                                                                      .getAccessKeyId(), AliUtil.getProperties()
                                                                                                                .getAccessKeySecret());
        try (InputStream is = multipartFile.getInputStream()) {
            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentType(multipartFile.getContentType());
            String name_ = StringUtils.uuid() + "." + split[split.length - 1];
            PutObjectRequest putObjectRequest = new PutObjectRequest(properties.getBucketName(), name_, is);
            PutObjectResult objectResult = ossClient.putObject(putObjectRequest);
            return ossUrl(name_);
            //return objectResult.getResponse().getUri();
        } catch (Exception e) {
            e.printStackTrace();
            throw BaseException.of(Status.ALIYUN_OSS_EXCEPTION, e);
        } finally {
            ossClient.shutdown();
        }
    }

    private static final String ossUrl(String name) {
        /*StringBuilder sb = new StringBuilder();
        sb.append(baseUrl).append("/").append(name);
        return sb.toString();*/
        return name;
    }
}
