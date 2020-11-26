package com.dv.uni.commons.controller;

import com.dv.uni.commons.entity.BaseEntity;
import com.dv.uni.commons.entity.Result;
import com.dv.uni.commons.enums.Status;
import com.dv.uni.commons.exceptions.BaseException;
import com.dv.uni.commons.group.InsertGroup;
import com.dv.uni.commons.group.UpdateGroup;
import com.dv.uni.commons.service.BaseService;
import com.dv.uni.commons.utils.ExcelUtils;
import com.dv.uni.commons.utils.SecurityUtils;
import com.dv.uni.commons.utils.SpringContextHolder;
import com.dv.uni.commons.utils.StringUtils;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import org.springframework.jdbc.core.JdbcTemplate;

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
 * @Date 2020/11/26 0026
 */
public abstract class AbstractController<T extends BaseEntity<ID>, ID extends Serializable> implements BaseController<T,ID> {
    private BaseService<T,ID> service;
    public AbstractController(BaseService<T,ID> service){
        this.service = service;
    }

    public BaseService<T, ID> getService() {
        return service;
    }

    @Override
    public Result insert(T entity) {
        return Result.ok(getService().insert(entity));
    }

    @Override
    public Result inserts(List<T> entities) {
        validateCollection(entities, InsertGroup.class);
        return Result.ok(getService().inserts(entities));
    }

    @Override
    public Result delete(@Valid @Length(max = 32) ID id) {
        return Result.ok(getService().delete(id));
    }

    @Override
    public Result deletes(List<ID> ids) {
        if (ids == null || ids.isEmpty()) {
            throw BaseException.of(Status.REQUEST_PARAM_ERROR, "参数为空");
        }
        ids.forEach(id -> {
            if (id == null || id.toString()
                                .length() > 32) {
                throw BaseException.of(Status.REQUEST_PARAM_ERROR, "id不能超过32为");
            }
        });
        return Result.ok(getService().deletes(ids));
    }

    @Override
    public Result update(T entity) {
        return Result.ok(getService().update(entity));
    }

    @Override
    public Result updates(List<T> entities) {
        validateCollection(entities, UpdateGroup.class);
        BaseEntity authentication = (BaseEntity) SecurityUtils.getClaim(SecurityUtils.get(), "authentication");
        return Result.ok(getService().updates(entities.stream()
                                                      .filter(s -> !StringUtils.isEmpty((String) s.getId()))
                                                      .map(s -> {
                                                          if (authentication != null) {
                                                              s.setUpdateBy((ID) authentication.getId());
                                                          }
                                                          s.setUpdateDate(new Date());
                                                          return s;
                                                      })
                                                      .collect(Collectors.toList())));
    }

    @Override
    public Result findById(@Valid @Length(max = 32) ID id) {
        return Result.ok(getService().findById(id));
    }

    @Override
    public Result findAll(T entity) {
        return Result.ok(getService().findAll(entity));
    }

    @Override
    public Result findAll(T entity, @Valid @Max(Integer.MAX_VALUE) int pageNum, @Valid @Max(2000) int pageSize) {
        return Result.ok(getService().findAll(entity, pageNum, pageSize));
    }

    @Override
    public Result export(T entity, String templateCode, HttpServletResponse response) {
        JdbcTemplate jdbcTemplate = SpringContextHolder.getApplicationContext()
                                                       .getBean(JdbcTemplate.class);
        Map<String, Object> templateMap = jdbcTemplate.queryForMap("SELECT id,class_name className,details FROM sys_template WHERE code=?", templateCode);
        ExcelUtils.export(templateMap, getService().findAll(entity), response);
        return null;
    }

    private void validateCollection(Collection<T> collection, Class group) {
        if (collection == null || collection.isEmpty()) {
            throw BaseException.of(Status.REQUEST_PARAM_ERROR, "数据为空");
        }
        ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
        Validator validator = validatorFactory.getValidator();
        collection.forEach(entity -> {
            Set<ConstraintViolation<T>> validate = validator.validate(entity, InsertGroup.class);
            if (!(validate == null || validate.isEmpty())) {
                validate.forEach(tConstraintViolation -> {
                    throw BaseException.of(Status.REQUEST_PARAM_ERROR, "参数[" + tConstraintViolation.getPropertyPath() + "]:" + tConstraintViolation.getMessage());
                });
            }
        });
    }
}
