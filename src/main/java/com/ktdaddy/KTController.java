package com.ktdaddy;

import com.google.common.util.concurrent.RateLimiter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

/**
 * @author kdaddy@163.com
 * @date 2021/2/18 22:43
 */
@RestController
@Slf4j
public class KTController {
    // 限流组件每秒允许发放两个令牌
    RateLimiter limiter = RateLimiter.create(2.0);
    //非阻塞限流
    @GetMapping("/tryAcquire")
    public String tryAcquire(Integer count){
        // 每次请求需要获取的令牌数量
        if (limiter.tryAcquire(count)){
            log.info("success, rate is {}",limiter.getRate());
            return "success";
        }else {
            log.info("fail ,rate is {}",limiter.getRate());
            return "fail";
        }
    }
    //限定时间的阻塞限流
    @GetMapping("tryAcquireWithTimeout")
    public String tryAcquireWithTimeout(Integer count, Integer timeout){
        if (limiter.tryAcquire(count,timeout, TimeUnit.SECONDS)){
            log.info("success, rate is {}",limiter.getRate());
            return "success";
        }else {
            log.info("fail ,rate is {}",limiter.getRate());
            return "fail";
        }
    }
    //同步阻塞限流
    @GetMapping("acquire")
    public String acquire(Integer count) {
        limiter.acquire(count);
        log.info("success, rate is {}",limiter.getRate());
        return "success";
    }
}
