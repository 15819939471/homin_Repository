package com.itheima;

import com.itheima.poperties.MyRedisProperties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import redis.clients.jedis.Jedis;

/**
 * @author homin
 * 日期2021-01-24 20:15
 */
//@EnableConfigurationProperties(MyRedisProperties.class)
public class MyRedisAutoConfiguration {
    @Bean
    @ConditionalOnMissingBean(name = "jedis")
    public Jedis jedis(MyRedisProperties myRedisProperties){
        System.out.println("MyRedis-host:"+myRedisProperties.getHost()+"<---->MyRedis-port:"+myRedisProperties.getPort());
        Jedis jedis = new Jedis(myRedisProperties.getHost(), myRedisProperties.getPort());
        return jedis;
    }
}
