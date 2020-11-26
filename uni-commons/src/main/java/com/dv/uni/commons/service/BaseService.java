package com.dv.uni.commons.service;

import com.dv.uni.commons.entity.BaseEntity;
import com.dv.uni.commons.entity.SortEntity;
import com.dv.uni.commons.enums.Status;
import com.dv.uni.commons.exceptions.BaseException;
import com.dv.uni.commons.group.FindGroup;
import com.dv.uni.commons.group.InsertGroup;
import com.dv.uni.commons.group.UpdateGroup;
import com.dv.uni.commons.repository.BaseRepository;
import com.dv.uni.commons.utils.Assert;
import com.dv.uni.commons.utils.SecurityUtils;
import com.dv.uni.commons.utils.StringUtils;
import org.hibernate.query.criteria.internal.OrderImpl;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import javax.persistence.criteria.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @Author 张盼
 * @Email zhangpan_soft@163.com
 * @Blog zhangpan_soft
 * @Date 2020/9/11 0011
 */
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public interface BaseService<T extends BaseEntity<ID>, ID extends Serializable> {

    @Transactional
    T insert(@Validated(InsertGroup.class) T entity) ;

    BaseRepository<T, ID> getRepository();

    @Transactional
    List<T> inserts(@Validated(InsertGroup.class) @NotEmpty List<T> entities);

    @Transactional
    T delete(@NotBlank ID id) ;

    @Transactional
    List<T> deletes(@NotEmpty List<ID> ids);

    @Transactional
    T update(@Validated(UpdateGroup.class) T entity);


    @Transactional
    List<T> updates(@Validated(UpdateGroup.class) @NotEmpty List<T> collect) ;

    T findById(@NotBlank ID id) ;

    List<T> findAll(@Validated(FindGroup.class) T entity) ;


    Page<T> findAll(@Validated(FindGroup.class) T entity, @NotNull @Min(1) int pageNum, @NotNull @Min(1) int pageSize) ;


}
