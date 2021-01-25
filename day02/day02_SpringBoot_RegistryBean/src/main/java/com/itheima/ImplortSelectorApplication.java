package com.itheima;

import com.itheima.config.TeacherConfig;
import com.itheima.pojo.Teacher;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Import;

import java.util.Map;

/**
 * @author homin
 * 日期2021-01-25 16:30
 */
@SpringBootApplication
@Import({TeacherConfig.class})
public class ImplortSelectorApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(ImportApplication.class, args);
        System.out.println(context.getBean("com.itheima.pojo.Teacher"));

    }
}
