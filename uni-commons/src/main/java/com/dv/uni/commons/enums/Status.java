package com.dv.uni.commons.enums;

import lombok.AllArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * @Author 张盼
 * @Email zhangpan_soft@163.com
 * @Blog zhangpan_soft
 * @Date 2020/9/10 0010
 * @Des 按基本http, 2000代表成功状态, 4xxx作为资源, 权限状态, 5xxx代表系统错误,
 * 1***	信息，服务器收到请求，需要请求者继续执行操作
 * 2***	成功，操作被成功接收并处理
 * 3***	重定向，需要进一步的操作以完成请求
 * 4***	客户端错误，请求包含语法错误或无法完成请求
 * 5***	服务器错误，服务器在处理请求的过程中发生了错误,所有系统自身的错误全在这里
 * 6*** 系统对接第三方系统错误
 * 7*** 第三方系统对接系统错误
 * 8*** 预留
 * 9*** 预留
 */
@AllArgsConstructor
public enum Status {
    OK(2000, "成功"),

    REDIRECTION(3000, "重定向方面异常"),

    CUSTOM(4000, "客户端错误"),
    ACCESS_TOKEN_WRONGFUL(4001, "非法的accessToken"),
    ACCESS_TOKEN_EXPIRE(4002, "accessToken已过期"),
    NO_PERMISSION(4003, "没有权限"),
    REQUEST_PARAM_ERROR(4004, "参数错误"),
    DATA_NOT_EXISTS(4005, "数据不存在"),
    USERNAME_OR_PASSWORD_ERROR(4006, "用户名或密码错误"),
    PHONE_CODE_EXPIRE(4007, "手机验证码已过期"),
    PHONE_CODE_ERROR(4008, "手机验证码不正确"),
    ACCESS_TOKEN_NOT_EXISTS(4009, "token不存在"),
    ORDER_DETAIL_EMPTY(4010,"订单详情为空"),

    SYSTEM(5000, "系统错误"),
    UNKNOWN(5001, "未知异常"),
    REQUEST_PARSE_ERROR(5002, "request解析错误"),
    REFLECT_ERROR(5003, "反射异常"),
    JACKSON_EXCEPTION(5004, "Jackson异常"),
    MAP_TO_XML_EXCEPTION(5005,"MAP 转 XML 异常"),
    XML_TO_MAP_EXCEPTION(5006,"XML 转 MAP 异常"),
    WX_SIGN_VALID_FAIL(5007,"微信验签失败"),

    SYSTEM_TO_THIRD(6000, "系统对接第三方系统错误"),
    WX_ERROR(6001, "微信异常"),
    ALIYUN_OSS_EXCEPTION(6002, "阿里云oss异常"),
    ALIYUN_VOD_EXCEPTION(6003, "阿里云视频点播异常"),
    ALI_SIGN_EXCEPTION(6004, "阿里签名异常"),
    URL_ENCODER_EXCEPTION(6005, "url编码异常"),

    WX_SIGN_CREATE_FAIL(6100,"微信签名创建失败"),

    THIRD_TO_SYSTEM(7000, "第三方系统对接系统错误");
    private Integer code;
    private String  msg;

    private static MessageSource messageSource;

    public static void setMessageSource(MessageSource messageSource) {
        Status.messageSource = messageSource;
    }

    public String getMsg() {
        String msg = null;
        try {
            msg = messageSource.getMessage("status."+this.code, null, LocaleContextHolder.getLocale());
        } catch (NoSuchMessageException e) {
            msg = this.msg;
        }
        return msg;
    }

    public Integer getCode() {
        return code;
    }

    //通过静态内部类的方式注入bean，并赋值到枚举中
    @Component
    public static class ReportTypeServiceInjector {

        private final MessageSource messageSource;

        public ReportTypeServiceInjector(MessageSource messageSource) {
            this.messageSource = messageSource;
        }
        // 依赖注入完成后被自动调用
        @PostConstruct
        public void postConstruct() {
            Status.setMessageSource(messageSource);
        }
    }

}
