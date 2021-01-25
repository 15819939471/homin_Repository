package com.itheima;

import com.itheima.config.UserConfig;
import com.itheima.pojo.Student;
import com.itheima.pojo.User;
import com.itheima.selector.MyImportSelector;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Import;

/**
 * @author homin
 * 日期2021-01-24 18:22
 */
@SpringBootApplication
@Import({MyImportSelector.class})
public class AutoConfigApplication {
    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(AutoConfigApplication.class, args);

        User user = context.getBean(User.class);
        Student student = context.getBean(Student.class);
        System.out.println(context.getBean("com.itheima.pojo.Student"));
        System.out.println(user);
        System.out.println(student);
    }
}
