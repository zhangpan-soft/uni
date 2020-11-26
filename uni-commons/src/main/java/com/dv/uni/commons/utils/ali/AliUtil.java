package com.dv.uni.commons.utils.ali;

import com.dv.uni.commons.enums.Status;
import com.dv.uni.commons.exceptions.BaseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.io.UnsupportedEncodingException;
import java.util.Map;
import java.util.TreeMap;
import java.util.UUID;

@Configuration
@EnableConfigurationProperties(AliProperties.class)
public class AliUtil {

    private static AliProperties properties;

    public static AliProperties getProperties() {
        return properties;
    }

    @Autowired
    public AliUtil(AliProperties properties) {
        AliUtil.properties = properties;
    }


    public static String sign(Map<String, String> params) {
        try {
            java.text.SimpleDateFormat df = new java.text.SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
            df.setTimeZone(new java.util.SimpleTimeZone(0, "GMT"));// 这里一定要设置GMT时区
            //Map<String,String> treeMap = new TreeMap<>(params);
            params.put("SignatureMethod", properties.getSignatureMethod());
            params.put("SignatureNonce", UUID.randomUUID()
                                             .toString());
            params.put("AccessKeyId", properties.getAccessKeyId());
            params.put("SignatureVersion", properties.getSignatureVersion());
            params.put("Timestamp", df.format(new java.util.Date()));
            params.put("Format", properties.getFormat());
            if (params.containsKey("Signature"))
                params.remove("Signature");
            TreeMap<String, String> sortParas = new TreeMap<String, String>(params);
            java.util.Iterator<String> it = sortParas.keySet()
                                                     .iterator();
            StringBuilder sortQueryStringTmp = new StringBuilder();
            while (it.hasNext()) {
                String key = it.next();
                sortQueryStringTmp.append("&")
                                  .append(specialUrlEncode(key))
                                  .append("=")
                                  .append(specialUrlEncode(sortParas.get(key)));
            }
            String sortedQueryString = sortQueryStringTmp.substring(1);// 去除第一个多余的&符号
            StringBuilder stringToSign = new StringBuilder();
            stringToSign.append("GET")
                        .append("&");
            stringToSign.append(specialUrlEncode("/"))
                        .append("&");
            stringToSign.append(specialUrlEncode(sortedQueryString));
            javax.crypto.Mac mac = javax.crypto.Mac.getInstance("HmacSHA1");
            mac.init(new javax.crypto.spec.SecretKeySpec(properties.getAccessKeySecret()
                                                                   .getBytes("UTF-8"), "HmacSHA1"));
            byte[] signData = mac.doFinal(stringToSign.toString()
                                                      .getBytes("UTF-8"));
            return new sun.misc.BASE64Encoder().encode(signData);
        } catch (Exception e) {
            throw BaseException.of(Status.ALI_SIGN_EXCEPTION);
        }
    }

    public static String specialUrlEncode(String value) {
        try {
            return java.net.URLEncoder.encode(value, "UTF-8")
                                      .replace("+", "%20")
                                      .replace("*", "%2A")
                                      .replace("%7E", "~");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            throw BaseException.of(Status.URL_ENCODER_EXCEPTION);
        }
    }


}
