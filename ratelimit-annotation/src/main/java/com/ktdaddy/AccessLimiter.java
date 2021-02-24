package com.ktdaddy;

import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.stereotype.Service;

/**
 * @author kdaddy@163.com
 * @date 2021/2/21 21:26
 */
@Service
@Slf4j
@Deprecated
public class AccessLimiter {
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private RedisScript<Boolean> rateLimitLua;


    public void limitAccess(String key,Integer limit){
        //第一步：调用lua脚本
        boolean acquired = stringRedisTemplate.execute(rateLimitLua,
                Lists.newArrayList(key),
                limit.toString()
        );

        if(!acquired){
            log.error("your access is blocked,key={}",key);
            throw new RuntimeException("your access is blocked");
        }
    }
}
