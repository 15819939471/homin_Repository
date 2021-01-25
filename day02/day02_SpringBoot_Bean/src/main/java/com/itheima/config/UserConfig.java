package com.itheima.config;

import com.itheima.pojo.User;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author homin
 * 日期2021-01-25 15:19
 */
@Configuration
public class UserConfig {

    @Bean
    public User user(){
        User user = new User();
        return user;
    }
}
