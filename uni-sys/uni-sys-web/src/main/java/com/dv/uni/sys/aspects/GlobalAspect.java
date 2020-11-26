package com.dv.uni.sys.aspects;

import com.dv.universal.authc.entity.SysConfig;
import com.dv.universal.authc.service.SysConfigService;
import com.dv.universal.commons.basic.exceptions.BaseException;
import com.dv.universal.commons.basic.models.Status;
import com.dv.universal.commons.basic.models.entity.BaseEntity;
import com.dv.universal.commons.basic.utils.ScopeUtils;
import com.dv.universal.commons.web.annotations.Config;
import com.dv.universal.commons.web.utils.SecurityUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;

/**
 * @Author 张盼
 * @Email zhangpan_soft@163.com
 * @Blog zhangpan_soft
 * @Date 2020/9/16 0016
 */

@Aspect
@Component
public class GlobalAspect {
    @Autowired
    private SysConfigService sysConfigService;

    @Pointcut("@annotation(com.dv.universal.commons.web.annotations.Select)")
    public void findAllAroundPointcut() {
    }

    @Around("findAllAroundPointcut()")
    public Object findAllAround(ProceedingJoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();
        if (args != null && args.length > 0) {
            BaseEntity authentication = SecurityUtils.getAuthentication(SecurityUtils.get());
            for (Object arg : args) {
                if (arg instanceof BaseEntity) {
                    if (!ScopeUtils.contains(authentication.getScope(),"manager")) {
                        try {
                            Field field = arg.getClass()
                                             .getDeclaredField("createBy");
                            field.setAccessible(true);
                            field.set(arg, authentication.getId());
                            field.setAccessible(false);
                        } catch (Exception e) {
                            throw BaseException.of(Status.REFLECT_ERROR, e);
                        }
                    }
                }
            }
        }
        try {
            return args == null || args.length == 0 ? joinPoint.proceed() : joinPoint.proceed(args);
        } catch (Throwable throwable) {
            throw new RuntimeException(throwable);
        }
    }

    @Pointcut("@annotation(com.dv.universal.commons.web.annotations.Config)")
    public void insertBeforePointcut() {
    }

    @Before("insertBeforePointcut()")
    public void insertBefore(JoinPoint joinPoint) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Config config = signature.getMethod()
                                 .getAnnotation(Config.class);
        BaseEntity authentication = SecurityUtils.getAuthentication(SecurityUtils.get());
        SysConfig sysConfig = sysConfigService.findByKey(config.key());
        if (sysConfig!=null){
            String value = sysConfig.getValue();
            String[] split = value.split(",");
            boolean flag = false;
            for (String s : split) {
                if (ScopeUtils.contains(authentication.getScope(),s)) {
                    flag = true;
                    break;
                }
            }
            if (!flag) {
                throw BaseException.of(Status.NO_PERMISSION);
            }
        }

    }
}
