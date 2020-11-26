package com.dv.uni.commons.service;

import com.dv.uni.commons.entity.BaseEntity;
import com.dv.uni.commons.entity.SortEntity;
import com.dv.uni.commons.enums.Status;
import com.dv.uni.commons.exceptions.BaseException;
import com.dv.uni.commons.repository.BaseRepository;
import com.dv.uni.commons.utils.Assert;
import com.dv.uni.commons.utils.SecurityUtils;
import com.dv.uni.commons.utils.StringUtils;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.query.criteria.internal.OrderImpl;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;

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
 * @Date 2020/11/26 0026
 */
@Getter
@Setter
public abstract class AbstractService<T extends BaseEntity<ID>,ID extends Serializable> implements BaseService<T,ID> {
    private BaseRepository<T,ID> repository;
    public AbstractService(BaseRepository<T,ID> repository){
        this.repository = repository;
    }

    @Override
    public T insert(T entity) {
        initInsert(entity);
        return this.repository.save(entity);
    }

    @Override
    public List<T> inserts(@NotEmpty List<T> entities) {
        BaseEntity authentication = (BaseEntity) SecurityUtils.getClaim(SecurityUtils.get(), "authentication");
        entities.forEach(entity -> {
            initInsert(entity,authentication);
        });
        return this.repository.saveAll(entities);
    }

    @Override
    public T delete(@NotBlank ID id) {
        Optional<T> optional = getRepository().findById(id);
        if (optional != null && optional.isPresent()) {
            BaseEntity authentication = (BaseEntity) SecurityUtils.getClaim(SecurityUtils.get(), "authentication");
            T t = optional.get();
            t.setDeleted(1);
            if (authentication != null) {
                t.setUpdateBy((ID) authentication.getId());
                t.setUpdateDate(new Date());
            }
            getRepository().save(t);
            return optional.get();
        } else {
            throw BaseException.of(Status.DATA_NOT_EXISTS);
        }
    }

    @Override
    public List<T> deletes(@NotEmpty List<ID> ids) {
        List<T> list = getRepository().findAllById(ids);
        BaseEntity authentication = (BaseEntity) SecurityUtils.getClaim(SecurityUtils.get(), "authentication");
        if (list != null && !list.isEmpty()) {
            list.forEach(l -> {
                l.setDeleted(1);
                if (authentication != null) {
                    l.setUpdateBy((ID) authentication.getId());
                    l.setUpdateDate(new Date());
                }
            });
            getRepository().saveAll(list);
        }
        return list;
    }

    @Override
    public T update(T entity) {
        Assert.hasText((String) entity.getId(), Status.REQUEST_PARAM_ERROR, "id不能为空");
        initUpdate(entity);
        return getRepository().save(entity);
    }

    @Override
    public List<T> updates(@NotEmpty List<T> collect) {
        BaseEntity authentication = (BaseEntity) SecurityUtils.getClaim(SecurityUtils.get(), "authentication");
        collect.forEach(entity->{
            initUpdate(entity,authentication);
        });
        return getRepository().saveAll(collect);
    }

    @Override
    public T findById(@NotBlank ID id) {
        Optional<T> optional = getRepository().findById(id);
        if (optional != null && optional.isPresent()) {
            return optional.get();
        }
        return null;
    }

    @Override
    public List<T> findAll(T entity) {
        return getRepository().findAll(specification(entity));
    }

    @Override
    public Page<T> findAll(T entity, @NotNull @Min(1) int pageNum, @NotNull @Min(1) int pageSize) {
        return getRepository().findAll(specification(entity), PageRequest.of(pageNum - 1, pageSize));
    }

    private Specification<T> specification(T entity){
        return new Specification<T>() {
            @Override
            public Predicate toPredicate(Root<T> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                Field[] fields = entity.getClass()
                                       .getDeclaredFields();
                List<Field> collectScope = Arrays.stream(fields)
                                                 .map(s -> {
                                                     s.setAccessible(true);
                                                     return s;
                                                 })
                                                 .filter(s -> {
                                                     try {
                                                         return s.get(entity) != null && s.get(entity)
                                                                                          .getClass()
                                                                                          .isArray();
                                                     } catch (Exception e) {
                                                         e.printStackTrace();
                                                         throw BaseException.of(Status.UNKNOWN, e);
                                                     }
                                                 })
                                                 .map(s -> {
                                                     s.setAccessible(false);
                                                     return s;
                                                 })
                                                 .collect(Collectors.toList());
                List<Predicate> predicates = Arrays.stream(fields)
                                                   .map(s -> {
                                                       s.setAccessible(true);
                                                       return s;
                                                   })
                                                   .filter(s -> {
                                                       try {
                                                           return !(s.getClass()
                                                                     .isAssignableFrom(Map.class) || s.getType() == Date.class || s.getType() == Integer.class || s.getType() == BigDecimal.class || s.getType() == Long.class || s.getType() == Double.class) && !s.getName()
                                                                                                                                                                                                                                                                    .contains("_") && !s.getName()
                                                                                                                                                                                                                                                                                        .equalsIgnoreCase("sortBy") && !StringUtils.isEmpty((String) s.get(entity));
                                                       } catch (Exception e) {
                                                           throw BaseException.of(Status.UNKNOWN, e);
                                                       }
                                                   })
                                                   .map(s -> {
                                                       s.setAccessible(false);
                                                       return s;
                                                   })
                                                   .collect(Collectors.toList())
                                                   .stream()
                                                   .map(s -> {
                                                       s.setAccessible(true);
                                                       Predicate predicate = null;
                                                       try {
                                                           if (((String) s.get(entity)).startsWith("%") || ((String) s.get(entity)).endsWith("%")) {
                                                               predicate = criteriaBuilder.like(root.<String>get(s.getName()), (String) s.get(entity));
                                                           } else {
                                                               predicate = criteriaBuilder.equal(root.<String>get(s.getName()), s.get(entity));
                                                           }

                                                       } catch (Exception e) {
                                                           e.printStackTrace();
                                                           throw BaseException.of(Status.SYSTEM, e);
                                                       }
                                                       s.setAccessible(false);
                                                       return predicate;

                                                   })
                                                   .collect(Collectors.toList());
                predicates.addAll(Arrays.stream(fields)
                                        .map(s -> {
                                            s.setAccessible(true);
                                            return s;
                                        })
                                        .filter(s -> {
                                            try {
                                                return s.getType() == Integer.class && s.get(entity) != null;
                                            } catch (Exception e) {
                                                throw BaseException.of(Status.UNKNOWN, e);
                                            }
                                        })
                                        .map(s -> {
                                            s.setAccessible(false);
                                            return s;
                                        })
                                        .collect(Collectors.toList())
                                        .stream()
                                        .map(s -> {
                                            s.setAccessible(true);
                                            try {
                                                return criteriaBuilder.equal(root.<String>get(s.getName()), s.get(entity));
                                            } catch (Exception e) {
                                                e.printStackTrace();
                                                throw BaseException.of(Status.SYSTEM, e);
                                            } finally {
                                                s.setAccessible(false);
                                            }
                                        })
                                        .collect(Collectors.toList()));
                Arrays.stream(fields)
                      .map(s -> {
                          s.setAccessible(true);
                          return s;
                      })
                      .filter(s -> {
                          return s.getClass()
                                  .isAssignableFrom(Map.class) && !s.getName()
                                                                    .equalsIgnoreCase("sortBy");
                      })
                      .map(s -> {
                          s.setAccessible(false);
                          return s;
                      })
                      .collect(Collectors.toList())
                      .forEach(s -> {
                          s.setAccessible(true);
                          try {
                              Map<String, Object> temp = (Map<String, Object>) s.get(entity);
                              temp.forEach((k, v) -> {
                                  predicates.add(criteriaBuilder.equal(criteriaBuilder.function("JSON_EXTRACT", String.class, root.get(s.getName()), criteriaBuilder.literal(k)), v));
                              });
                          } catch (Exception e) {
                              e.printStackTrace();
                              throw BaseException.of(Status.REFLECT_ERROR, e);
                          }
                          s.setAccessible(false);
                      });
                collectScope.forEach(s -> {
                    s.setAccessible(true);
                    Object o = null;
                    try {
                        o = s.get(entity);
                    } catch (Exception e) {
                        throw BaseException.of(Status.REFLECT_ERROR, e);
                    }
                    if (o != null) {
                        /*Object[] oo = (Object[]) o;
                        if (oo.length==1){
                            criteriaBuilder.greaterThanOrEqualTo(root.<Object>get(s.getName().split("_")[0]),(Object) oo[0]);
                        }*/
                        if (o instanceof Date[]) {
                            Date[] oo = (Date[]) o;
                            predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get(s.getName()
                                                                                          .split("_")[0]), oo[0]));
                            if (oo.length > 1) {
                                predicates.add(criteriaBuilder.lessThan(root.get(s.getName()
                                                                                  .split("_")[0]), oo[1]));
                            }
                        }
                        if (o instanceof Integer[]) {
                            Integer[] oo = (Integer[]) o;
                            predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get(s.getName()
                                                                                          .split("_")[0]), oo[0]));
                            if (oo.length > 1) {
                                predicates.add(criteriaBuilder.lessThan(root.get(s.getName()
                                                                                  .split("_")[0]), oo[1]));
                            }
                        }
                        if (o instanceof BigDecimal[]) {
                            BigDecimal[] oo = (BigDecimal[]) o;
                            predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get(s.getName()
                                                                                          .split("_")[0]), oo[0]));
                            if (oo.length > 1) {
                                predicates.add(criteriaBuilder.lessThan(root.get(s.getName()
                                                                                  .split("_")[0]), oo[1]));
                            }
                        }
                        if (o instanceof Long[]) {
                            Long[] oo = (Long[]) o;
                            predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get(s.getName()
                                                                                          .split("_")[0]), oo[0]));
                            if (oo.length > 1) {
                                predicates.add(criteriaBuilder.lessThan(root.get(s.getName()
                                                                                  .split("_")[0]), oo[1]));
                            }
                        }
                        if (o instanceof Double[]) {
                            Double[] oo = (Double[]) o;
                            predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get(s.getName()
                                                                                          .split("_")[0]), oo[0]));
                            if (oo.length > 1) {
                                predicates.add(criteriaBuilder.lessThan(root.get(s.getName()
                                                                                  .split("_")[0]), oo[1]));
                            }
                        }
                        if (o instanceof BigInteger[]) {
                            BigInteger[] oo = (BigInteger[]) o;
                            predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get(s.getName()
                                                                                          .split("_")[0]), oo[0]));
                            if (oo.length > 1) {
                                predicates.add(criteriaBuilder.lessThan(root.get(s.getName()
                                                                                  .split("_")[0]), oo[1]));
                            }
                        }

                    }
                    s.setAccessible(false);
                });
                criteriaQuery.where(predicates.toArray(new Predicate[predicates.size()]));
                Object o = null;
                try {
                    Field sortBy = entity.getClass()
                                         .getDeclaredField("sortBy");
                    sortBy.setAccessible(true);
                    o = sortBy.get(entity);
                    sortBy.setAccessible(false);
                } catch (Exception e) {
                    e.printStackTrace();
                    throw BaseException.of(Status.REFLECT_ERROR, e);
                }
                if (o != null) {
                    List<SortEntity> list = (List<SortEntity>) o;
                    if (!list.isEmpty()) {
                        List<Order> orders = list.stream()
                                                 .map(s -> {
                                                     return new OrderImpl(root.get(s.getProperty()), s.getIsAsc());
                                                 })
                                                 .collect(Collectors.toList());
                        if (orders != null && !orders.isEmpty()) {
                            criteriaQuery.orderBy(orders);
                        }
                    }
                }
                return criteriaQuery.getRestriction();
            }
        };
    }

    private void initInsert(T entity){
        if(entity.getId()==null || StringUtils.isEmpty(String.valueOf(entity.getId()))){
            entity.setId((ID) StringUtils.uuid());
        }
        if (entity.getCreateDate()==null){
            entity.setCreateDate(new Date());
        }
        entity.setUpdateDate(new Date());
        BaseEntity authentication = (BaseEntity) SecurityUtils.getClaim(SecurityUtils.get(), "authentication");
        if (authentication!=null){
            entity.setUpdateBy((ID) authentication.getId());
            if (entity.getCreateBy()==null||StringUtils.isEmpty(String.valueOf(entity.getCreateBy()))){
                entity.setCreateBy((ID) authentication.getId());
            }
        }
    }

    private void initInsert(T entity,BaseEntity authentication){
        if (entity.getId()==null||StringUtils.isEmpty(String.valueOf(entity.getId()))){
            entity.setId((ID) StringUtils.uuid());
        }
        if (entity.getCreateDate()==null){
            entity.setCreateDate(new Date());
        }
        entity.setUpdateDate(new Date());
        if (authentication!=null){
            entity.setUpdateBy((ID) authentication.getId());
            if (entity.getCreateBy()==null||StringUtils.isEmpty(String.valueOf(entity.getCreateBy()))){
                entity.setCreateBy((ID) authentication.getId());
            }
        }
    }

    private void initUpdate(T entity){
        entity.setUpdateDate(new Date());
        BaseEntity authentication = (BaseEntity) SecurityUtils.getClaim(SecurityUtils.get(), "authentication");
        if (authentication != null) {
            entity.setUpdateBy((ID) authentication.getId());
        }
    }

    private void initUpdate(T entity,BaseEntity authentication){
        entity.setUpdateDate(new Date());
        if (authentication != null) {
            entity.setUpdateBy((ID) authentication.getId());
        }
    }
}
