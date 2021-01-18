package com.itheima.health.controller;

import com.alibaba.dubbo.common.logger.Logger;
import com.alibaba.dubbo.common.logger.LoggerFactory;
import com.itheima.health.entity.Result;
import com.itheima.health.exception.HealthException;
import com.itheima.health.exception.UserNameNotFoudException;
import org.springframework.expression.spel.SpelEvaluationException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author homin
 * 日期2021-01-06 19:25
 */
@RestControllerAdvice

public class MyExceptionAdvice {

    private static final Logger log = LoggerFactory.getLogger(MyExceptionAdvice.class);

    //拦截到HealthException
    @ExceptionHandler(value = HealthException.class)
    public Result businessException(HealthException healthException){
        String message = healthException.getMessage();
        Result result = new Result(false, message);
        return result;
    }

    @ExceptionHandler(value = UserNameNotFoudException.class)
    public Result UserNameNotFoundException(UserNameNotFoudException userNameNotFoundException){
        String message = userNameNotFoundException.getMessage();
        return new Result(false,message);
    }

    @ExceptionHandler(value = Exception.class)
    public Result businessException(Exception exception){
        log.error("发生异常",exception);
        return new Result(false, "发生未知错误，操作失败，请联系管理员");
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public Result spelEvaluationException(MethodArgumentNotValidException exception){
        log.error("发生异常",exception);
        return new Result(false, "价格不能过低！");
    }

}
