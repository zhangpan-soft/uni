package com.dv.uni.mall.controller;

import com.dv.uni.commons.controller.AbstractController;
import com.dv.uni.commons.controller.BaseController;
import com.dv.uni.mall.entity.UniBrand;
import com.dv.uni.mall.entity.UniCommodity;
import com.dv.uni.mall.service.UniBrandService;
import com.dv.uni.mall.service.UniCommodityService;
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
@RequestMapping("uni/commodity")
@Api(tags = "商品")
public class UniCommodityController extends AbstractController<UniCommodity,String> implements BaseController<UniCommodity,String> {
    private UniCommodityService service;
    public UniCommodityController(UniCommodityService service) {
        super(service);
        this.service = service;
    }

    @Override
    public UniCommodityService getService() {
        return service;
    }
}
