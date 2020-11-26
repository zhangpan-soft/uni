package com.dv.uni.commons.utils;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.MimeHeaders;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Field;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.*;

@Slf4j
public final class HttpServletRequestUtil {

    public static String getRequestId() {
        HttpServletRequest request = get();
        if (request == null)
            return null;
        String requestId = request.getHeader("Request-Id");
        if (requestId == null) {
            requestId = request.getParameter("RequestId");
        }
        if (requestId == null) {
            requestId = (String) request.getAttribute("RequestId");
        }
        if (requestId == null) {
            requestId = StringUtils.uuid();
            reflectSetparam(request, "Request-Id", requestId);
        }
        return requestId;
    }


    /**
     * 修改header信息，key-value键值对儿加入到header中
     *
     * @param request
     * @param key
     * @param value
     */
    private static void reflectSetparam(HttpServletRequest request, String key, String value) {
        Class<? extends HttpServletRequest> requestClass = request.getClass();
        System.out.println("request实现类=" + requestClass.getName());
        try {
            Field request1 = requestClass.getDeclaredField("request");
            request1.setAccessible(true);
            Object o = request1.get(request);
            Field coyoteRequest = o.getClass()
                                   .getDeclaredField("coyoteRequest");
            coyoteRequest.setAccessible(true);
            Object o1 = coyoteRequest.get(o);
            System.out.println("coyoteRequest实现类=" + o1.getClass()
                                                       .getName());
            Field headers = o1.getClass()
                              .getDeclaredField("headers");
            headers.setAccessible(true);
            MimeHeaders o2 = (MimeHeaders) headers.get(o1);
            o2.addValue(key)
              .setString(value);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取HttpServletRequest
     *
     * @return
     */
    public static HttpServletRequest get() {
        try {
            ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            HttpServletRequest request = requestAttributes.getRequest();
            return request;
        } catch (Exception e) {
            //            e.printStackTrace();
            return null;
        }
    }

    /**
     * 获取请求头
     *
     * @param key 键
     * @return
     */
    public static String getHeader(String key) {
        return get().getHeader(key);
    }

    /**
     * 将请求实体,转换为map
     *
     * @return
     */
    public static Map<String, Object> requestToMap() {
        HttpServletRequest request = get();
        Map<String, Object> map = new HashMap<String, Object>();
        String method = request.getMethod();
        map.put("method", method);
        String ip = getIpAddr();
        map.put("ip", ip);
        String requestURI = request.getRequestURI();
        map.put("requestURI", requestURI);
        Enumeration<String> headerNames = request.getHeaderNames();
        List<Map> list = new ArrayList<>();
        while (headerNames.hasMoreElements()) {
            String s = headerNames.nextElement();
            list.add(new HashMap() {{
                this.put(s, request.getHeader(s));
            }});
        }
        map.put("headers", list);
        map.put("time", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS").format(new Date()));
        return map;
    }

    /**
     * 将请求实体转换为json
     *
     * @return
     */
    public static String requestToJSON() {
        Map<String, Object> map = requestToMap();
        if (map == null || map.isEmpty())
            return null;
        else
            return JSON.toJSONString(map);
    }

    /**
     * 获取当前网络ip
     *
     * @return
     */
    public static String getIpAddr() {
        HttpServletRequest request = get();
        String ipAddress = request.getHeader("x-forwarded-for");
        log.info("ipAddress1 : " + ipAddress);
        if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader("Proxy-Client-IP");
            log.info("ipAddress2 : " + ipAddress);
        }
        if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader("WL-Proxy-Client-IP");
            log.info("ipAddress3 : " + ipAddress);
        }
        if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getRemoteAddr();
            log.info("ipAddress4 : " + ipAddress);
            if (ipAddress.equals("127.0.0.1") || ipAddress.equals("0:0:0:0:0:0:0:1")) {
                //根据网卡取本机配置的IP
                InetAddress inet = null;
                try {
                    inet = InetAddress.getLocalHost();
                } catch (UnknownHostException e) {
                    e.printStackTrace();
                }
                ipAddress = inet.getHostAddress();
                log.info("ipAddress5 : " + ipAddress);
            }
        }
        //对于通过多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割
        if (ipAddress != null && ipAddress.length() > 15) { //"***.***.***.***".length() = 15
            if (ipAddress.indexOf(",") > 0) {
                ipAddress = ipAddress.substring(0, ipAddress.indexOf(","));
                log.info("ipAddress6 : " + ipAddress);
            }
        }
        return ipAddress;
    }
}
