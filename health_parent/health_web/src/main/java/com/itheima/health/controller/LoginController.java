package com.itheima.health.controller;

import com.itheima.health.entity.Result;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *
 * </p>
 *
 * @author: Eric
 * @since: 2021/1/15
 */
@RestController
@RequestMapping("/login")
public class LoginController {

    /**
     * 获取登陆用户名
     * @return
     */
    @GetMapping("/getLoginUsername")
    public Result getLoginUsername(){
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return new Result(true, "获取登陆用户名成功",user.getUsername());
    }

    /**
     * 登录成功
     * @return
     */
    @RequestMapping("/loginSuccess")
    public Result loginSuccess(){
        return new Result(true,"登录成功");
    }

    /**
     * 登录失败
     * @return
     */
    @RequestMapping("/loginFail")
    public Result loginFail(){
        return new Result(false,"用户名或者密码错误，请重新输入");
    }
}
