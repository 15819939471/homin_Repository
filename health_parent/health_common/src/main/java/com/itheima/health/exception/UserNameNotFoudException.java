package com.itheima.health.exception;

/**
 * @author homin
 * 日期2021-01-14 20:29
 */
public class UserNameNotFoudException extends RuntimeException{
    public UserNameNotFoudException(String message) {
        super(message);
    }
}
