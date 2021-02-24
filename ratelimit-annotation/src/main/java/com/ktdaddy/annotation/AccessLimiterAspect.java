package com.ktdaddy.annotation;

import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * @author kdaddy@163.com
 * @date 2021/2/24 21:34
 */
@Slf4j
@Aspect
@Component
public class AccessLimiterAspect {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private RedisScript<Boolean> rateLimitLua;

    @Pointcut("@annotation(com.ktdaddy.annotation.AccessLimiter)")
    public void cut() {
    }

    @Before("cut()")
    public void before(JoinPoint joinPoint){
        // 1.获取方法签名，作为method Key
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        AccessLimiter annotation = method.getAnnotation(AccessLimiter.class);
        if(annotation == null){
            return;
        }

        String key = annotation.methodKey();
        Integer limit = annotation.limit();

        //如果没有设置key，从调用方法签名自动生成Key
        if(StringUtils.isEmpty(key)){
            Class[] type = method.getParameterTypes();
            key = method.getName();

            if(type != null){
                String paramTypes = Arrays.stream(type).map(Class::getName).collect(Collectors.joining(","));
                log.info("param types:" + paramTypes);
                key +="#" + paramTypes;
            }
        }

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
