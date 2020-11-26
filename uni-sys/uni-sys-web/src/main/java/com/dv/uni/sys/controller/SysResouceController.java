package com.dv.uni.sys.controller;

import com.dv.uni.commons.controller.AbstractController;
import com.dv.uni.commons.controller.BaseController;
import com.dv.uni.commons.service.BaseService;
import com.dv.uni.sys.entity.SysResource;
import com.dv.uni.sys.service.SysResourceService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author 张盼
 * @Email zhangpan_soft@163.com
 * @Blog zhangpan_soft
 * @Date 2020/9/14 0014
 */
@RestController
@RequestMapping("sys/resource")
@Api(tags = "资源")
public class SysResouceController extends AbstractController<SysResource, String> {

    private SysResourceService service;

    public SysResouceController(SysResourceService service) {
        super(service);
        this.service = service;
    }

    @Override
    public SysResourceService getService() {
        return this.service;
    }
}
