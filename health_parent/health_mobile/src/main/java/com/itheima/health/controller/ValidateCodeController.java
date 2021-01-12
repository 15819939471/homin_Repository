package com.itheima.health.controller;

import com.aliyuncs.exceptions.ClientException;
import com.itheima.health.constant.RedisMessageConstant;
import com.itheima.health.entity.Result;
import com.itheima.health.utils.SMSUtils;
import com.itheima.health.utils.ValidateCodeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * @author homin
 * 日期2021-01-12 17:41
 * setex 001_15819939471 600 666666
 */
@RestController
@RequestMapping("/validateCode")
public class ValidateCodeController {

    @Autowired
    private JedisPool jedisPool;

    private static final Logger log = LoggerFactory.getLogger(ValidateCodeController.class);

    /**
     * 发送验证码
     *
     * @return
     */
    @GetMapping("/send4Order")
    public Result sendValidateCode(String telephone) {
        // 获取redis中
        Jedis jedis = jedisPool.getResource();
        String key = jedis.get(RedisMessageConstant.SENDTYPE_ORDER + "_" + telephone);
        // 判断该验证码是否已经发送
        if (key != null) {
            // 不为空则验证码已发送并还在有效期十分钟内
            return new Result(true, "验证码已发送，请注意查收");
        }
        // key为空，验证码未发送
        // 生成验证码
        Integer code = ValidateCodeUtils.generateValidateCode(6);
//        try {
//            // 发送验证码
//            SMSUtils.sendShortMessage(SMSUtils.VALIDATE_CODE, telephone, code.toString());
//            // 并将该手机的验证码状态存储到redis数据库
//            return new Result(true, "发送验证码成功");
//        } catch (ClientException e) {
//            log.error("发送验证码失败", e);
        //return new Result(false, "发送验证码失败");
//        }
        jedis.setex(RedisMessageConstant.SENDTYPE_ORDER + telephone, 10 * 60, code.toString());
        return new Result(true, "验证码已发送，请注意查收");
    }
}
