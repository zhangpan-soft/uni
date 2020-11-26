package com.dv.uni.sys.token;

import com.dv.uni.commons.authc.AuthcToken;
import lombok.Data;

/**
 * @Author 张盼
 * @Email zhangpan_soft@163.com
 * @Blog zhangpan_soft
 * @Date 2020/9/14 0014
 */
@Data
public class PhoneToken implements AuthcToken {
    private String id;
    private String code;
    private String phone;
    private String scope;

    @Override
    public String getName() {
        return "phoneToken";
    }

    @Override
    public Object principal() {
        return id;
    }
}
