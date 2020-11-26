package com.dv.uni.sys.realm;

import com.dv.uni.commons.authc.AuthcToken;
import com.dv.uni.commons.entity.BaseEntity;
import com.dv.uni.commons.enums.Status;
import com.dv.uni.commons.exceptions.BaseException;
import com.dv.uni.commons.realms.Realm;
import com.dv.uni.commons.utils.Assert;
import com.dv.uni.commons.utils.StringUtils;
import com.dv.uni.commons.utils.md5.MD5Util;
import com.dv.uni.sys.entity.SysUser;
import com.dv.uni.sys.service.SysUserService;
import com.dv.uni.sys.token.UsernamePasswordToken;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @Author 张盼
 * @Email zhangpan_soft@163.com
 * @Blog zhangpan_soft
 * @Date 2020/9/14 0014
 */
@Data
@Component
public class UsernamePasswordRealm extends AbstructRealm implements Realm {
    @Autowired
    private SysUserService sysUserService;

    @Override
    public BaseEntity authentication(AuthcToken token) {
        UsernamePasswordToken usernamePasswordToken = (UsernamePasswordToken) token;
        Assert.hasText(usernamePasswordToken.getUsername(), Status.REQUEST_PARAM_ERROR, "用户名为空");
        Assert.hasText(usernamePasswordToken.getPassword(),Status.REQUEST_PARAM_ERROR,"密码为空");
        SysUser sysUser;
        if (StringUtils.isPhone(((UsernamePasswordToken) token).getUsername())){
            sysUser = sysUserService.findByPhone(usernamePasswordToken.getUsername());
        }else if (StringUtils.isEmpty(usernamePasswordToken.getUsername())){
            sysUser = sysUserService.findByEmail(usernamePasswordToken.getUsername());
        }else {
            sysUser = sysUserService.findByUsername(usernamePasswordToken.getUsername());
        }
        if (sysUser == null || !MD5Util.verify(usernamePasswordToken.getPassword(), sysUser.getPassword(), sysUser.getSalt())) {
            throw BaseException.of(Status.USERNAME_OR_PASSWORD_ERROR);
        }
        usernamePasswordToken.setId(sysUser.getId());
        return sysUser;
    }
}
