package com.dv.uni.commons.filters;

import com.dv.uni.commons.ModifyBodyHttpServletRequestWrapper;
import com.dv.uni.commons.enums.Status;
import com.dv.uni.commons.exceptions.BaseException;
import com.dv.uni.commons.utils.StringUtils;
import com.dv.uni.commons.utils.aes.AESUtil;
import com.dv.uni.commons.utils.rsa.RSAUtil;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.Map;

/**
 * @Author 张盼
 * @Email zhangpan_soft@163.com
 * @Blog zhangpan_soft
 * @Date 2020/9/11 0011
 */
public class GlobalFilter implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        String method = request.getMethod();
        if ("POST".equalsIgnoreCase(method) || "PUT".equalsIgnoreCase(method)) {
            if (!StringUtils.isEmpty(request.getHeader("encrypted")) && Boolean.valueOf(request.getHeader("encrypted"))) {
                if (!StringUtils.isEmpty(request.getContentType()) && (request.getContentType()
                                                                              .toLowerCase()
                                                                              .contains("application/json") || request.getContentType()
                                                                                                                      .toLowerCase()
                                                                                                                      .contains("application/xml"))) {
                    StringBuffer sb = new StringBuffer();
                    try (BufferedReader reader = request.getReader()) {
                        String str;

                        while ((str = reader.readLine()) != null) {
                            sb.append(str);
                        }
                    } catch (Exception e) {
                        throw BaseException.of(Status.REQUEST_PARSE_ERROR, e);
                    }
                    Map<String, String> stringStringMap;
                    if (request.getContentType()
                               .toLowerCase()
                               .contains("application/json")) {
                        ObjectMapper om = new ObjectMapper();
                        stringStringMap = om.readValue(sb.toString(), new TypeReference<Map<String, String>>() {
                        });

                    } else {
                        XmlMapper xm = new XmlMapper();
                        stringStringMap = xm.readValue(sb.toString(), new TypeReference<Map<String, String>>() {
                        });
                    }
                    String key = RSAUtil.decryptPrivate(stringStringMap.get("key"), RSAUtil.CLIENT_PUB_KEY);
                    String body = AESUtil.decrypt(stringStringMap.get("body"), key);
                    ModifyBodyHttpServletRequestWrapper wrapper = new ModifyBodyHttpServletRequestWrapper(request, body);
                    filterChain.doFilter(wrapper, response);
                    return;
                }
            }
        }
        filterChain.doFilter(request, response);
    }


}
