package com.dv.uni.commons.utils.wechat.pay;

import lombok.Data;

import java.io.Serializable;

@Data
public class WXNotifyRequest implements Serializable {
    private String return_code;//返回状态码
    private String return_msg;//返回信息
    private String appid;//小程序ID
    private String mch_id;//商户号
    private String device_info;//设备号
    private String nonce_str;//随机字符串
    private String sign;//签名
    private String sign_type;//签名类型
    private String result_code;//业务结果
    private String err_code;//错误代码
    private String err_code_des;//错误代码描述
    private String openid;//用户标识
    private String is_subscribe;//是否关注公众账号
    private String trade_type;//交易类型
    private String bank_type;//付款银行
    private int    total_fee;//订单金额
    private int    settlement_total_fee;//应结订单金额
    private String fee_type;//货币种类
    private int    cash_fee;//现金支付金额
    private String cash_fee_type;//现金支付货币类型
    private int    coupon_fee;//总代金券金额
    private int    coupon_count;//代金券使用数量
    private String coupon_type_$n;//代金券类型
    private String coupon_id_$n;//代金券ID
    private int    coupon_fee_$n;//单个代金券支付金额
    private String transaction_id;//微信支付订单号
    private String out_trade_no;//商户订单号
    private String attach;//商家数据包
    private String time_end;//支付完成时间
}
