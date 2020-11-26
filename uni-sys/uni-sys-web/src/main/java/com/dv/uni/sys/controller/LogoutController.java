package com.dv.uni.sys.controller;

import com.dv.uni.commons.entity.Result;
import com.dv.uni.commons.utils.SecurityUtils;
import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author 张盼
 * @Email zhangpan_soft@163.com
 * @Blog zhangpan_soft
 * @Date 2020/11/6 0006
 */
@RestController
@RequestMapping("logout")
@Api
@ApiSupport(author = "zhangpan_soft")
public class LogoutController {

    @GetMapping(value = "", produces = "application/json")
    @ApiOperation("登出")
    public Result logout() {
        SecurityUtils.logout();
        return Result.ok();
    }
}
