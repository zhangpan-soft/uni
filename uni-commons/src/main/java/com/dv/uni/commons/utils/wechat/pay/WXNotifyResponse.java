package com.dv.uni.commons.utils.wechat.pay;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WXNotifyResponse implements Serializable {
    private String return_code;
    private String return_msg;
}
