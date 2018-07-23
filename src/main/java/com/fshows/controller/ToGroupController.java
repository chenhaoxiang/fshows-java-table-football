/**
 * fshows.com
 * Copyright (C) 2013-2018 All Rights Reserved.
 */
package com.fshows.controller;

import com.fshows.DTO.UserDTO;
import com.fshows.comment.RedisKey;
import com.fshows.config.StatusEnum;
import com.fshows.utils.RedisCommand;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Set;

/**
 *
 * @author chenhx
 * @version ToGroupController.java, v 0.1 2018-07-23 下午 5:02
 */

@RestController
@Slf4j
public class ToGroupController {

    @Autowired
    private RedisCommand redisCommand;
    private Long yearTime = 60*60*60*24*365L;
    @PostMapping("/toGroup")
    public void toGroup(HttpServletResponse response , String name) throws IOException {
        boolean isOk = false;
        //先判断名字是否在可报名人员里面
        for(int i = 0; i<RedisKey.USER_NAMES.length; i++){
            if(RedisKey.USER_NAMES[i].equals(name)){
                isOk=true;
                break;
            }
        }
        //判断种子选手
        boolean isSeed =false;
        for(int i = 0; i<RedisKey.USER_NAMESA.length; i++){
            if(RedisKey.USER_NAMESA[i].equals(name)){
                isSeed=true;
                break;
            }
        }
        if(!isOk && !isSeed){
            log.info("不是内部人员报名：{}",name);
            response.sendRedirect("group");
            return;
        }

        synchronized (new Object()){
            Map<String,UserDTO> userDTOMap = (Map<String, UserDTO>) redisCommand.get(RedisKey.REDIS_USER_MAP_KEY);
            log.debug("userDTOMap:{}",userDTOMap);
            if(userDTOMap!=null){
                Set<String> stringSet = userDTOMap.keySet();
                for(String key:stringSet){
                    UserDTO userDTO = userDTOMap.get(key);
                    if(userDTO.getName().contains(name)){
                        response.sendRedirect("group");
                        log.info("已经报名了：{}",name);
                        return;
                    }
                }
            }
            //先将用户进行分组
            Integer index = null;
            String groups = RedisKey.GROUP_NAME;
            Integer max =groups.length();
            String group="";
            if(isSeed){
                groups = RedisKey.GROUP_NAMEA;
                max =groups.length();
            }

            //进行分组
            while (max>=0){
                if(max==0){
                    max=1;
                }
                index = new Random(System.currentTimeMillis()).nextInt(max);
                group = groups.charAt(index)+"";
                //从redis中获取该组组成员有几个
                Integer num = (Integer) redisCommand.get(RedisKey.REDIS_USER_GROUP_KEY+group);
                //分到该组
                if(num==null || num==0){
                    redisCommand.set(RedisKey.REDIS_USER_GROUP_KEY+group,1,yearTime);
                    index=-1;
                    break;
                }else {
                    if(RedisKey.GROUP_NAMEA.contains(group)){
                        //种子选手的队伍
                        if(isSeed){
                            //已经有一个人了
                            if(num==1){
                                //判断里面的人是不是种子选手
                                boolean isSeed2 =false;
                                for(int i = 0; i<RedisKey.USER_NAMESA.length; i++){
                                    if(RedisKey.USER_NAMESA[i].equals(userDTOMap.get(group).getName())){
                                        isSeed2=true;
                                        break;
                                    }
                                }
                                //里面人不是种子选手
                                if(!isSeed2){
                                    redisCommand.set(RedisKey.REDIS_USER_GROUP_KEY+group,num+1,yearTime);
                                    index=-1;
                                    break;
                                }
                            }

                        }else{
                            //已经有一个人了
                            if(num==1){
                                //判断里面的人是不是种子选手
                                boolean isSeed2 =false;
                                for(int i = 0; i<RedisKey.USER_NAMESA.length; i++){
                                    if(RedisKey.USER_NAMESA[i].equals(userDTOMap.get(group).getName())){
                                        isSeed2=true;
                                        break;
                                    }
                                }
                                //里面人是种子选手
                                if(isSeed2){
                                    redisCommand.set(RedisKey.REDIS_USER_GROUP_KEY+group,num+1,yearTime);
                                    index=-1;
                                    break;
                                }
                            }
                        }
                    }else {
                        //不是种子选手队伍
                        if(num==1){
                            redisCommand.set(RedisKey.REDIS_USER_GROUP_KEY+group,num+1,yearTime);
                            index=-1;
                            break;
                        }
                    }
                }
                max--;
                //删除groups中的该字符
                groups=groups.replaceAll(group,"");
            }

            //将分好的成员加入到map中。
            if(index!=-1){
                //组已经分完了，出bug了，基本不可能
                log.error("分组已经完成。出bug了...{}",name);
            }
            if(userDTOMap==null){
                userDTOMap = new HashMap<>();
            }
            UserDTO userDTO = userDTOMap.get(group);
            if(userDTO==null){
                userDTO = new UserDTO();
                userDTO.setId(group);
                userDTO.setName(name);
                userDTO.setTime(new Date());
                userDTO.setStatusEnum(StatusEnum.OK);
            }else{
                userDTO.setTime(new Date());
                userDTO.setName(userDTO.getName()+","+name);
            }
            userDTOMap.put(group,userDTO);
            redisCommand.set(RedisKey.REDIS_USER_MAP_KEY,userDTOMap,yearTime);
        }
        log.info("报名成功：{}",name);
        response.sendRedirect("group");
        return;
    }
}