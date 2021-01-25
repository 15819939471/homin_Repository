package com.itheima.testotherpackge;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author homin
 * 日期2021-01-25 15:47
 */
@SpringBootApplication
@ComponentScan(basePackages = {"com.itheima"})// userConfig不在启动类的包目录下，需要spring进行扫包路径的指定
public class OtherPackgeApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(OtherPackgeApplication.class, args);
        System.out.println(context.getBean("userConfig"));
    }
}
