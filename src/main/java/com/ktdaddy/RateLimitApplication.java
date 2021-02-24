package com.ktdaddy;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

/**
 * @author kdaddy@163.com
 * @date 2021/2/18 22:40
 */
@SpringBootApplication
public class RateLimitApplication {
    public static void main(String[] args) {
        new SpringApplicationBuilder(RateLimitApplication.class).web(WebApplicationType.SERVLET).run(args);
    }
}
