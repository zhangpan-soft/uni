package com.dv.uni.sys.controller;

import com.dv.uni.commons.annotations.Select;
import com.dv.uni.commons.controller.AbstractController;
import com.dv.uni.commons.entity.Result;
import com.dv.uni.commons.service.BaseService;
import com.dv.uni.sys.entity.SysUser;
import com.dv.uni.sys.service.SysRoleService;
import com.dv.uni.sys.service.SysUserService;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.Max;

/**
 * @Author 张盼
 * @Email zhangpan_soft@163.com
 * @Blog zhangpan_soft
 * @Date 2020/9/11 0011
 */
@RestController
@RequestMapping("sys/user")
@Api(tags = "用户")
public class SysUserController extends AbstractController<SysUser, String> {

    private SysUserService service;

    public SysUserController(SysUserService service) {
        super(service);
        this.service = service;
    }

    @Override
    public SysUserService getService() {
        return this.service;
    }

    @Override
    @Select
    public Result findAll(SysUser entity, @Valid @Max(Integer.MAX_VALUE) int pageNum, @Valid @Max(2000) int pageSize) {
        return Result.ok(service.findAll(entity, pageNum, pageSize));
    }

    @Override
    @Select
    public Result findAll(SysUser entity) {
        return Result.ok(service.findAll(entity));
    }
}
