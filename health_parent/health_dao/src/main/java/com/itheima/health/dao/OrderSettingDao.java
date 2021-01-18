package com.itheima.health.dao;

import com.itheima.health.pojo.OrderSetting;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface OrderSettingDao {
    /**
     * 查询预约设置
     * @param orderDate
     * @return
     */
    OrderSetting findByOrderSettingDate(Date orderDate);

    /**
     * 添加预约设置
     * @param orderSetting
     */
    void add(OrderSetting orderSetting);

    /**
     * 更新预约设置
     * @param orderSetting
     */
    void update(OrderSetting orderSetting);

    /**
     * 根据月份查询预约设置信息
     * @param month
     * @return
     */
    List<Map<String, Integer>> getOrderSettingByMonth(String month);

    /**
     * 更新可预约人数
     * @param orderSetting
     */
    void updateNumber(OrderSetting orderSetting);

    /**
     * 根据日期查询当天是否可预约
     * @param orderDate
     * @return
     */
    OrderSetting findOrderSettingByOrderDate(Date orderDate);

    /**
     * 更新预约人数
     * @param orderDate
     */
    void editReservationsByOrderDate(Date orderDate);
}
