package com.dv.uni.mall.controller;

import com.dv.uni.commons.controller.AbstractController;
import com.dv.uni.commons.controller.BaseController;
import com.dv.uni.mall.entity.UniBrand;
import com.dv.uni.mall.entity.UniUsp;
import com.dv.uni.mall.service.UniBrandService;
import com.dv.uni.mall.service.UniUspService;
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
@RequestMapping("uni/usp")
@Api(tags = "卖点")
public class UniUspController extends AbstractController<UniUsp,String> implements BaseController<UniUsp,String> {
    private UniUspService service;
    public UniUspController(UniUspService service) {
        super(service);
        this.service = service;
    }

    @Override
    public UniUspService getService() {
        return service;
    }
}
