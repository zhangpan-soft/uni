package com.dv.uni.sys.token;

import com.dv.uni.commons.authc.AuthcToken;
import lombok.Data;

import java.util.Map;

/**
 * @Author 张盼
 * @Email zhangpan_soft@163.com
 * @Blog zhangpan_soft
 * @Date 2020/10/26 0026
 */
@Data
public class WxMpToken implements AuthcToken {
    private String              name;
    private String              id;
    private String              code;
    private Map<String, Object> cache;
    private String              scope;

    @Override
    public String getName() {
        return "wxMpToken";
    }

    @Override
    public Object principal() {
        return this.id;
    }
}
