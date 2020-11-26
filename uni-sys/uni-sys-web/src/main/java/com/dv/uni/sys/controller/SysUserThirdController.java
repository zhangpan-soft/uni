package com.dv.uni.sys.controller;

import com.dv.uni.commons.controller.AbstractController;
import com.dv.uni.commons.service.BaseService;
import com.dv.uni.sys.entity.SysUserThird;
import com.dv.uni.sys.service.SysUserThirdService;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author 张盼
 * @Email zhangpan_soft@163.com
 * @Blog zhangpan_soft
 * @Date 2020/9/18 0018
 */
@RestController
@RequestMapping("sys/user/third")
@Api(tags = "用户第三方信息")
public class SysUserThirdController extends AbstractController<SysUserThird, String> {

    private SysUserThirdService service;

    public SysUserThirdController(SysUserThirdService service) {
        super(service);
        this.service = service;
    }

    @Override
    public BaseService<SysUserThird, String> getService() {
        return this.service;
    }
}
