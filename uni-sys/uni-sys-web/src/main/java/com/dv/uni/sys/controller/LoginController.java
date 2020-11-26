package com.dv.uni.sys.controller;

import com.dv.uni.commons.annotations.IgnoreAuth;
import com.dv.uni.commons.entity.Result;
import com.dv.uni.commons.utils.SecurityUtils;
import com.dv.uni.sys.realm.PhoneCodeRealm;
import com.dv.uni.sys.realm.UsernamePasswordRealm;
import com.dv.uni.sys.realm.WxMiniappRealm;
import com.dv.uni.sys.realm.WxMpRealm;
import com.dv.uni.sys.token.PhoneToken;
import com.dv.uni.sys.token.UsernamePasswordToken;
import com.dv.uni.sys.token.WxMiniappToken;
import com.dv.uni.sys.token.WxMpToken;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;


/**
 * @Author 张盼
 * @Email zhangpan_soft@163.com
 * @Blog zhangpan_soft
 * @Date 2020/9/14 0014
 */
@RestController
@RequestMapping("login")
@Api(tags = "登录")
@ApiSupport(author = "zhangpan_soft")
public class LoginController {
    @Autowired
    private UsernamePasswordRealm usernamePasswordRealm;
    @Autowired
    private WxMiniappRealm wxMiniappRealm;
    @Autowired
    private PhoneCodeRealm phoneCodeRealm;
    @Autowired
    private WxMpRealm      wxMpRealm;

    @PostMapping(consumes = {
            "application/json"
    }, produces = {
            "application/json"
    })
    @IgnoreAuth
    @ApiOperation("登录-[用户名密码|手机号密码|email密码,用户名不能包含@并且不能为纯数字]")
    @ApiOperationSupport(author = "zhangpan_soft",ignoreParameters = {
            "id","salt"
    })
    public Result loginOfUsernamePassword(@RequestBody UsernamePasswordToken token) {
        return Result.ok(SecurityUtils.login(token, usernamePasswordRealm));
    }

    @PostMapping(value = "wx/miniapp", consumes = {
            "application/json"
    }, produces = {
            "application/json"
    })
    @ApiOperation("登录-微信小程序")
    @IgnoreAuth
    @ApiOperationSupport(author = "zhangpan_soft",ignoreParameters = {"id","cache"})
    public Result loginOfWxMiniapp(@RequestBody WxMiniappToken token) {
        Map<String, Object> map = SecurityUtils.login(token, wxMiniappRealm);
        map.putAll(token.getCache());
        return Result.ok(map);
    }

    @PostMapping(value = "wx/mp", consumes = {
            "application/json"
    }, produces = {
            "application/json"
    })
    @ApiOperation("登录-微信公众号")
    @IgnoreAuth
    @ApiOperationSupport(author = "zhangpan_soft",ignoreParameters = {"id","cache"})
    public Result loginOfWxMp(@RequestBody WxMpToken token) {
        Map<String, Object> map = SecurityUtils.login(token, wxMpRealm);
        map.putAll(token.getCache());
        return Result.ok(map);
    }

    @PostMapping(value = "phone", consumes = {
            "application/json"
    }, produces = {
            "application/json"
    })
    @IgnoreAuth
    @ApiOperation("登录-手机号")
    @ApiOperationSupport(author = "zhangpan_soft",ignoreParameters = {"id"})
    public Result loginOfPhone(@RequestBody PhoneToken token) {
        return Result.ok(SecurityUtils.login(token, phoneCodeRealm));
    }
}
