package com.itheima;

import com.itheima.pojo.User;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * @author homin
 * 日期2021-01-25 15:32
 */
@SpringBootApplication
public class ImportSelectorApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(ImportSelectorApplication.class, args);
        Object userConfig = context.getBean("userConfig");
        System.out.println(userConfig);
        User user = (User) context.getBean("user");
        System.out.println(user.getName()+"的年龄是"+user.getAge()+"岁");

    }
}
