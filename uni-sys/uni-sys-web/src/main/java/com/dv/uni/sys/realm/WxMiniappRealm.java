package com.dv.uni.sys.realm;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.bean.WxMaJscode2SessionResult;
import com.dv.uni.commons.authc.AuthcToken;
import com.dv.uni.commons.entity.BaseEntity;
import com.dv.uni.commons.enums.Status;
import com.dv.uni.commons.exceptions.BaseException;
import com.dv.uni.commons.realms.Realm;
import com.dv.uni.commons.utils.ObjectUtils;
import com.dv.uni.commons.utils.ScopeUtils;
import com.dv.uni.commons.utils.SecurityUtils;
import com.dv.uni.commons.utils.StringUtils;
import com.dv.uni.commons.utils.wechat.miniapp.WxMaConfiguration;
import com.dv.uni.sys.entity.SysUser;
import com.dv.uni.sys.entity.SysUserThird;
import com.dv.uni.sys.service.SysUserService;
import com.dv.uni.sys.service.SysUserThirdService;
import com.dv.uni.sys.token.WxMiniappToken;
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
@Component
@Data
public class WxMiniappRealm extends AbstructRealm implements Realm {
    @Autowired
    private SysUserService      sysUserService;
    @Autowired
    private SysUserThirdService sysUserThirdService;

    @Override
    public BaseEntity authentication(AuthcToken token) {
        WxMiniappToken wxMiniappToken = (WxMiniappToken) token;
        WxMaService maService = WxMaConfiguration.getMaService(wxMiniappToken.getAppid());
        try {
            WxMaJscode2SessionResult sessionInfo = maService.getUserService()
                                                            .getSessionInfo(wxMiniappToken.getCode());
            String userId;
            if (!StringUtils.isEmpty(sessionInfo.getUnionid())) {
                userId = sysUserThirdService.findUserIdByUnionId(sessionInfo.getUnionid());
            } else {
                userId = sysUserThirdService.findUserIdByOpenId(sessionInfo.getOpenid());
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
                sysUserThird.setOpenId(sessionInfo.getOpenid());
                sysUserThird.setUnionId(sessionInfo.getUnionid());
                sysUserThirdService.insert(sysUserThird);
            } else {
                sysUser = sysUserService.findById(userId);
                SysUserThird temp = sysUserThirdService.findByOpenId(sessionInfo.getOpenid());
                if (temp==null) {
                    SysUserThird sysUserThird = new SysUserThird();
                    sysUserThird.setId(StringUtils.uuid());
                    if (authentication != null) {
                        sysUserThird.setCreateBy((String) authentication.getId());
                        sysUserThird.setUpdateBy((String) authentication.getId());
                    }
                    sysUserThird.setCreateDate(new Date());
                    sysUserThird.setUpdateDate(new Date());
                    sysUserThird.setUserId(sysUser.getId());
                    sysUserThird.setOpenId(sessionInfo.getOpenid());
                    sysUserThird.setUnionId(sessionInfo.getUnionid());
                    sysUserThirdService.insert(sysUserThird);
                }else {
                    if (StringUtils.isEmpty(temp.getUnionId())&&!StringUtils.isEmpty(sessionInfo.getUnionid())){
                        temp.setUnionId(sessionInfo.getUnionid());
                        sysUserThirdService.update(temp);
                    }
                }
            }
            wxMiniappToken.setId(sysUser.getId());
            wxMiniappToken.setCache(ObjectUtils.obj2Map(sessionInfo, String.class, Object.class));
            return sysUser;
        } catch (Exception e) {
            e.printStackTrace();
            throw BaseException.of(Status.WX_ERROR, "获取sessionInfo异常", e);
        }
    }
}
