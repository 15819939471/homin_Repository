package com.itheima.health.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.health.constant.MessageConstant;
import com.itheima.health.constant.RedisMessageConstant;
import com.itheima.health.entity.Result;
import com.itheima.health.exception.HealthException;
import com.itheima.health.pojo.Order;
import com.itheima.health.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;


import java.sql.ResultSet;
import java.util.Map;

/**
 * @author homin
 * 日期2021-01-12 22:03
 */
@RequestMapping("/order")
@RestController
public class OrderController {

    @Reference
    private OrderService orderService;

    @Autowired
    private JedisPool jedisPool;

    @PostMapping("/submit")
    public Result submit(@RequestBody Map<String,String> dataMap){

        // 获取客户端传过来的手机号、验证码
        String telephone = dataMap.get("telephone");
        String validateCode = dataMap.get("validateCode");
        // 拼接验证码的手机号,从redis数据库进行查询
        Jedis jedis = jedisPool.getResource();
        String keystr = RedisMessageConstant.SENDTYPE_ORDER + "_" + telephone;
        // 从数据库中获取验证码校验该验证码是否有效
        String code = jedis.get(keystr);
        if (!code.equals(validateCode)) {
            throw new HealthException("验证码错误，请重新输入!");
        }

        Order order = orderService.submit(dataMap);
        return new Result(true, MessageConstant.ORDER_SUCCESS,order);
    }

    @PostMapping("/findById")
    public Result findById(int id){
        Map<String, Object> data = orderService.findById(id);
        return new Result(true,MessageConstant.QUERY_ORDER_SUCCESS,data);
    }

}
