package com.itheima.health.controller;

import com.itheima.health.entity.Result;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.ResultSet;

/**
 * @author homin
 * 日期2021-01-14 22:14
 */
@RestController
@RequestMapping("/test")
public class TestAnnotationSecurity {

    /**
     * 使用hasAuthority，里面的权限必须准确
     * @return
     */
    @RequestMapping("/a")
    @PreAuthorize("hasAuthority('CHECKITEM_QUERY')")
    public Result test1(){
        System.out.println("a");
        return new Result(true,"可以访问","hasAuthority访问成功");
    }

    /**
     * 如果使用hasRole，框架底层会自动帮你补充前缀ROLE_
     * eg：ROLE_ADMIN
     * @return
     */
    @RequestMapping("/b")
    @PreAuthorize("hasRole('ADMIN')")
    public Result test2(){
        System.out.println("b");
        return new Result(true,"可以访问","hasRole访问成功");
    }
}
