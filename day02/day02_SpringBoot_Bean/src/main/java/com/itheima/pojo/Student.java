package com.itheima.pojo;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author homin
 * 日期2021-01-25 15:49
 */
@ConfigurationProperties("myapp.student")
public class Student {
    private String name;
    private Integer age;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }
}
