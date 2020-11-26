package com.dv.uni.commons.utils;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Author 张盼
 * @Email zhangpan_soft@163.com
 * @Blog zhangpan_soft
 * @Date 2020/9/10 0010
 */
public class StringUtils {
    /**
     * 生成uuid去下划线
     *
     * @return
     */
    public static String uuid() {
        return UUID.randomUUID()
                   .toString()
                   .replaceAll("-", "");
    }

    /**
     * 字符串是否为空
     *
     * @param s
     * @return
     */
    public static boolean isEmpty(String s) {

        if (s == null)
            return true;
        return s.matches("\\s*");
    }

    /**
     * 不为空
     *
     * @param s
     * @return
     */
    public static boolean notEmpty(String s) {
        return !isEmpty(s);
    }

    /**
     * 是否有长度
     *
     * @param str
     * @return
     */
    public static boolean hasLength(String str) {
        return (str != null && !str.isEmpty());
    }

    /**
     * 是否有字符
     *
     * @param str
     * @return
     */
    public static boolean hasText(String str) {
        return (str != null && !str.isEmpty() && containsText(str));
    }

    private static boolean containsText(CharSequence str) {
        int strLen = str.length();
        for (int i = 0; i < strLen; i++) {
            if (!Character.isWhitespace(str.charAt(i))) {
                return true;
            }
        }
        return false;
    }

    /**
     * 下划线转驼峰
     *
     * @param s
     * @param smallCamel
     * @return
     */
    public static String underline2Hump(String s, boolean smallCamel) {
        if (s == null || "".equals(s)) {
            return "";
        }
        StringBuffer sb = new StringBuffer();
        Pattern pattern = Pattern.compile("([A-Za-z\\d]+)(_)?");
        Matcher matcher = pattern.matcher(s);
        while (matcher.find()) {
            String word = matcher.group();
            sb.append(smallCamel && matcher.start() == 0 ? Character.toLowerCase(word.charAt(0)) : Character.toUpperCase(word.charAt(0)));
            int index = word.lastIndexOf('_');
            if (index > 0) {
                sb.append(word.substring(1, index)
                              .toLowerCase());
            } else {
                sb.append(word.substring(1)
                              .toLowerCase());
            }
        }
        return sb.toString()
                 .toUpperCase();
    }

    /**
     * 驼峰转下划线
     *
     * @param s
     * @return
     */
    public static String hump2Underline(String s) {
        if (s == null || "".equals(s)) {
            return "";
        }
        s = String.valueOf(s.charAt(0))
                  .toUpperCase()
                  .concat(s.substring(1));
        StringBuffer sb = new StringBuffer();
        Pattern pattern = Pattern.compile("[A-Z]([a-z\\d]+)?");
        Matcher matcher = pattern.matcher(s);
        while (matcher.find()) {
            String word = matcher.group();
            sb.append(word.toUpperCase());
            sb.append(matcher.end() == s.length() ? "" : "_");
        }
        return sb.toString();
    }

    /**
     * 去空格
     *
     * @param s
     * @return
     */
    public static String removeSpace(String s) {
        return s.replaceAll("\\s+", "");
    }

    /**
     * 小数点转/
     *
     * @param s
     * @return
     */
    public static String spot2Slash(String s) {
        return s.replaceAll("[.]", "/");
    }

    /**
     * 首字母大写
     *
     * @param str
     * @return
     */
    public static String upperCaseFirst(String str) {
        return str.substring(0, 1)
                  .toUpperCase() + str.substring(1);
    }

    /**
     * 首字母小写
     *
     * @param str
     * @return
     */
    public static String lowerCaseFirst(String str) {
        return str.substring(0, 1)
                  .toLowerCase() + str.substring(1);
    }

    /**
     * 获取get方法名
     *
     * @param name
     * @return
     */
    public static String getMethodName(String name) {
        return "get" + upperCaseFirst(name);
    }

    /**
     * 获取set方法名
     *
     * @param name
     * @return
     */
    public static String setMethodName(String name) {
        return "set" + upperCaseFirst(name);
    }

    static Character[] sysbol = new Character[]{
            'A',
            'B',
            'C',
            'D',
            'E',
            'F',
            'G',
            'H',
            'I',
            'J',
            'K',
            'L',
            'M',
            'N',
            'O',
            'P',
            'Q',
            'R',
            'S',
            'T',
            'U',
            'V',
            'W',
            'X',
            'Y',
            'Z',
            'a',
            'b',
            'c',
            'd',
            'e',
            'f',
            'g',
            'h',
            'i',
            'j',
            'k',
            'l',
            'm',
            'n',
            'o',
            'p',
            'q',
            'r',
            's',
            't',
            'u',
            'v',
            'w',
            'x',
            'y',
            'z',
            '0',
            '1',
            '2',
            '3',
            '4',
            '5',
            '6',
            '7',
            '8',
            '9',
            '.',
            '@',
            '#',
            '*',
            '_',
            '(',
            ')'
    };

    static Random random = new Random();

    /**
     * 生成盐
     *
     * @param permit 位数
     * @return
     */
    public static String salt(int permit) {
        StringBuilder sb = new StringBuilder();
        if (permit < 1)
            permit = 1;
        int length = sysbol.length;
        for (int i = 0; i < permit; i++) {
            sb.append(sysbol[random.nextInt(length)]);
        }
        return sb.toString();
    }

    /**
     * 生成盐
     *
     * @param permit
     * @param pos
     * @return
     */
    public static String salt(int permit, int pos) {
        if (pos <= 0)
            return salt(permit);
        if (permit < 1)
            permit = 1;
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < permit; i++) {
            sb.append(sysbol[random.nextInt(pos)]);
        }
        return sb.toString();
    }

    /**
     * 生成验证码
     *
     * @param permit 验证码位数
     * @return
     */
    public static String verficatyCode(int permit) {
        if (permit < 1)
            permit = 1;
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < permit; i++) {
            sb.append(sysbol[random.nextInt(10) + 52]);
        }
        return sb.toString();
    }

    /**
     * 获取扩展名
     *
     * @param filename
     * @return
     */
    public static String getFileExt(String filename) {
        return filename.substring(filename.lastIndexOf(".") + 1);
    }

    /**
     * @param url 网址
     * @return
     */
    public static boolean isStandardWebSite(String url) {
        return url.matches("^([hH][tT]{2}[pP]:\\/\\/|[hH][tT]{2}[pP][sS]:\\/\\/)(([A-Za-z0-9-~]+)\\.)+([A-Za-z0-9-~\\/])+$");
    }

    /**
     * 是否不是空白字符串
     *
     * @param cs
     * @return
     */
    public static boolean isNotBlank(CharSequence cs) {
        return !isBlank(cs);
    }

    /**
     * 是否是空白字符串
     *
     * @param cs
     * @return
     */
    public static boolean isBlank(CharSequence cs) {
        int strLen;
        if (cs != null && (strLen = cs.length()) != 0) {
            for (int i = 0; i < strLen; ++i) {
                if (!Character.isWhitespace(cs.charAt(i))) {
                    return false;
                }
            }

            return true;
        } else {
            return true;
        }
    }

    /**
     * 集合转字符串,以","分割
     *
     * @param collection
     * @return
     */
    public static String collectionToString(Collection<String> collection) {
        StringBuilder sb = new StringBuilder();
        if (collection==null&&collection.isEmpty()) return null;
        collection.forEach(s -> {
            sb.append(s)
              .append(",");
        });
        sb.deleteCharAt(sb.length() - 1);
        return sb.toString();
    }

    /**
     * 是否是大写
     *
     * @param target
     * @return
     */
    public static boolean isUpperCase(String target) {
        char[] chars = target.toCharArray();
        for (char c : chars) {
            if (Character.isLowerCase(c))
                return false;
        }
        return true;
    }

    /**
     * 是否是手机号
     *
     * @param src
     * @return
     */
    public static boolean isPhone(String src) {
        String regex = "^((13[0-9])|(14[5|7])|(15([0-3]|[5-9]))|(16[0-9])|(17[013678])|(18[0,5-9])|(19[0-9]))\\d{8}$";
        if (src.length() != 11) {
            return false;
        } else {
            Pattern p = Pattern.compile(regex);
            Matcher m = p.matcher(src);
            boolean isMatch = m.matches();
            return isMatch;
        }
    }

    /**
     * 手机验证码
     *
     * @param permit
     * @return
     */
    public static String phoneCode(int permit) {
        return verficatyCode(permit <= 0 ? 6 : permit);
    }

    /**
     * 是否是身份证号
     *
     * @param src
     * @return
     */
    public static boolean isIdCard(String src) {
        String regex = "(^\\d{15}$)|(^\\d{18}$)|(^\\d{17}(\\d|X|x)$)";
        if (isEmpty(src))
            return false;
        if (!(src.length() == 15 || src.length() == 18)) {
            return false;
        }
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(src);
        boolean isMatch = m.matches();
        return isMatch;
    }

    /**
     * 是否是邮箱
     *
     * @param target
     * @return
     */
    public static boolean isEmail(String target) {
        String regex = "^[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+(\\.[a-zA-Z0-9_-]+)+$";
        if (target == null) {
            return false;
        } else {
            return target.matches(regex);
        }
    }

    public static void main(String[] args) {
        System.out.println(salt(32));
    }

    private static void test(List list, Object o) {
        if (list.size() >= 10) {
            list.remove(list.size() - 1);
        }
        list.add(0, o);
    }

    /**
     * 订单编号
     *
     * @return
     */
    public static String orderNo() {
        StringBuffer sb = new StringBuffer();
        sb.append(new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date()));
        for (int i = 0; i < 6; i++) {
            int j = random.nextInt(10);
            sb.append(j);
        }
        return sb.toString();
    }
}
