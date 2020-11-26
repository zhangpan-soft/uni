package com.dv.uni.sys.controller;

import com.dv.uni.commons.annotations.IgnoreAuth;
import com.dv.uni.commons.entity.Result;
import com.dv.uni.commons.enums.Status;
import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author 张盼
 * @Email zhangpan_soft@163.com
 * @Blog zhangpan_soft
 * @Date 2020/11/6 0006
 */
@RestController
@RequestMapping("status")
@Api(tags = "状态码")
@ApiSupport(author = "zhangpan_soft")
public class StatusController {

    @GetMapping(value = "all",produces = "application/json")
    @IgnoreAuth
    @ApiOperation("获取所有状态码")
    public Result all(){
        Status[] values = Status.values();
        List<Object[]> list = new ArrayList<>();
        for (Status value : values) {
            list.add(new Object[]{value.getCode(),value.name(),value.getMsg()});
        }
        return Result.ok(list);
    }
}
