package com.ktdaddy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author kdaddy@163.com
 * @date 2021/2/21 22:06
 */
@RestController
public class ControllerTest {

    @Autowired
    private AccessLimiter accessLimiter;

    @GetMapping("/test")
    public String test(){
        accessLimiter.limitAccess("ratelimit-test",1);
        return "success";
    }

    @GetMapping("test-annotation")
    @com.ktdaddy.annotation.AccessLimiter(limit = 1)
    public String testAnnotation(Integer test){
        return "success";
    }
}
