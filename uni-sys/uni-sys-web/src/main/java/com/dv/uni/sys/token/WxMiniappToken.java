package com.dv.uni.sys.token;

import com.dv.uni.commons.authc.AuthcToken;
import lombok.Data;

import java.util.Map;

/**
 * @Author 张盼
 * @Email zhangpan_soft@163.com
 * @Blog zhangpan_soft
 * @Date 2020/9/14 0014
 */
@Data
public class WxMiniappToken implements AuthcToken {
    private String             appid;
    private String             code;
    private String             id;
    private Map<String,Object> cache;
    private String             scope;

    @Override
    public String getName() {
        return "wxMiniappToken";
    }

    @Override
    public Object principal() {
        return this.id;
    }
}
