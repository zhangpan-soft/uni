package com.dv.uni.mall.controller;

import com.dv.uni.commons.controller.AbstractController;
import com.dv.uni.commons.controller.BaseController;
import com.dv.uni.mall.entity.UniBrand;
import com.dv.uni.mall.entity.UniSpecification;
import com.dv.uni.mall.service.UniBrandService;
import com.dv.uni.mall.service.UniSpecificationService;
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
@RequestMapping("uni/specification")
@Api(tags = "规格")
public class UniSpecificationController extends AbstractController<UniSpecification,String> implements BaseController<UniSpecification,String> {
    private UniSpecificationService service;
    public UniSpecificationController(UniSpecificationService service) {
        super(service);
        this.service = service;
    }

    @Override
    public UniSpecificationService getService() {
        return service;
    }
}
