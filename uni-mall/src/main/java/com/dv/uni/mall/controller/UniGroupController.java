package com.dv.uni.mall.controller;

import com.dv.uni.commons.controller.AbstractController;
import com.dv.uni.commons.controller.BaseController;
import com.dv.uni.mall.entity.UniBrand;
import com.dv.uni.mall.entity.UniGroup;
import com.dv.uni.mall.service.UniBrandService;
import com.dv.uni.mall.service.UniGroupService;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author 张盼
 * @Email zhangpan_soft@163.com
 * @Blog zhangpan_soft
 * @Date 2020/12/1 0001
 */
@RestController
@RequestMapping("uni/group")
@Api(tags = "分组")
public class UniGroupController extends AbstractController<UniGroup,String> implements BaseController<UniGroup,String> {
    private UniGroupService service;
    public UniGroupController(UniGroupService service) {
        super(service);
        this.service = service;
    }

    @Override
    public UniGroupService getService() {
        return service;
    }
}
