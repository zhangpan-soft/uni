package com.dv.uni.commons.entity;

import com.alibaba.fastjson.JSON;
import com.dv.uni.commons.enums.Status;
import com.dv.uni.commons.utils.StringUtils;
import com.dv.uni.commons.utils.aes.AESUtil;
import com.dv.uni.commons.utils.rsa.RSAUtil;
import lombok.Data;

/**
 * @Author 张盼
 * @Email zhangpan_soft@163.com
 * @Blog zhangpan_soft
 * @Date 2020/9/10 0010
 */
@Data
public class Result implements BaseModel {
    private Integer code;
    private String  msg;
    private Long    timestamp;
    private String  key;
    private Object  data;

    private static void encrypted(Result response) {
        // 生成临时aes秘钥
        response.key = StringUtils.uuid();
        // 对返回数据用aes加密
        response.data = AESUtil.encrypt(JSON.toJSONString(response.data), response.key);
        // 对key进行rsa加密
        response.key = RSAUtil.encryptPrivate(response.key, RSAUtil.SERVER_PRI_KEY);
    }

    /**
     * 成功
     *
     * @param msg       成功的提示消息
     * @param data      数据
     * @param encrypted 是否加密
     * @return
     */
    public static Result ok(String msg, Object data, boolean encrypted) {
        Result response = new Result();
        response.code = Status.OK.getCode();
        response.msg = msg == null ? Status.OK.getMsg() : msg;
        response.data = data;
        response.timestamp = System.currentTimeMillis();
        if (encrypted && data != null) {// 如果是加密的
            encrypted(response);
        }
        return response;
    }

    /**
     * 成功
     *
     * @param data      数据
     * @param encrypted 是否加密
     * @return
     */
    public static Result ok(Object data, boolean encrypted) {
        return ok(Status.OK.getMsg(), data, encrypted);
    }

    /**
     * 成功,不加密
     *
     * @param data 数据
     * @return
     */
    public static Result ok(Object data, String msg) {
        return ok(msg, data, false);
    }

    /**
     * 成功,不加密
     *
     * @param data 数据
     * @return
     */
    public static Result ok(Object data) {
        return ok(null, data, false);
    }

    /**
     * 成功,不加密
     *
     * @return
     */
    public static Result ok() {
        return ok(Status.OK.getMsg(), null, false);
    }

    /**
     * 失败
     *
     * @param status    状态码
     * @param data      数据
     * @param encrypted 是否加密
     * @return
     */
    public static Result fail(Status status, Object data, boolean encrypted) {
        Result response = new Result();
        response.timestamp = System.currentTimeMillis();
        response.code = status.getCode();
        response.msg = status.getMsg();
        response.data = data;
        if (encrypted) {
            encrypted(response);
        }
        return response;
    }

    /**
     * 失败,不加密
     *
     * @param status 状态码
     * @return
     */
    public static Result fail(Status status) {
        return fail(status, null, false);
    }

}
