package com.itheima;

import com.itheima.pojo.User;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Import;

import java.util.Map;

/**
 * @author homin
 * 日期2021-01-24 18:13
 */
@SpringBootApplication
@Import(User.class)
public class AutoConfigApplication {
    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(AutoConfigApplication.class, args);
        Object user = context.getBean("com.itheima.pojo.User");
        System.out.println(user);
        Map<String, User> map = context.getBeansOfType(User.class);
        User user1 = map.get("com.itheima.pojo.User");
        System.out.println(user1);
    }
}

