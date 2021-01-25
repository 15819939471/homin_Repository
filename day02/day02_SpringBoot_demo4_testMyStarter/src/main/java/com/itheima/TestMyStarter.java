package com.itheima;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import redis.clients.jedis.Jedis;

/**
 * @author homin
 * 日期2021-01-24 20:39
 */
@SpringBootApplication
public class TestMyStarter {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(TestMyStarter.class, args);
        Jedis jedis = (Jedis) context.getBean("jedis");
        jedis.set("name","jay");
        System.out.println(jedis);
    }
}
