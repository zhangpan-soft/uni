package com.dv.uni.mall.controller;

import com.dv.uni.commons.controller.AbstractController;
import com.dv.uni.commons.controller.BaseController;
import com.dv.uni.commons.service.BaseService;
import com.dv.uni.mall.entity.UniBrand;
import com.dv.uni.mall.service.UniBrandService;
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
@RequestMapping("uni/brand")
@Api(tags = "品牌")
public class UniBrandController extends AbstractController<UniBrand,String> implements BaseController<UniBrand,String> {
    private UniBrandService service;
    public UniBrandController(UniBrandService service) {
        super(service);
        this.service = service;
    }

    @Override
    public UniBrandService getService() {
        return service;
    }
}
