package com.dv.uni.commons.config;

import com.alibaba.fastjson.JSON;
import com.dv.uni.commons.entity.Result;
import com.dv.uni.commons.enums.Status;
import com.dv.uni.commons.exceptions.BaseException;
import com.dv.uni.commons.utils.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.io.PrintWriter;
import java.util.List;
import java.util.stream.Collectors;

@Component
@Slf4j
public class GlobalExceptionResolver implements HandlerExceptionResolver {

    @Autowired
    private MessageSource messageSource;

    @Override
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        ex.printStackTrace();
        log.error("{}", JSON.toJSONString(ex));
        response.setStatus(200);
        response.setContentType("application/json;charset=utf-8");
        response.setCharacterEncoding("utf-8");
        boolean encrypted = StringUtils.isEmpty(request.getHeader("encrypted")) ? false : Boolean.valueOf(request.getHeader("encrypted"));
        response.setHeader("encrypted",encrypted+"");
        Result builder = null;
        if (ex instanceof BaseException) {
//            builder = Result.fail(((BaseException) ex).getStatus(),)
            builder = Result.fail(((BaseException) ex).getStatus(), StringUtils.isEmpty(ex.getMessage()) ? ex.getLocalizedMessage() : ex.getMessage(), encrypted);
        } else if (ex instanceof BindException) {
            List<String> list = ((BindException) ex).getBindingResult()
                                                    .getAllErrors()
                                                    .stream()
                                                    .map(ObjectError::getDefaultMessage)
                                                    .collect(Collectors.toList());
            builder = Result.fail(Status.REQUEST_PARAM_ERROR, list, encrypted);
        } else if (ex instanceof ConstraintViolationException) {
            List<String> collect = ((ConstraintViolationException) ex).getConstraintViolations()
                                                                      .stream()
                                                                      .map(ConstraintViolation::getMessage)
                                                                      .collect(Collectors.toList());
            builder = Result.fail(Status.REQUEST_PARAM_ERROR, collect, encrypted);
        } else {
            builder = Result.fail(Status.UNKNOWN, StringUtils.isEmpty(ex.getMessage()) ? ex.getLocalizedMessage() : ex.getMessage(), encrypted);
        }
        try (PrintWriter pw = response.getWriter()) {
            pw.print(JSON.toJSONString(builder));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ModelAndView();
    }
}
