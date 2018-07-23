/**
 * fshows.com
 * Copyright (C) 2013-2018 All Rights Reserved.
 */
package com.fshows.config;

/**
 *
 * @author chenhx
 * @version StatusEnum.java, v 0.1 2018-07-23 下午 2:44
 */
public enum StatusEnum {
    ERROR(1,"禁用"),
    OUT(2,"淘汰"),
    OK(3,"报名成功"),
        ;
    StatusEnum(int code, String message) {
        this.code = code;
        this.message = message;
    }
    private int code;
    private String message;

    /**
     * Getter method for property <tt>code</tt>.
     *
     * @return property value of code
     */
    public int getCode() {
        return code;
    }

    /**
     * Getter method for property <tt>message</tt>.
     *
     * @return property value of message
     */
    public String getMessage() {
        return message;
    }
}