package com.itheima;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;

/**
 * @author homin
 * 日期2021-01-24 16:15
 */
@SpringBootApplication
public class AutoConfigApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(AutoConfigApplication.class, args);
        Object stringRedisTemplate = context.getBean("stringRedisTemplate");
        System.out.println(stringRedisTemplate);
        /*Object redisTemplate = context.getBean("redisTemplate");
        System.out.println(redisTemplate);*/
    }
}
