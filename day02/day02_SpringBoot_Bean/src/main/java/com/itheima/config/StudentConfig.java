package com.itheima.config;

import com.itheima.pojo.SonOfStudent;
import com.itheima.pojo.Student;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author homin
 * 日期2021-01-25 15:54
 */

public class StudentConfig {
    @Bean
    public Student student() {
        return new Student();
    }
    @Bean
    public SonOfStudent sonOfStudent(){
        return new SonOfStudent();
    }
}
