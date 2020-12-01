package com.dv.uni.mall.controller;

import com.dv.uni.commons.controller.AbstractController;
import com.dv.uni.commons.controller.BaseController;
import com.dv.uni.mall.entity.UniBrand;
import com.dv.uni.mall.entity.UniLabel;
import com.dv.uni.mall.service.UniBrandService;
import com.dv.uni.mall.service.UniLabelService;
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
@RequestMapping("uni/label")
@Api(tags = "标签")
public class UniLabelController extends AbstractController<UniLabel,String> implements BaseController<UniLabel,String> {
    private UniLabelService service;
    public UniLabelController(UniLabelService service) {
        super(service);
        this.service = service;
    }

    @Override
    public UniLabelService getService() {
        return service;
    }
}
