package com.dv.uni.sys.realm;

import com.dv.uni.commons.authc.AuthcToken;
import com.dv.uni.commons.entity.BaseEntity;
import com.dv.uni.commons.enums.Status;
import com.dv.uni.commons.realms.Realm;
import com.dv.uni.commons.utils.Assert;
import com.dv.uni.commons.utils.ScopeUtils;
import com.dv.uni.commons.utils.SecurityUtils;
import com.dv.uni.commons.utils.StringUtils;
import com.dv.uni.sys.entity.SysUser;
import com.dv.uni.sys.service.SysUserService;
import com.dv.uni.sys.token.PhoneToken;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @Author 张盼
 * @Email zhangpan_soft@163.com
 * @Blog zhangpan_soft
 * @Date 2020/9/14 0014
 */
@Data
@Component
public class PhoneCodeRealm extends AbstructRealm implements Realm {
    @Autowired
    private SysUserService sysUserService;

    @Override
    public BaseEntity authentication(AuthcToken token) {
        PhoneToken phoneToken = (PhoneToken) token;
        String cache = (String) SecurityUtils.getCache("phoneCode:" + phoneToken.getPhone());
        Assert.hasText(cache, Status.PHONE_CODE_EXPIRE);
        Assert.state(cache.equalsIgnoreCase(phoneToken.getCode()), Status.PHONE_CODE_ERROR);
        SysUser sysUser = sysUserService.findByPhone(phoneToken.getPhone());
        if (sysUser == null) {
            sysUser = new SysUser();
            if (!StringUtils.isEmpty(token.getScope()) && !ScopeUtils.contains(token.getScope(), "manager")){
                sysUser.setScope(token.getScope());
            }
            BaseEntity authentication = (BaseEntity) SecurityUtils.getClaim(SecurityUtils.get(), "authentication");
            sysUser.setId(StringUtils.uuid());
            if (authentication != null) {
                sysUser.setCreateBy((String) authentication.getId());
                sysUser.setUpdateBy((String) authentication.getId());
            }
            sysUser.setCreateDate(new Date());
            sysUser.setUpdateDate(new Date());
            sysUser.setPhone(phoneToken.getPhone());
            if (!StringUtils.isEmpty(token.getScope())){
                sysUser.setScope(token.getScope());
            }
            sysUserService.insert(sysUser);
        }
        phoneToken.setId(sysUser.getId());
        return sysUser;
    }

}
