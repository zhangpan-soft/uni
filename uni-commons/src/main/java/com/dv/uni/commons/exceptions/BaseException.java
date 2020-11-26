package com.dv.uni.commons.exceptions;

import com.dv.uni.commons.enums.Status;
import lombok.Getter;
import lombok.Setter;

/**
 * @Author 张盼
 * @Email zhangpan_soft@163.com
 * @Blog zhangpan_soft
 * @Date 2020/9/10 0010
 */
@Getter
@Setter
public class BaseException extends RuntimeException {
    private Status status;
    private String message;
    private Throwable e;

    private BaseException(Status status) {
        super(status.getMsg());
        this.status = status;
    }

    private BaseException(Status status, String message) {
        super(message);
        this.status = status;
        this.message = message;
    }

    private BaseException(Status status, Throwable cause) {
        super(status.getMsg(), cause);
        this.status = status;
        this.e = cause;
    }

    private BaseException(Status status, String message, Throwable cause) {
        super(message, cause);
        this.status = status;
        this.message = message;
        this.e = cause;
    }


    public static BaseException of(Status status) {
        return new BaseException(status);
    }

    public static BaseException of(Status status, String message) {
        return new BaseException(status, message);
    }

    public static BaseException of(Status status, Throwable cause) {
        return new BaseException(status, cause);
    }

    public static BaseException of(Status status, String message, Throwable cause) {
        return new BaseException(status, message, cause);
    }

    public static BaseException of(Status status, Throwable cause, String msg) {
        return new BaseException(status, msg, cause);
    }
}
