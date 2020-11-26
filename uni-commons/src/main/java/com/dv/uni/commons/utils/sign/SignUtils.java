package com.dv.uni.commons.utils.sign;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.security.MessageDigest;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
import java.util.UUID;

/**
 * @Author 张盼
 * @Email zhangpan_soft@163.com
 * @Blog zhangpan_soft
 * @Date 2020/10/22 0022
 */
public class SignUtils {
    /**
     * 判断字符串是否为空
     * @param target
     * @return
     */
    public static boolean isEmpty(String target){
        if (target == null)
            return true;
        return target.matches("\\s*");
    }

    /**
     * 签名
     * @param map 请求参数
     * @param key 秘钥
     * @param signType 签名方式
     * @return 签名字符串
     */
    public static String sign(Map<String,String> map,String key,SignType signType) {
        return sign(map, key, signType,null);
    }

    private static String sign(Map<String,String> map,String key,SignType signType,String nonceStr){
        map.put("key",key);// 添加key
        if (isEmpty(nonceStr)){
            map.put("nonceStr", UUID.randomUUID().toString().replaceAll("-", ""));// 添加随机字符串
        }
        map.put("signType",signType.name());// 指定签名方式
        Map<String,String> treeMap = new TreeMap<>(map);// ASCII排序
        StringBuilder sb = new StringBuilder();
        treeMap.forEach((k,v)->{
            if (!isEmpty(v)&&!"sign".equalsIgnoreCase(k)){// 如果值不为空,并且键不是sign则拼接待加密字符串
                sb.append(k).append("=").append(v).append("&");
            }
        });
        if (sb.length()>0){
            sb.deleteCharAt(sb.length()-1);// 删除最后一个&
        }
        String sign = null;
        if (SignType.MD5==signType){// 如果是md5加签
            sign = md5(sb.toString());
        }else if (SignType.HMACSHA256==signType){
            sign = hmacsha256(sb.toString(), key);
        }
        map.remove("key");
        return sign;
    }

    /**
     *
     * 签名
     * @param map 请求参数
     * @param key 秘钥
     * @param signType 签名方式
     * @return 签名之后的map,含有sign
     */
    public static Map<String,String> signMap(Map<String,String> map,String key,SignType signType) {
        map.put("sign",sign(map,key,signType,null));
        return map;
    }

    /**
     * 签名验证
     * @param data 请求参数
     * @param key 秘钥
     * @return
     */
    public static boolean isValid(Map<String,String> data,String key)  {
        if (!data.containsKey("sign")||!data.containsKey("signType")) {
            return false;
        } else {
            String sign = (String)data.get("sign");
            return sign(data, key, SignType.valueOf(data.get("signType")),data.get("nonceStr")).equals(sign);
        }
    }

    /**
     * md5签名
     * @param data 待签名字符串
     * @return
     */
    public static String md5(String data) {
        try {
            MessageDigest md = MessageDigest.getInstance("md5");
            byte[] array = md.digest(data.getBytes("UTF-8"));
            StringBuilder sb = new StringBuilder();
            byte[] var4 = array;
            int var5 = array.length;
            for(int var6 = 0; var6 < var5; ++var6) {
                byte item = var4[var6];
                sb.append(Integer.toHexString(item & 255 | 256).substring(1, 3));
            }
            return sb.toString().toUpperCase();
        } catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    /**
     * hmacsha256签名
     * @param data 待签名字符串
     * @param key 秘钥
     * @return
     */
    public static String hmacsha256(String data, String key)  {
        try {
            Mac sha256_HMAC = Mac.getInstance("HmacSHA256");
            SecretKeySpec secret_key = new SecretKeySpec(key.getBytes("UTF-8"), "HmacSHA256");
            sha256_HMAC.init(secret_key);
            byte[] array = sha256_HMAC.doFinal(data.getBytes("UTF-8"));
            StringBuilder sb = new StringBuilder();
            byte[] var6 = array;
            int var7 = array.length;
            for(int var8 = 0; var8 < var7; ++var8) {
                byte item = var6[var8];
                sb.append(Integer.toHexString(item & 255 | 256).substring(1, 3));
            }
            return sb.toString().toUpperCase();
        } catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args){
        /**
         {deviceId=K81H-000000000000000100000001, serviceTime=120, userId=user, avatar=https://htlg.oss-cn-shenzhen.aliyuncs.com/upload/1704271648402570.jpg, nickname=username, nonceStr=0a093c69719147baaf5ae15d52e7f788, signType=MD5, sign=C7D36116774235A96328E13F4346DC7C}
         */
        String key = "3ST6IRdF6Ocdt#UD(SdOrGAgoHY6G1Bm";
        Map<String,String> params = new HashMap<String, String>(){{
            this.put("deviceId","K81H-000000000000000100000001");
            this.put("serviceTime","120");
            this.put("userId","user");
            this.put("avatar","https://htlg.oss-cn-shenzhen.aliyuncs.com/upload/1704271648402570.jpg");
            this.put("nickname","username");
            //this.put("nonceStr","0a093c69719147baaf5ae15d52e7f788");
            //this.put("signType","MD5");
            //this.put("sign","C7D36116774235A96328E13F4346DC7C");
        }};
        Map<String, String> signMap = SignUtils.signMap(new HashMap<String, String>(params), key, SignType.MD5);
        System.out.println(signMap);
        System.out.println(SignUtils.isValid(signMap,key));
    }
}
