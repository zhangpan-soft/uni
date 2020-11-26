package com.dv.uni.sys.controller;

import com.dv.uni.commons.annotations.Config;
import com.dv.uni.commons.annotations.IgnoreAuth;
import com.dv.uni.commons.controller.AbstractController;
import com.dv.uni.commons.controller.BaseController;
import com.dv.uni.commons.entity.Result;
import com.dv.uni.commons.service.BaseService;
import com.dv.uni.sys.entity.SysConfig;
import com.dv.uni.sys.service.SysConfigService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Author 张盼
 * @Email zhangpan_soft@163.com
 * @Blog zhangpan_soft
 * @Date 2020/9/14 0014
 */
@RestController
@Api(tags = "系统配置")
@RequestMapping("sys/config")
public class SysConfigController extends AbstractController<SysConfig,String>{
    private SysConfigService service;
    public SysConfigController(SysConfigService service) {
        super(service);
        this.service = service;
    }

    @Override
    public SysConfigService getService() {
        return this.service;
    }

    @Override
    @IgnoreAuth
    public Result findAll(SysConfig entity) {
        return Result.ok(getService().findAll(entity));
    }

    @Override
    @IgnoreAuth
    public Result findAll(SysConfig entity, int pageNum, int pageSize) {
        return Result.ok(getService().findAll(entity, pageNum, pageSize));
    }

    @Override
    @IgnoreAuth
    public Result findById(String id) {
        return Result.ok(getService().findById(id));
    }

    @IgnoreAuth
    @ApiOperation("根据键查询")
    @GetMapping("find/key/{key}")
    public Result findByKey(@PathVariable String key) {
        return Result.ok(getService().findByKey(key));
    }

    @Override
    @Config(key = "configInsertScope")
    public Result insert(SysConfig entity) {
        return Result.ok(this.getService().insert(entity));
    }

    @Override
    @Config(key = "configInsertScope")
    public Result inserts(List<SysConfig> entities) {
        return Result.ok(this.getService().inserts(entities));
    }
}
