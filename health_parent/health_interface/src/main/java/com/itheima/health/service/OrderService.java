package com.itheima.health.service;

import com.itheima.health.exception.HealthException;
import com.itheima.health.pojo.Order;


import java.util.Map;

/**
 * @author homin
 * 日期2021-01-12 22:16
 */
public interface OrderService {
    /**
     * 提交预约信息
     * @param dataMap
     * @return
     * @throws HealthException
     */
    public Order submit(Map<String, String> dataMap) throws HealthException;

    /**
     * 根据id查询预约信息
     * @param id
     * @return
     */
    Map<String,Object> findById(int id);
}
