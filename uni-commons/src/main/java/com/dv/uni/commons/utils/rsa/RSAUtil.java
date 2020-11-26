package com.dv.uni.commons.utils.rsa;

/**
 * @Author 张盼
 * @Email zhangpan_soft@163.com
 * @Blog zhangpan_soft
 * @Date 2020/9/10 0010
 */

import org.apache.commons.codec.binary.Base64;

import javax.crypto.Cipher;
import java.io.IOException;
import java.io.InputStream;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Properties;

public class RSAUtil {
    public static String CLIENT_PUB_KEY = null;
    public static String SERVER_PUB_KEY = null;
    public static String SERVER_PRI_KEY = null;

    static {
        try (InputStream is = RSAUtil.class.getClassLoader()
                                           .getResourceAsStream("rsa.properties")) {
            Properties properties = new Properties();
            properties.load(is);
            CLIENT_PUB_KEY = properties.getProperty("clintPublicKey");
            SERVER_PUB_KEY = properties.getProperty("serverPublicKey");
            SERVER_PRI_KEY = properties.getProperty("serverPrivateKey");
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    //private static Map<Integer, String> keyMap = new HashMap<Integer, String>();  //用于封装随机产生的公钥与私钥

    public static void main(String[] args) throws Exception {
        //生成公钥和私钥
/*        String[] keys = genKeyPair();
        System.out.println("公钥 : \n"+keys[0]);
        System.out.println(keys[0].length());
        System.out.println("私钥 : \n"+keys[1]);
        System.out.println(keys[1].length());
        //加密字符串
        String message = "df7238201111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111";
        System.out.println("待加密源 : \n" + message);
        //System.out.println();
        String s0 = encryptPublic(message, keys[0]);
        System.out.println("公钥加密之后 : \n" + s0);
        System.out.println("私钥解密之后 : \n" + decryptPrivate(s0, keys[1]));
        String s1 = encryptPrivate(message, keys[1]);
        System.out.println("私钥加密之后 : \n" + s1);
        System.out.println("公钥解密之后 : \n" + decryptPublic(s1, keys[0]));*/
        String s = "HlDD2Ca1hh8kc5ldtHqEmWMxT8OZsRAOpwR+4mVbINH/90+1KeEbDUFIf5O/benxOixcwt3CAnRkWJ22HbiOshf2GNhd82kGLIOH0it8FeoWPUnTok56y0JVqL3VxxCSDYWWlHfdNEDHa2lkVADJcWkyrQgqd2ea00+KvBpSvxPLMoLpuBA2c0JKckXgLqIKDR/wqtFgg5jg/kVTm3JJrRyYKLz2e0p/PNlrENNuMw8GG2+DEIRV/KzJPZ69FJKuyVNvInfnYa/ZTLUmzcn9RjP6DgvXShMwJnBuQ2IaTPX0FhhQ8OkEGJMiNIveb8MEsDQGFQ4EZHyIm6HWw9zUDA==";
        System.out.println(decryptPrivate(s, "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCQJ3kWiz3y2fivl3l1dAStBRRwk6vTK6u9oroOD9YIqrSccmh94Ou+Wbvz8TQBwtSGPZcNSJhqQUSPr75HygBJRhRGrNJSO8C6M4NuYKsGz8YP3EcTlZndA55Vfrx0uZdEmOYXUtUtb3IbirWS2WiIhJItu6mSWiJyzVri2sVTthhlB+qaRVoLrki0K7XCHBtFyKQHf22rB5d+dAP3jg3h9NoG+CmOV9h2TT2ujTH5HPJ/AU62ijasSIwlM6z7Agi2F52E8UUGpD5kTIz94H2AlhXtZ/1FPfHQzTo5UoNAEV33QcVuC7A4dxuCUObEKoFZoTlyv7wNSSw8NIfOIugVAgMBAAECggEAPLMiXcfMEhKOkQGrdzWfMmvNK4pC8+yTqIp45artaUDYRHg4X6jyG3kVl6adS5CKhOwySrmi1Dsfb5GFAh2g+Tm1F/11ooLeqIlAcp3p5jhfIhaTJz/7RgFcDowLNutfMtdfK7sJYNRorAhAZNF/Ht0T4IYA5Utm7ghimNud8506etpj84j8VbFeDGX89gOrWHGwKNeGFxaHQaIIZIwuqTtlmoIa2QRTtnVuFwshqTYwO+zCE6kqgivnmoiAMIxWWvxiKOA5LWdqhDoaTCj2P8ae19ZEpYz99Yb7Lx2efcHB1OYPAI/JSORURAEG5VjF0T3UyAv4SlsaCHRqkMGWEQKBgQDbX4Q7pIrRy96Y5+AobjuLPQKfAu5gWZ2XX88ktT7ZjCk79hbWM/aWPmPxbZq4rLHvAnryr9kZASuRgkZwpgbqHyQAhf2mZ5jmCBGSQPnB3wO3F6reyuh4kkkQVDR3o46nS5t2OKI+15SfMCpfwYmYafiL0FB9JiBEjnLyQCXV2wKBgQCoOO+2ZoZS+eLfk0L3wAAqP8sY/rToOVz0R/gVfDpSFOnezLe3XTUrQrLk6COiQuIJ9eRenqa+Mt1cieptaiB/CV0Y68hMAaGz5/gNazLtkfEUczW2NZjKOZwESUxgewA2cRiZb2JNHmlSM5l+miOIoBFpfSwdyDfkfd2b45e0zwKBgAmOfK04es+u6PCSUWKRgsiLDN0ufIH4BXR9uQFpX+aMQ5OYIeCM/PYIm8P9uVrIMywtWHvQC04ajfJV0YnAwdZbKu9W5vjj1HZY7aMIb5jxhTDpuAjioAP4o3QxpuN7XZCOK6SXzQGd1JymtYHcZYkdPLWiio8ZJRi3d+xzvPI3AoGAYar88ifAYTiYEjqLrRAaiG0VW+O14QY9A88tKDxCGBnwVt0A5UMGdaF6ABEPb0vptOLAvnbaVJ9viiTAqNnvGBK1rJxoZEimO6+4gwH0RZ5wG/FwA+RGW1LrVEnCQFnpm7I6GAtlRWUcvQ8cVTbk3pQgx+BW1svCN7UkDzgqgl0CgYEA2oFUkm5IpHbxvMkklplwI6KvbaF+aXGWgEZNvNnI5dYTcjwc9gJRlXlAwo/g+ImEg5l/drKijQUYcgiKozGIU0cZRBIBO26TUFPmqGmWnIREBrxNyLzDqkRKriM8muu7HWjIflCSzBXIO5BcQxTQWDvnsvSU3U6E/73O7pWZ2zw="));
        //System.out.println(encryptPrivate("df723820","MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQDV9OCyNmLz9QDqE+vGTQovEByeAldqDdlx9C/1W6d5mdo0pd/rIowlD7tgZgqdbOdytgdd4VTFdAczY8yghUIa/ni2dSZeWrYpST6kRyh/WFOO9Vegq+PJzwo4nnFSHTsSePQ4qX+W5ZBGmxZvn8jaCtXdqhF4gx2LhTKKZ4663qtsgy+WYlP+zCfeh6Czo6l5lYfge4+zhgLR6aUWwO3jTHwQswCOwNlSLaO8KhkXf4TPN23V2aB22FV2fHY9dxZuZPxVqMLyX5ccGkS26PeGUbwnI/7nqvq+J1Wk8/Wi6eEqCz9SU1JflrKcrInSEietUkDACnwvIaYQT/gHdoVTAgMBAAECggEAGUxECS5fEuNkQUkrtp3DV387Mv+p4FNU46Fwzj0RF1K2t0Tprf6+vV8X07UdCjInR6v4QQR0pogqlcv2FQDMqS5vjXtalwt4wHb1niz/v9bswlmGC+xnGaSOW9V4JrJoGq4MimqYOHpaTc7mnzowYH+pe/Uw7aPrKWUNbcoC2VtlYbXrHuL/wUpqffFK1cqtndQ7yPCJzWS71msffRxpZES5ujUw5BL53FbfoczIonpH0r2IYwWIUO5KxtUvupZxXdr1BjUauHHI9vMwkeG3mstVnz6W/q1xA34o7KhHIXW7SMjA+uIIdtn1ElE6To1o6SI6CzqCGDHnH2qOFqaCMQKBgQDwpH7CKEodyGveMpwS2NoK0Yqf9ppGcDhDCMKS26TgVJ4IG1jyRtLXMlzBZ3pJY79BA4rWSbY0B3D1SDmcJJP1ixOBgFDznnIdv9dUUM4C61TDFXOkQSgcfEeAThYLpX0/OLjj7+SEhYbnp58mF96S1G0wF218IvV7LPfAK7KqCwKBgQDjnGYwJ1wDuRiSmrcZdtXL9zxwvsG2ODgA5U0Q98BcKWuvsM2/FWvYuBcN+5tIAzToJs9ohKRJss+ugZSZSXpv7zvYScrg4ph9mQsn/zhXiGx1GiOkxorOohqf5Gvx7Wf7nqm/EVPmog0FMQlnkR0JBMu4XrmywHxnGmTjNkxm2QKBgQDfKw5LLWYe3MH8nN7VM7pykgWHeAF3FZd3w2X/ICd1y8OLLSF9/mSGIjSXQEnOSe0SdCCLvmx1L/l33/VdcyasbsA2NzPb2rNmF4Wwsgd7+ZbwHLLUP4DdefwtZz1Wq7DNsuL0sIMyy0pjB7a2cyh87vgbw35Lw3f5NajF1UCqJQKBgE9Z90ZScoHZxfdWeP8nruGtECU+W8prTxsA1h1UQnve9OwLd69miHLFu0Pks/4nIArPfP+zPpNzA3STOHs4YrcjcHm3QEOmvAMNmBYZpErgBO/ObR7FGR9w5FdaC0gMvHO8nPE/2UBOvrtQnTa+IKFESsG8RIFNGhHX4dRU2c4BAoGAL3TlLNAOtVU7xzQhi0e3wsneUC+08axWVpudZpTkkuAwvGVw+ZbG2tb9nF/oApbgVskNptP9M9K8VPLhgbplB6hfvvI/OrKRpyWib73RK8y98g/tRvd+vQWqfetnCeY63r1deBKnxCdwmBxKr+Svg+RTx7tggIYp6BAJzWVwWP8="));
    }

    /**
     * 随机生成密钥对
     *
     * @throws NoSuchAlgorithmException
     */
    public static String[] genKeyPair() {
        try {
            // KeyPairGenerator类用于生成公钥和私钥对，基于RSA算法生成对象
            KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance("RSA");
            // 初始化密钥对生成器，密钥大小为96-1024位
            keyPairGen.initialize(2048, new SecureRandom());
            // 生成一个密钥对，保存在keyPair中
            KeyPair keyPair = keyPairGen.generateKeyPair();
            RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();   // 得到私钥


            RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();  // 得到公钥
            String publicKeyString = new String(Base64.encodeBase64(publicKey.getEncoded()));
            // 得到私钥字符串
            String privateKeyString = new String(Base64.encodeBase64((privateKey.getEncoded())));
            // 将公钥和私钥保存到Map
            return new String[]{
                    publicKeyString,
                    privateKeyString
            };
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    /**
     * RSA公钥加密
     *
     * @param str       加密字符串
     * @param publicKey 公钥
     * @return 密文
     * @throws Exception 加密过程中的异常信息
     */
    public static String encryptPublic(String str, String publicKey) {
        try {
            //base64编码的公钥
            byte[] decoded = Base64.decodeBase64(publicKey);
            RSAPublicKey pubKey = (RSAPublicKey) KeyFactory.getInstance("RSA")
                                                           .generatePublic(new X509EncodedKeySpec(decoded));
            //RSA加密
            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.ENCRYPT_MODE, pubKey);
            String outStr = Base64.encodeBase64String(cipher.doFinal(str.getBytes("UTF-8")));
            return outStr;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    /**
     * RSA私钥解密
     *
     * @param str        解密字符串
     * @param privateKey 私钥
     * @return 铭文
     * @throws Exception 解密过程中的异常信息
     */
    public static String decryptPrivate(String str, String privateKey) {
        try {
            //64位解码加密后的字符串
            byte[] inputByte = Base64.decodeBase64(str.getBytes("UTF-8"));
            //base64编码的私钥
            byte[] decoded = Base64.decodeBase64(privateKey);
            RSAPrivateKey priKey = (RSAPrivateKey) KeyFactory.getInstance("RSA")
                                                             .generatePrivate(new PKCS8EncodedKeySpec(decoded));
            //RSA解密
            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.DECRYPT_MODE, priKey);
            String outStr = "";
            outStr = new String(cipher.doFinal(inputByte));
            return outStr;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    /**
     * rsa 私钥加密
     *
     * @param str
     * @param privateKey
     * @return
     * @throws Exception
     */
    public static String encryptPrivate(String str, String privateKey) {
        try {
            //base64编码的公钥
            byte[] decoded = Base64.decodeBase64(privateKey);
            RSAPrivateKey priKey = (RSAPrivateKey) KeyFactory.getInstance("RSA")
                                                             .generatePrivate(new PKCS8EncodedKeySpec(decoded));
            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.ENCRYPT_MODE, priKey);
            String outStr = Base64.encodeBase64String(cipher.doFinal(str.getBytes("UTF-8")));
            return outStr;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    /**
     * rsa 公钥解密
     *
     * @param str
     * @param publicKey
     * @return
     * @throws Exception
     */
    public static String decryptPublic(String str, String publicKey) {
        try {
            //64位解码加密后的字符串
            byte[] inputByte = Base64.decodeBase64(str.getBytes("UTF-8"));
            //base64编码的私钥
            byte[] decoded = Base64.decodeBase64(publicKey);
            RSAPublicKey priKey = (RSAPublicKey) KeyFactory.getInstance("RSA")
                                                           .generatePublic(new X509EncodedKeySpec(decoded));
            //RSA解密
            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.DECRYPT_MODE, priKey);
            String outStr = new String(cipher.doFinal(inputByte));
            return outStr;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
