package com.itheima.health.jobs;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.health.service.OrderSettingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author homin
 * 日期2021-01-18 19:52
 */
@Component
public class CleanExpireOrderSetting {

    private static final Logger log = LoggerFactory.getLogger(CleanImgJob.class);

    @Reference
    private OrderSettingService orderSettingService;

    /**
     * 清理过期的预约设置
     */
    //@Scheduled(initialDelay = 3000, fixedDelay = 20000)
    @Scheduled(cron = "0 0 3 L 0 ? *")
    public void cleanOrderSetting() {
        // 获取今天的日期
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String date = sdf.format(new Date());
        // 订阅接口提供的cleanExpireOrderSetting方法
        orderSettingService.cleanExpireOrderSetting(date);
        log.info("清理过期预约设定成功");
    }
}
