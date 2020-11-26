package com.dv.uni.commons.utils.aes;

/**
 * @Author 张盼
 * @Email zhangpan_soft@163.com
 * @Blog zhangpan_soft
 * @Date 2020/9/10 0010
 */

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;

public class AESUtil {
    private static String Algorithm         = "AES";
    private static String AlgorithmProvider = "AES/ECB/PKCS5Padding"; // 算法/模式/补码方式

    public static byte[] encrypt(String src, byte[] key) {
        try {
            SecretKey secretKey = new SecretKeySpec(key, Algorithm);
            Cipher cipher = Cipher.getInstance(AlgorithmProvider);
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            byte[] cipherBytes = cipher.doFinal(src.getBytes(Charset.forName("utf-8")));
            return cipherBytes;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public static String encrypt(String src, String key) {
        try {
            return byteToHexString(encrypt(src, key.getBytes("utf-8")));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public static byte[] decrypt(String src, byte[] key) {
        try {
            SecretKey secretKey = new SecretKeySpec(key, Algorithm);
            Cipher cipher = Cipher.getInstance(AlgorithmProvider);
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            byte[] hexBytes = hexStringToBytes(src);
            byte[] plainBytes = cipher.doFinal(hexBytes);
            return plainBytes;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public static String decrypt(String src, String key) {
        try {
            return new String(decrypt(src, key.getBytes("utf-8")), "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    /**
     * 将byte转换为16进制字符串
     *
     * @param src
     * @return
     */
    public static String byteToHexString(byte[] src) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < src.length; i++) {
            int v = src[i] & 0xff;
            String hv = Integer.toHexString(v);
            if (hv.length() < 2) {
                sb.append("0");
            }
            sb.append(hv);
        }
        return sb.toString();
    }

    /**
     * 将16进制字符串装换为byte数组
     *
     * @param hexString
     * @return
     */
    public static byte[] hexStringToBytes(String hexString) {
        hexString = hexString.toUpperCase();
        int length = hexString.length() / 2;
        char[] hexChars = hexString.toCharArray();
        byte[] b = new byte[length];
        for (int i = 0; i < length; i++) {
            int pos = i * 2;
            b[i] = (byte) (charToByte(hexChars[pos]) << 4 | charToByte(hexChars[pos + 1]));
        }
        return b;
    }

    private static byte charToByte(char c) {
        return (byte) "0123456789ABCDEF".indexOf(c);
    }
}

