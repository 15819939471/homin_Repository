package com.itheima;

import com.itheima.config.StudentConfig;
import com.itheima.pojo.Student;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Import;
import org.springframework.core.env.ConfigurableEnvironment;

import java.util.Map;

/**
 * @author homin
 * 日期2021-01-25 15:57
 */
@SpringBootApplication
@Import(StudentConfig.class)
public class ImportApplication {
    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(ImportApplication.class, args);
        System.out.println(context.getBean("com.itheima.config.StudentConfig"));
        Student student = (Student) context.getBean("student");
        System.out.println(student);
        Student sonOfStudent = (Student) context.getBean("sonOfStudent");
        System.out.println(sonOfStudent);
        System.out.println(context.getBeansOfType(Student.class));

    }
}
