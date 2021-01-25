package com.itheima.pojo;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author homin
 * 日期2021-01-25 16:17
 */
@ConfigurationProperties(prefix = "myapp.student")
public class SonOfStudent extends Student{
    private String name;
    private Integer age;

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public Integer getAge() {
        return age;
    }

    @Override
    public void setAge(Integer age) {
        this.age = age;
    }
}
