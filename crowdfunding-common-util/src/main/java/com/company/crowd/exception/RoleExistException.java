package com.company.crowd.exception;

/**
 * @author: yaochunguang
 * @date: 2020-11-27 17:21
 * @description: 角色已经存在异常
 **/
public class RoleExistException extends RuntimeException {
    public RoleExistException() {
        super();
    }

    public RoleExistException(String message) {
        super(message);
    }

    public RoleExistException(String message, Throwable cause) {
        super(message, cause);
    }

    public RoleExistException(Throwable cause) {
        super(cause);
    }

    protected RoleExistException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
