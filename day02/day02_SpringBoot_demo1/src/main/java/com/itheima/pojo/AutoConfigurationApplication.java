package com.itheima.pojo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author homin
 * 日期2021-01-24 17:38
 */
@ComponentScan(value = "com.itheima")
@SpringBootApplication
public class AutoConfigurationApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(AutoConfigurationApplication.class,args);
        Object user = context.getBean("userConfig");
        System.out.println("user实例 ： "+user);
    }
}
