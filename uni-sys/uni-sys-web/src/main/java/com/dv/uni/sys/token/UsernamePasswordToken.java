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
public class UsernamePasswordToken implements AuthcToken {
    private String username;
    private String password;
    private String salt;
    private String id;
    private String scope;

    @Override
    public String getName() {
        return "usernamePaswordToken";
    }

    @Override
    public Object principal() {
        return this.id;
    }
}
