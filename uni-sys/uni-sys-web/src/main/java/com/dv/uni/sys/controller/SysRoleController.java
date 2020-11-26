package com.dv.uni.sys.controller;

import com.dv.uni.commons.controller.AbstractController;
import com.dv.uni.commons.controller.BaseController;
import com.dv.uni.commons.service.BaseService;
import com.dv.uni.sys.entity.SysRole;
import com.dv.uni.sys.service.SysRoleService;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author 张盼
 * @Email zhangpan_soft@163.com
 * @Blog zhangpan_soft
 * @Date 2020/9/14 0014
 */
@RestController
@RequestMapping("sys/role")
@Api(tags = "角色")
public class SysRoleController extends AbstractController<SysRole, String> {
    private SysRoleService service;

    public SysRoleController(SysRoleService service) {
        super(service);
        this.service = service;
    }

    @Override
    public SysRoleService getService() {
        return this.service;
    }
}
