package com.ktdaddy;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;

/**
 * @author kdaddy@163.com
 * @date 2021/2/21 21:36
 */
@Configuration
public class RedisConfiguration {


  @Bean
  public RedisTemplate<String,String> redisTemplate(RedisConnectionFactory redisConnectionFactory){
      return new StringRedisTemplate(redisConnectionFactory);
  }

  @Bean
  public DefaultRedisScript loadRedisScript(){
      DefaultRedisScript redisScript = new DefaultRedisScript();
      redisScript.setLocation(new ClassPathResource("ratelimiter.lua"));
      redisScript.setResultType(Boolean.class);
      return redisScript;
  }

}
