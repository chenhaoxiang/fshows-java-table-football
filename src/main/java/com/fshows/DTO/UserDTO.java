/**
 * fshows.com
 * Copyright (C) 2013-2018 All Rights Reserved.
 */
package com.fshows.DTO;

import com.fshows.config.StatusEnum;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author chenhx
 * @version UserDTO.java, v 0.1 2018-07-23 下午 2:42
 */
@Data
public class UserDTO implements Serializable {
    private static final long serialVersionUID = 7676040650153663537L;
    private String name;
    private String id;
    private Date time;
    private StatusEnum statusEnum;
}