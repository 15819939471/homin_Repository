package com.itheima.health.controller;

import com.itheima.health.constant.RedisMessageConstant;
import com.itheima.health.entity.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.Map;


/**
 * @author homin
 * 日期2021-01-14 10:23
 */
@RestController
@RequestMapping("/login")
public class LoginController {

    @Autowired
    private JedisPool jedisPool;

    @RequestMapping("/check")
    public Result check(@RequestBody Map<String, String> dataMap) {
        String validateCode = dataMap.get("validateCode");
        String telephone = dataMap.get("telephone");
        Jedis jedis = jedisPool.getResource();
        String code = jedis.get(RedisMessageConstant.SENDTYPE_LOGIN +"_"+ telephone);
        if (code.equals(validateCode)) {
            // 验证通过
            return new Result(true,"验证通过");
        } else {
            return new Result(false,"验证码有误，请重新输入");
        }
    }
}
