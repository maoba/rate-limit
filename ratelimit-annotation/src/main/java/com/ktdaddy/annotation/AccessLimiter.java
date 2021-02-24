package com.ktdaddy.annotation;

import java.lang.annotation.*;

/**
 * @author kdaddy@163.com
 * @date 2021/2/24 21:31
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface AccessLimiter {
    int limit();

    String methodKey() default "";
}
