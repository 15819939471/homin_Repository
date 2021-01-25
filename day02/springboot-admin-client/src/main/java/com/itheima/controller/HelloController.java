package com.itheima.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author homin
 * 日期2021-01-25 12:03
 */
@RestController
@RequestMapping("/hello")
public class HelloController {
    @RequestMapping("/show")
    public String show() {
        return "success";
    }
}
