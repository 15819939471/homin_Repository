package com.itheima.health.service;

import com.itheima.health.exception.HealthException;
import com.itheima.health.pojo.OrderSetting;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author homin
 * 日期2021-01-09 17:26
 */
public interface OrderSettingService {
    /**
     * 根据月份获取预约设置信息
     * @param month
     * @return
     */
    List<Map<String, Integer>> getOrderSettingByMonth(String month);

    /**
     * 批量导入设置预约信息
     * @param orderSettingList
     */
    void addBatch(List<OrderSetting> orderSettingList) throws HealthException;

    /**
     * 根据日期设置可预约人数
     * @param orderSetting
     */
    void update(OrderSetting orderSetting);

    /**
     * 清理过期预约设定
     * @param date
     */
    void cleanExpireOrderSetting(String date);
}
