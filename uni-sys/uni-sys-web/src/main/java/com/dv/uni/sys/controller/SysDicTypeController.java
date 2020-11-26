package com.dv.uni.sys.controller;

import com.dv.uni.commons.annotations.Config;
import com.dv.uni.commons.annotations.IgnoreAuth;
import com.dv.uni.commons.controller.AbstractController;
import com.dv.uni.commons.entity.Result;
import com.dv.uni.commons.service.BaseService;
import com.dv.uni.sys.entity.SysDicType;
import com.dv.uni.sys.service.SysDicTypeService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Author 张盼
 * @Email zhangpan_soft@163.com
 * @Blog zhangpan_soft
 * @Date 2020/9/18 0018
 */
@RestController
@RequestMapping("sys/dic/type")
@Api(tags = "字典类型")
public class SysDicTypeController extends AbstractController<SysDicType, String> {

    private SysDicTypeService service;

    public SysDicTypeController(SysDicTypeService service) {
        super(service);
        this.service = service;
    }

    @Override
    public BaseService<SysDicType, String> getService() {
        return this.service;
    }

    @Override
    @IgnoreAuth
    public Result findAll(SysDicType entity) {
        return Result.ok(this.getService().findAll(entity));
    }

    @Override
    @IgnoreAuth
    public Result findAll(SysDicType entity, int pageNum, int pageSize) {
        return Result.ok(this.getService().findAll(entity, pageNum, pageSize));
    }

    @Override
    @IgnoreAuth
    public Result findById(String id) {
        return Result.ok(this.getService().findById(id));
    }

    @Override
    @Config(key = "dicInsertScope")
    public Result inserts(List<SysDicType> entities) {
        return Result.ok(this.getService().inserts(entities));
    }

    @Override
    @Config(key = "dicInsertScope")
    public Result insert(SysDicType entity) {
        return Result.ok(this.getService().insert(entity));
    }
}
