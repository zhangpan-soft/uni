package com.dv.uni.commons.utils;

import com.dv.uni.commons.enums.Status;
import com.dv.uni.commons.exceptions.BaseException;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

/**
 * @Author 张盼
 * @Email zhangpan_soft@163.com
 * @Blog zhangpan_soft
 * @Date 2020/9/11 0011
 */
public class Assert {
    public static void newException(Status status) {
        throw BaseException.of(status);
    }

    public static void newException(Status status, Throwable e) {
        throw BaseException.of(status, e);
    }

    public static void newException(BaseException e) {
        throw e;
    }

    public static void newException(Status status, String msg) {
        throw BaseException.of(status, msg);
    }

    public static void newException(Status status, String msg, Throwable e) {
        throw BaseException.of(status, e, msg);
    }

    public static void state(boolean expression, Status status) {
        if (!expression) {
            newException(status);
        }
    }

    public static void state(boolean expression, String message) {
        if (!expression) {
            throw new IllegalStateException(message);
        }
    }

    public static void state(boolean expression, Status status, String message) {
        if (!expression) {
            throw BaseException.of(status, message);
        }
    }

    public static void isTrue(boolean expression, Status status) {
        if (!expression) {
            newException(status);
        }
    }

    public static void isTrue(boolean expression, String message) {
        if (!expression) {
            throw new IllegalStateException(message);
        }
    }

    public static void isTrue(boolean expression, Status status, String message) {
        if (!expression) {
            newException(status, message);
        }
    }

    public static void isNull( Object object, Status status) {
        if (object != null) {
            newException(status);
        }
    }

    public static void isNull( Object object, String message) {
        if (object != null) {
            throw new IllegalStateException(message);
        }
    }

    public static void isNull( Object object, Status status, String message) {
        if (object != null) {
            newException(status, message);
        }
    }

    public static void notNull( Object object, Status status) {
        if (object == null) {
            newException(status);
        }
    }

    public static void notNull( Object object, Status status, String message) {
        if (object == null) {
            newException(status, message);
        }
    }

    public static void notNull( Object object, String message) {
        if (object == null) {
            throw new IllegalStateException(message);
        }
    }

    public static void hasLength( String text, Status status) {
        if (!StringUtils.hasLength(text)) {
            newException(status);
        }
    }

    public static void hasLength( String text, Status status, String message) {
        if (!StringUtils.hasLength(text)) {
            newException(status, message);
        }
    }

    public static void hasLength( String text, String message) {
        if (!StringUtils.hasLength(text)) {
            throw new IllegalStateException(message);
        }
    }

    public static void hasText( String text, Status status) {
        if (!StringUtils.hasText(text)) {
            newException(status);
        }
    }

    public static void hasText( String text, Status status, String message) {
        if (!StringUtils.hasText(text)) {
            newException(status, message);
        }
    }

    public static void hasText( String text, String message) {
        if (!StringUtils.hasText(text)) {
            throw new IllegalStateException(message);
        }
    }

    public static void doesNotContain( String textToSearch, String substring, Status status) {
        if (StringUtils.hasLength(textToSearch) && StringUtils.hasLength(substring) && textToSearch.contains(substring)) {
            newException(status);
        }
    }

    public static void doesNotContain( String textToSearch, String substring, Status status, String message) {
        if (StringUtils.hasLength(textToSearch) && StringUtils.hasLength(substring) && textToSearch.contains(substring)) {
            newException(status, message);
        }
    }


    public static void doesNotContain( String textToSearch, String substring, String message) {
        if (StringUtils.hasLength(textToSearch) && StringUtils.hasLength(substring) && textToSearch.contains(substring)) {
            throw new IllegalStateException(message);
        }
    }

    public static void notEmpty( Object[] array, Status status) {
        if (array == null || array.length == 0) {
            newException(status);
        }
    }

    public static void notEmpty( Object[] array, Status status, String message) {
        if (array == null || array.length == 0) {
            newException(status, message);
        }
    }

    public static void notEmpty( Object[] array, String message) {
        if (array == null || array.length == 0) {
            throw new IllegalStateException(message);
        }
    }

    public static void noNullElements( Object[] array, Status status) {
        if (array != null) {
            Object[] var2 = array;
            int var3 = array.length;

            for (int var4 = 0; var4 < var3; ++var4) {
                Object element = var2[var4];
                if (element == null) {
                    newException(status);
                }
            }
        }

    }

    public static void noNullElements( Object[] array, Status status, String message) {
        if (array != null) {
            Object[] var2 = array;
            int var3 = array.length;

            for (int var4 = 0; var4 < var3; ++var4) {
                Object element = var2[var4];
                if (element == null) {
                    newException(status, message);
                }
            }
        }

    }

    public static void noNullElements( Object[] array, String message) {
        if (array != null) {
            Object[] var2 = array;
            int var3 = array.length;

            for (int var4 = 0; var4 < var3; ++var4) {
                Object element = var2[var4];
                if (element == null) {
                    throw new IllegalStateException(message);
                }
            }
        }

    }

    public static void notEmpty( Collection<?> collection, Status status) {
        if (collection == null || collection.isEmpty()) {
            newException(status);
        }
    }

    public static void notEmpty( Collection<?> collection, Status status, String message) {
        if (collection == null || collection.isEmpty()) {
            newException(status, message);
        }
    }

    public static void notEmpty( Collection<?> collection, String message) {
        if (collection == null || collection.isEmpty()) {
            throw new IllegalStateException(message);
        }
    }

    public static void noNullElements( Collection<?> collection, Status status) {
        if (collection != null) {
            Iterator var2 = collection.iterator();

            while (var2.hasNext()) {
                Object element = var2.next();
                if (element == null) {
                    newException(status);
                }
            }
        }

    }

    public static void noNullElements( Collection<?> collection, Status status, String message) {
        if (collection != null) {
            Iterator var2 = collection.iterator();

            while (var2.hasNext()) {
                Object element = var2.next();
                if (element == null) {
                    newException(status, message);
                }
            }
        }

    }

    public static void noNullElements( Collection<?> collection, String message) {
        if (collection != null) {
            Iterator var2 = collection.iterator();

            while (var2.hasNext()) {
                Object element = var2.next();
                if (element == null) {
                    throw new IllegalArgumentException(message);
                }
            }
        }

    }

    public static void notEmpty( Map<?, ?> map, Status status) {
        if (map == null || map.isEmpty()) {
            newException(status);
        }
    }

    public static void notEmpty( Map<?, ?> map, Status status, String message) {
        if (map == null || map.isEmpty()) {
            newException(status, message);
        }
    }

    public static void notEmpty( Map<?, ?> map, String message) {
        if (map == null || map.isEmpty()) {
            throw new IllegalArgumentException(message);
        }
    }

    public static void isInstanceOf( Class<?> type,  Object obj, Status status) {
        if (!type.isInstance(obj)) {
            newException(status);
        }

    }

    public static void isInstanceOf( Class<?> type,  Object obj, Status status, String message) {
        if (!type.isInstance(obj)) {
            newException(status, message);
        }

    }

    public static void isInstanceOf(Class<?> type,  Object obj, String message) {
        if (!type.isInstance(obj)) {
            throw new IllegalStateException(message);
        }

    }

    public static void isAssignable( Class<?> superType,  Class<?> subType, Status status) {
        if (subType == null || !superType.isAssignableFrom(subType)) {
            newException(status);
        }

    }

    public static void isAssignable( Class<?> superType,  Class<?> subType, Status status, String message) {
        if (subType == null || !superType.isAssignableFrom(subType)) {
            newException(status, message);
        }

    }

    public static void isAssignable( Class<?> superType,  Class<?> subType, String message) {
        if (subType == null || !superType.isAssignableFrom(subType)) {
            throw new IllegalStateException();
        }

    }

    /**
     * 比较相等
     *
     * @param s1     不能为空
     * @param s2
     * @param flag   是否区分大小写
     * @param status
     */
    static void equal( String s1, String s2, boolean flag, Status status) {
        if (flag) {
            state(s1.equals(s2), status);
        } else {
            state(s1.equalsIgnoreCase(s2), status);
        }
    }

    static void equal( String s1, String s2, boolean flag, String message) {
        if (flag) {
            state(s1.equals(s2), message);
        } else {
            state(s1.equalsIgnoreCase(s2), message);
        }
    }

    static void equal( String s1, String s2, boolean flag, Status status, String message) {
        if (flag) {
            state(s1.equals(s2), status, message);
        } else {
            state(s1.equalsIgnoreCase(s2), status, message);
        }
    }
}
