package com.dv.uni.commons.utils.ali.sms;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.dv.uni.commons.utils.ali.AliUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
@EnableConfigurationProperties(SmsProperties.class)
public class SmsUtils {

    private static SmsProperties properties;

    @Autowired
    public SmsUtils(SmsProperties properties) {
        SmsUtils.properties = properties;
    }

    public static SmsProperties getProperties() {
        return properties;
    }

    /**
     * 发送短信
     *
     * @param code         短信code
     * @param phone        手机号
     * @param templateCode 模板code
     */
    public static JSONObject send(String code, String phone, String templateCode) {
        DefaultProfile profile = DefaultProfile.getProfile(properties.getRegionId(), AliUtil.getProperties()
                                                                                            .getAccessKeyId(), AliUtil.getProperties()
                                                                                                                      .getAccessKeySecret());
        IAcsClient client = new DefaultAcsClient(profile);
        CommonRequest request = new CommonRequest();
        request.setSysMethod(MethodType.POST);
        request.setSysDomain(properties.getSendUrl());
        request.setSysVersion(properties.getVersion());
        request.setSysAction("SendSms");
        request.putQueryParameter("RegionId", properties.getRegionId());
        request.putQueryParameter("PhoneNumbers", phone);
        request.putQueryParameter("SignName", properties.getSignName());
        request.putQueryParameter("TemplateCode", templateCode);
        request.putQueryParameter("TemplateParam", "{\"code\":\"" + code + "\"}");
        try {
            CommonResponse response = client.getCommonResponse(request);
            log.info(response.getData());
            return JSON.parseObject(response.getData());
        } catch (ServerException e) {
            e.printStackTrace();
        } catch (ClientException e) {
            e.printStackTrace();
        }
        return null;
    }
}
