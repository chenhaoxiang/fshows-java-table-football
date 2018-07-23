/**
 * fshows.com
 * Copyright (C) 2013-2018 All Rights Reserved.
 */
package com.fshows.controller;

import com.fshows.DTO.UserDTO;
import com.fshows.comment.RedisKey;
import com.fshows.utils.RedisCommand;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

/**
 *
 * @author chenhx
 * @version IndexController.java, v 0.1 2018-07-23 下午 1:09
 */
@Controller
@Slf4j
public class IndexController {
    @Autowired
    private RedisCommand redisCommand;

    @GetMapping("/group")
    public ModelAndView group(Map<String,Object> map){
        Map<String,UserDTO> userDTOMap = (Map<String, UserDTO>) redisCommand.get(RedisKey.REDIS_USER_MAP_KEY);
        map.put("userDTOMap",userDTOMap);
        return new ModelAndView("group",map);
    }

}