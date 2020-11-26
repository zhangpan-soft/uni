package com.dv.uni.commons.utils.md5;

import com.dv.uni.commons.utils.StringUtils;
import org.apache.commons.codec.digest.DigestUtils;

/**
 * @Author 张盼
 * @Email zhangpan_soft@163.com
 * @Blog zhangpan_soft
 * @Date 2020/9/14 0014
 */
public class MD5Util {
    public static String md5(String plaintext) {
        return DigestUtils.md5Hex(DigestUtils.md5Hex(plaintext));
    }

    public static String md5(String plaintext, String salt) {
        return md5(plaintext + salt);
    }

    /**
     * 校验MD5
     *
     * @param plaintext  明文
     * @param ciphertext 密文
     * @return
     */
    public static boolean verify(String plaintext, String ciphertext) {
        return md5(plaintext).equals(ciphertext);
    }

    public static boolean verify(String plaintext, String ciphertext, String salt) {
        return md5(plaintext, salt).equals(ciphertext);
    }

    public static void main(String[] args) {
        String salt = StringUtils.salt(6);
        System.out.println(salt);
        System.out.println(md5(123123 + salt));
    }
}
