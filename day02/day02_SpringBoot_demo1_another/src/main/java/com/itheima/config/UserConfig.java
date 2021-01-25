package com.itheima.config;

import com.itheima.pojo.User;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author homin
 * 日期2021-01-24 17:35
 */
@Configuration
public class UserConfig {
    //@Bean
    public User getUser(){
        return new User();
    }
}
