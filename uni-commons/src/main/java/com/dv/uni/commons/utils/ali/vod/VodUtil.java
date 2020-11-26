package com.dv.uni.commons.utils.ali.vod;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.vod.model.v20170321.*;
import com.dv.uni.commons.enums.Status;
import com.dv.uni.commons.exceptions.BaseException;
import com.dv.uni.commons.utils.ali.AliUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @Author 张盼
 * @Email zhangpan_soft@163.com
 * @Blog zhangpan_soft
 * @Date 2020/7/30 0030
 */
@Configuration
@EnableConfigurationProperties(VodProperties.class)
public class VodUtil {
    private static VodProperties    properties;
    private static DefaultAcsClient client;

    public static VodProperties getProperties() {
        return properties;
    }

    @Autowired
    public VodUtil(VodProperties properties, AliUtil aliUtil) {
        VodUtil.properties = properties;
        try {
            client = initVodClient(AliUtil.getProperties()
                                          .getAccessKeyId(), AliUtil.getProperties()
                                                                    .getAccessKeySecret());
        } catch (ClientException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取视频播放地址
     *
     * @param id
     * @return
     */
    public static final GetPlayInfoResponse getPlayInfo(String id) {
        GetPlayInfoRequest request = new GetPlayInfoRequest();
        request.setVideoId(id);
        try {
            return client.getAcsResponse(request);
        } catch (ClientException e) {
            e.printStackTrace();
            throw BaseException.of(Status.ALIYUN_VOD_EXCEPTION, e);
        }
    }

    /**
     * 获取视频播放凭证
     *
     * @param id
     * @return
     */
    public static final GetVideoPlayAuthResponse getVideoPlayAuth(String id) {
        GetVideoPlayAuthRequest request = new GetVideoPlayAuthRequest();
        request.setVideoId(id);

        try {
            return client.getAcsResponse(request);
        } catch (ClientException e) {
            e.printStackTrace();
            throw BaseException.of(Status.ALIYUN_VOD_EXCEPTION, e);
        }
    }

    /**
     * 获取视频上传凭证
     *
     * @param title    视频标题
     * @param filename 视频名称,带后缀
     * @return
     */
    public static final CreateUploadVideoResponse createUploadVideo(String title, String filename) {
        CreateUploadVideoRequest request = new CreateUploadVideoRequest();
        request.setTitle(title);
        request.setFileName(filename);
        request.setStorageLocation(properties.getStorageLocation());
        try {
            return client.getAcsResponse(request);
        } catch (ClientException e) {
            e.printStackTrace();
            throw BaseException.of(Status.ALIYUN_VOD_EXCEPTION, e);
        }
    }

    /**
     * 刷新视频上传凭证
     *
     * @param id 视频id
     * @return
     */
    public static final RefreshUploadVideoResponse refreshUploadVideo(String id) {
        RefreshUploadVideoRequest request = new RefreshUploadVideoRequest();
        request.setVideoId(id);
        try {
            return client.getAcsResponse(request);
        } catch (ClientException e) {
            e.printStackTrace();
            throw BaseException.of(Status.ALIYUN_VOD_EXCEPTION, e);
        }
    }


    public static final DefaultAcsClient initVodClient(String accessKeyId, String accessKeySecret) throws ClientException {
        String regionId = properties.getRegionId();  // 点播服务接入区域
        DefaultProfile profile = DefaultProfile.getProfile(regionId, accessKeyId, accessKeySecret);
        DefaultAcsClient client = new DefaultAcsClient(profile);
        return client;
    }

    public static final DefaultAcsClient initVodClient(String accessKeyId, String accessKeySecret, String securityToken) throws ClientException {
        String regionId = properties.getRegionId();  // 点播服务接入区域
        DefaultProfile profile = DefaultProfile.getProfile(regionId, accessKeyId, accessKeySecret, securityToken);
        DefaultAcsClient client = new DefaultAcsClient(profile);
        return client;
    }
}
