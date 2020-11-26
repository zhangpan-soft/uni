package com.dv.uni.commons.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import com.dv.uni.commons.authc.AuthcToken;
import com.dv.uni.commons.entity.BaseEntity;
import com.dv.uni.commons.enums.Status;
import com.dv.uni.commons.exceptions.BaseException;
import com.dv.uni.commons.realms.Realm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.util.*;
import java.util.concurrent.TimeUnit;


/**
 * @Author 张盼
 * @Email zhangpan_soft@163.com
 * @Blog zhangpan_soft
 * @Date 2020/9/11 0011
 */
@Component
public class SecurityUtils {

    private static RedisTemplate<String, Object> redisTemplate;
    private static String                        secret;
    private static long                          timeout;

    @PostConstruct
    public void init() {

    }

    @Autowired
    public void setRedisTemplate(RedisTemplate<String, Object> redisTemplate) {
        SecurityUtils.redisTemplate = redisTemplate;
    }

    @Value("${token.secret}")
    public void setSecret(String secret) {
        SecurityUtils.secret = secret;
    }

    @Value("${token.timeout}")
    public void setTimeout(long timeout) {
        SecurityUtils.timeout = timeout;
    }

    public static void logout(){
        delCache("accessToken:" + get());
    }

    public static Map<String, Object> login(AuthcToken token, Realm realm) {
        // 认证
        BaseEntity authentication = realm.authentication(token);
        Assert.notNull(authentication, Status.CUSTOM, "认证不通过");
        // 授权
        Set<String> authorization = realm.authorization(token);
        String sign = sign(token, authentication, authorization);
        return new HashMap<String, Object>() {{
            this.put("sign", sign);
            this.put("authentication", authentication);
            this.put("authorization", authorization);
        }};
    }

    public static String sign(AuthcToken token, BaseEntity authentication, Set<String> authorization) {
        Object principal = token.principal();
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream(); ObjectOutputStream oos = new ObjectOutputStream(baos); ByteArrayOutputStream baos1 = new ByteArrayOutputStream(); ObjectOutputStream oos1 = new ObjectOutputStream(baos1); ByteArrayOutputStream baos2 = new ByteArrayOutputStream(); ObjectOutputStream oos2 = new ObjectOutputStream(baos2); ByteArrayOutputStream baos3 = new ByteArrayOutputStream(); ObjectOutputStream oos3 = new ObjectOutputStream(baos3);) {
            oos.writeObject(principal);
            byte[] bytes = baos.toByteArray();
            String s = Base64.getEncoder()
                             .encodeToString(bytes);
            // 此处过期时间是以毫秒为单位，所以乘以1000
            Date date = new Date(System.currentTimeMillis() + SecurityUtils.timeout * 1000);
            Algorithm algorithm = Algorithm.HMAC256(secret + s);

            // 附带account帐号信息
            oos1.writeObject(token);
            oos2.writeObject(authentication);
            oos3.writeObject(authorization);
            String accessToken = JWT.create()
                                    .withClaim("principal", s)
                                    .withClaim("authcToken", Base64.getEncoder()
                                                                   .encodeToString(baos1.toByteArray()))
                                    .withClaim("authentication", Base64.getEncoder()
                                                                       .encodeToString(baos2.toByteArray()))
                                    .withClaim("authorization", Base64.getEncoder()
                                                                      .encodeToString(baos3.toByteArray()))
                                    .withExpiresAt(date)
                                    .sign(algorithm);
            setCache("accessToken:" + accessToken, accessToken, timeout);
            return accessToken;
        } catch (IOException e) {
            throw BaseException.of(Status.SYSTEM, "签名异常", e);
        }
    }

    public static BaseEntity<? extends Serializable> getAuthentication(String token) {
        if (StringUtils.isEmpty(token)) {
            return null;
        }
        return (BaseEntity<? extends Serializable>) getClaim(token, "authentication");
    }

    public static Set<String> getAuthorization(String token) {
        if (StringUtils.isEmpty(token))
            return null;
        return (Set<String>) getClaim(token, "authorization");
    }

    public static boolean verify(String token) {
        Assert.hasText(token, Status.ACCESS_TOKEN_NOT_EXISTS, "token不存在");
        String cache = (String) getCache("accessToken:" + token);
        Assert.hasText(cache, Status.ACCESS_TOKEN_EXPIRE);
        DecodedJWT jwt = JWT.decode(token);
        // 只能输出String类型，如果是其他类型返回null
        String s = jwt.getClaim("principal")
                      .asString();
        Algorithm algorithm = Algorithm.HMAC256(secret + s);
        JWTVerifier verifier = JWT.require(algorithm)
                                  .build();
        try {
            DecodedJWT verify = verifier.verify(token);
        } catch (JWTVerificationException e) {
            e.printStackTrace();
            Assert.newException(Status.ACCESS_TOKEN_WRONGFUL);
        }
        return true;
    }

    public static boolean hasPermission(String permission) {
        Set<String> authorization = (Set<String>) getClaim(get(), "authorization");
        return authorization.contains(permission);
    }

    /**
     * 获得Token中的信息无需secret解密也能获得
     *
     * @param token
     * @param claim
     * @return java.lang.String
     * @author Wang926454
     * @date 2018/9/7 16:54
     */
    public static Object getClaim(String token, String claim) {
        if (StringUtils.isEmpty(token))
            return null;
        DecodedJWT jwt = JWT.decode(token);
        // 只能输出String类型，如果是其他类型返回null
        String s = jwt.getClaim(claim)
                      .asString();
        byte[] decode = Base64.getDecoder()
                              .decode(s);
        try (ByteArrayInputStream bais = new ByteArrayInputStream(decode); ObjectInputStream ois = new ObjectInputStream(bais);) {
            return ois.readObject();
        } catch (Exception e) {
            throw BaseException.of(Status.SYSTEM, "反序列化异常", e);
        }
    }

    /**
     * 获取请求的token信息
     *
     * @return
     */
    public static String get() {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();
        String token = request.getHeader("access-token");
        if (StringUtils.isEmpty(token)) {
            token = request.getHeader("Authorization");
        }
        if (StringUtils.isEmpty(token)) {
            token = request.getParameter("accessToken");
        }
        return token;
    }

    public static void setCache(String k, Object v) {
        redisTemplate.opsForValue()
                     .set(getK(k), v);
    }

    public static void setCache(String k, Object v, long timeout) {
        redisTemplate.opsForValue()
                     .set(getK(k), v, timeout, TimeUnit.SECONDS);
    }

    public static Object getCache(String k) {
        return redisTemplate.opsForValue()
                            .get(getK(k));
    }

    private static String getK(String k) {
        return "cache:" + k;
    }

    public static void delCache(String k){
        redisTemplate.delete(getK(k));
    }
}
