package com.dv.uni.commons.interceptors;

import com.dv.uni.commons.annotations.IgnoreAuth;
import com.dv.uni.commons.annotations.Permission;
import com.dv.uni.commons.enums.Status;
import com.dv.uni.commons.exceptions.BaseException;
import com.dv.uni.commons.utils.SecurityUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Author 张盼
 * @Email zhangpan_soft@163.com
 * @Blog zhangpan_soft
 * @Date 2020/9/11 0011
 */
public class LoginInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
            return true;
        }
        Permission permission = null;
        if (handler instanceof HandlerMethod) {
            IgnoreAuth ignoreAuth = ((HandlerMethod) handler).getMethodAnnotation(IgnoreAuth.class);
            if (ignoreAuth != null)
                return true;
            permission = ((HandlerMethod) handler).getMethodAnnotation(Permission.class);
            if (!SecurityUtils.verify(SecurityUtils.get())) {
                throw BaseException.of(Status.CUSTOM, "token校验失败");
            }
            if (permission != null) {
                if (!SecurityUtils.hasPermission(permission.value())) {
                    throw BaseException.of(Status.NO_PERMISSION);
                }
            }
        }
        return true;
    }
}
