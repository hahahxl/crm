package com.bytedance.crm.setting.exception;

/**
 * Created by hxl on 2020/8/6.
 */
public class UserLoginException extends RuntimeException {
    public UserLoginException() {
        super();
    }

    public UserLoginException(String message) {
        super(message);
    }
}
