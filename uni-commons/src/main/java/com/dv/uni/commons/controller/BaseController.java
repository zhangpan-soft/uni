package com.dv.uni.commons.controller;

import com.dv.uni.commons.entity.BaseEntity;
import com.dv.uni.commons.entity.Result;
import com.dv.uni.commons.enums.Status;
import com.dv.uni.commons.exceptions.BaseException;
import com.dv.uni.commons.group.FindGroup;
import com.dv.uni.commons.group.InsertGroup;
import com.dv.uni.commons.group.UpdateGroup;
import com.dv.uni.commons.service.BaseService;
import com.dv.uni.commons.utils.ExcelUtils;
import com.dv.uni.commons.utils.SecurityUtils;
import com.dv.uni.commons.utils.SpringContextHolder;
import com.dv.uni.commons.utils.StringUtils;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import io.swagger.annotations.ApiOperation;
import org.hibernate.validator.constraints.Length;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.*;
import javax.validation.constraints.Max;
import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @Author 张盼
 * @Email zhangpan_soft@163.com
 * @Blog zhangpan_soft
 * @Date 2020/9/11 0011
 */
@ApiSupport(author = "zhangpan_soft")
public interface BaseController<T extends BaseEntity<ID>, ID extends Serializable> {


    @ApiOperation("新增")
    @PostMapping(value = "insert", consumes = {
            "application/json"
    }, produces = {
            "application/json"
    })
    @ApiOperationSupport(author = "zhangpan_soft", ignoreParameters = {
            "id",
            "createBy",
            "createDate",
            "createDate_scope",
            "updateBy",
            "updateDate_scope",
            "updateDate"
    })
    Result insert(@Validated(InsertGroup.class) @RequestBody T entity);

    @ApiOperation("批量新增")
    @PostMapping(value = "inserts", consumes = {
            "application/json"
    }, produces = {
            "application/json"
    })
    @ApiOperationSupport(author = "zhangpan_soft")
    Result inserts(@RequestBody List<T> entities);

    @ApiOperation("删除")
    @DeleteMapping(value = "delete/{id}", produces = {
            "application/json"
    })
    @ApiOperationSupport(author = "zhangpan_soft")
    Result delete(@Valid @Length(max = 32) @PathVariable ID id);

    @ApiOperation("批量删除")
    @PostMapping(value = "deletes", consumes = {
            "application/json"
    }, produces = {
            "application/json"
    })
    @ApiOperationSupport(author = "zhangpan_soft")
    Result deletes(@RequestBody List<ID> ids);

    @ApiOperation("修改")
    @PutMapping(value = "update", consumes = {
            "application/json"
    }, produces = {
            "application/json"
    })
    @ApiOperationSupport(author = "zhangpan_soft", ignoreParameters = {
            "createBy",
            "createDate",
            "createDate_scope",
            "updateBy",
            "updateDate_scope",
            "updateDate"
    })
    Result update(@Validated(UpdateGroup.class) @RequestBody T entity) ;


    @ApiOperation("批量修改")
    @PutMapping(value = "updates", consumes = {
            "application/json"
    }, produces = {
            "application/json"
    })
    @ApiOperationSupport(author = "zhangpan_soft")
    Result updates(@RequestBody List<T> entities) ;

    @GetMapping(value = "find/{id}", produces = {
            "application/json"
    })
    @ApiOperation("根据id查询")
    @ApiOperationSupport(author = "zhangpan_soft")
    Result findById(@Valid @Length(max = 32) @PathVariable ID id);

    @PostMapping(value = "find", consumes = {
            "application/json"
    }, produces = {
            "application/json"
    })
    @ApiOperation("条件查询,不分页")
    @ApiOperationSupport(author = "zhangpan_soft")
    Result findAll(@Validated(FindGroup.class) @RequestBody T entity);

    @PostMapping(value = "find/{pageNum}/{pageSize}", consumes = {
            "application/json"
    }, produces = {
            "application/json"
    })
    @ApiOperationSupport(author = "zhangpan_soft")
    @ApiOperation("条件查询,分页")
    Result findAll(@Validated(FindGroup.class) @RequestBody T entity, @Valid @Max(Integer.MAX_VALUE) @PathVariable int pageNum, @Valid @Max(2000) @PathVariable int pageSize);

    @PostMapping(value = "export/{templateCode}", consumes = "application/json")
    @ApiOperation("excel导出")
    Result export(@RequestBody T entity, @PathVariable String templateCode, HttpServletResponse response);

}
