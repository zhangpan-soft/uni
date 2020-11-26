package com.dv.uni.sys.realm;

import com.dv.uni.commons.authc.AuthcToken;
import com.dv.uni.commons.entity.BaseEntity;
import com.dv.uni.commons.enums.Status;
import com.dv.uni.commons.exceptions.BaseException;
import com.dv.uni.commons.realms.Realm;
import com.dv.uni.commons.utils.ObjectUtils;
import com.dv.uni.commons.utils.ScopeUtils;
import com.dv.uni.commons.utils.SecurityUtils;
import com.dv.uni.commons.utils.StringUtils;
import com.dv.uni.sys.entity.SysUser;
import com.dv.uni.sys.entity.SysUserThird;
import com.dv.uni.sys.service.SysUserService;
import com.dv.uni.sys.service.SysUserThirdService;
import com.dv.uni.sys.token.WxMpToken;
import lombok.Data;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.result.WxMpOAuth2AccessToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @Author 张盼
 * @Email zhangpan_soft@163.com
 * @Blog zhangpan_soft
 * @Date 2020/10/26 0026
 */
@Component
@Data
public class WxMpRealm extends AbstructRealm implements Realm {
    @Autowired
    private WxMpService wxMpService;

    @Autowired
    private SysUserService      sysUserService;
    @Autowired
    private SysUserThirdService sysUserThirdService;

    @Override
    public BaseEntity authentication(AuthcToken token) {
        try {
            WxMpToken wxMpToken = (WxMpToken) token;
            WxMpOAuth2AccessToken accessToken = wxMpService.oauth2getAccessToken(wxMpToken.getCode());
            String userId;
            if (!StringUtils.isEmpty(accessToken.getUnionId())) {
                userId = sysUserThirdService.findUserIdByUnionId(accessToken.getUnionId());
            } else {
                userId = sysUserThirdService.findUserIdByOpenId(accessToken.getOpenId());
            }
            SysUser sysUser;
            BaseEntity authentication = (BaseEntity) SecurityUtils.getClaim(SecurityUtils.get(), "authentication");
            if (StringUtils.isEmpty(userId)) {// 新增
                sysUser = new SysUser();
                if (!StringUtils.isEmpty(token.getScope()) && !ScopeUtils.contains(token.getScope(), "manager")){
                    sysUser.setScope(token.getScope());
                }
                sysUser.setId(StringUtils.uuid());
                if (authentication != null) {
                    sysUser.setCreateBy((String) authentication.getId());
                    sysUser.setUpdateBy((String) authentication.getId());
                }
                sysUser.setCreateDate(new Date());
                sysUser.setUpdateDate(new Date());
                sysUserService.insert(sysUser);
                SysUserThird sysUserThird = new SysUserThird();
                sysUserThird.setId(StringUtils.uuid());
                if (authentication != null) {
                    sysUserThird.setCreateBy((String) authentication.getId());
                    sysUserThird.setUpdateBy((String) authentication.getId());
                }
                sysUserThird.setCreateDate(new Date());
                sysUserThird.setUpdateDate(new Date());
                sysUserThird.setUserId(sysUser.getId());
                sysUserThird.setOpenId(accessToken.getOpenId());
                sysUserThird.setUnionId(accessToken.getUnionId());
                sysUserThirdService.insert(sysUserThird);
            } else {
                sysUser = sysUserService.findById(userId);
                SysUserThird temp = sysUserThirdService.findByOpenId(accessToken.getOpenId());
                if (temp == null) {
                    SysUserThird sysUserThird = new SysUserThird();
                    sysUserThird.setId(StringUtils.uuid());
                    if (authentication != null) {
                        sysUserThird.setCreateBy((String) authentication.getId());
                        sysUserThird.setUpdateBy((String) authentication.getId());
                    }
                    sysUserThird.setCreateDate(new Date());
                    sysUserThird.setUpdateDate(new Date());
                    sysUserThird.setUserId(sysUser.getId());
                    sysUserThird.setOpenId(accessToken.getOpenId());
                    sysUserThird.setUnionId(accessToken.getUnionId());
                    sysUserThirdService.insert(sysUserThird);
                } else {
                    if (StringUtils.isEmpty(temp.getUnionId()) && !StringUtils.isEmpty(accessToken.getUnionId())) {
                        temp.setUnionId(accessToken.getUnionId());
                        sysUserThirdService.update(temp);
                    }
                }
            }
            wxMpToken.setId(sysUser.getId());
            wxMpToken.setCache(ObjectUtils.obj2Map(accessToken, String.class, Object.class));
            return sysUser;
        } catch (WxErrorException e) {
            e.printStackTrace();
            throw BaseException.of(Status.WX_ERROR, e);
        }
    }
}
