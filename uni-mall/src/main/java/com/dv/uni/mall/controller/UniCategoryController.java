package com.dv.uni.mall.controller;

import com.dv.uni.commons.controller.AbstractController;
import com.dv.uni.commons.controller.BaseController;
import com.dv.uni.mall.entity.UniBrand;
import com.dv.uni.mall.entity.UniCategory;
import com.dv.uni.mall.service.UniBrandService;
import com.dv.uni.mall.service.UniCategoryService;
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
@RequestMapping("uni/category")
@Api(tags = "分类")
public class UniCategoryController extends AbstractController<UniCategory,String> implements BaseController<UniCategory,String> {
    private UniCategoryService service;
    public UniCategoryController(UniCategoryService service) {
        super(service);
        this.service = service;
    }

    @Override
    public UniCategoryService getService() {
        return service;
    }
}
