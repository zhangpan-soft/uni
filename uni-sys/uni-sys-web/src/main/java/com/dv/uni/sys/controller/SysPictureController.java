package com.dv.uni.sys.controller;

import com.dv.uni.commons.controller.AbstractController;
import com.dv.uni.commons.controller.BaseController;
import com.dv.uni.commons.service.BaseService;
import com.dv.uni.sys.entity.SysPicture;
import com.dv.uni.sys.service.SysPictureService;
import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author 张盼
 * @Email zhangpan_soft@163.com
 * @Blog zhangpan_soft
 * @Date 2020/10/23 0023
 */
@Api(tags = "图片库")
@ApiSupport(author = "zhangpan_soft")
@RestController
@RequestMapping("fc/picture")
public class SysPictureController extends AbstractController<SysPicture, String> {

    private SysPictureService service;

    public SysPictureController(SysPictureService service) {
        super(service);
        this.service = service;
    }

    @Override
    public SysPictureService getService() {
        return this.service;
    }
}
