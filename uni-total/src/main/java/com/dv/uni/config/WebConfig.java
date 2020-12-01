package com.dv.uni.config;

import com.dv.uni.commons.filters.GlobalFilter;
import com.dv.uni.commons.interceptors.LoginInterceptor;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @Author 张盼
 * @Email zhangpan_soft@163.com
 * @Blog zhangpan_soft
 * @Date 2020/9/14 0014
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowCredentials(true)
                .allowedMethods("OPTIONS", "POST", "PUT", "GET", "DELETE")
                .allowedHeaders("*")
                .allowedOrigins("*")
                .maxAge(3600);
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginInterceptor())
                .addPathPatterns("/**")
                .excludePathPatterns("/doc.html", "/swagger-resources/**", "/webjars/**", "/v2/**", "/swagger-ui.html/**", "/csrf", "/error", "/favicon.ico")
                .excludePathPatterns("/admin/login", "/user/login", "/status", "/druid/**");
    }

    @Bean
    public FilterRegistrationBean<GlobalFilter> globalFilter() {
        FilterRegistrationBean<GlobalFilter> filterRegistrationBean = new FilterRegistrationBean<>(new GlobalFilter());
        filterRegistrationBean.addUrlPatterns("/**");
        filterRegistrationBean.setOrder(1);
        return filterRegistrationBean;
    }
}
