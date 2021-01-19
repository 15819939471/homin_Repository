package com.itheima.health.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.itheima.health.dao.OrderSettingDao;
import com.itheima.health.exception.HealthException;
import com.itheima.health.pojo.OrderSetting;
import com.itheima.health.service.OrderSettingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * @author homin
 * 日期2021-01-09 17:32
 */
@Service(interfaceClass = OrderSettingService.class)
public class OrderSettingServiceImpl implements OrderSettingService {

    @Autowired
    private OrderSettingDao orderSettingDao;


    /**
     * 批量导入预约设置信息
     * @param orderSettingList
     */
    @Override
    @Transactional
    public void addBatch(List<OrderSetting> orderSettingList) throws HealthException{
        // 判断该时间的预约信息是否存在
        for (OrderSetting orderSetting : orderSettingList) {
            OrderSetting resultOrderSetting = orderSettingDao.findByOrderSettingDate(orderSetting.getOrderDate());
            if (resultOrderSetting == null) {
                // 该预约日期信息未被设置,则添加该预约日期信息
                orderSettingDao.add(orderSetting);
            } else {
                // 该预约日期信息已被设置，则判断可预约数量是否小于已预约人数
                if (orderSetting.getNumber() < resultOrderSetting.getNumber()){
                    // 可预约人数小于已被预约人数，则不能修改
                    throw new HealthException(orderSetting.getOrderDate().toString()+"最大预约人数小于已预约人数");
                } else {
                    // 可预约人数大于已预约人数则更新数据
                    orderSettingDao.update(orderSetting);
                }
            }
        }
    }

    /**
     * 根据日期设置可预约人数
     * @param orderSetting
     */
    @Override
    public void update(OrderSetting orderSetting) {
        // 先判断该日期是否可预约
        OrderSetting resultOrderSetting = orderSettingDao.findByOrderSettingDate(orderSetting.getOrderDate());
        if(resultOrderSetting != null){
            // 代表该日期有设置,判断该日期的预约人数是否小于设置的可预约人数
            if (resultOrderSetting.getReservations() < orderSetting.getNumber()) {
                // 更新可预约人数
                orderSettingDao.updateNumber(orderSetting);
            } else {
                throw new HealthException(orderSetting.getOrderDate().toString()+"最大预约人数小于已预约人数");
            }
        } else {
            orderSettingDao.add(orderSetting);
        }
    }


    /**
     * 根据月份获取预约设置信息
     * @param month
     * @return
     */
    @Override
    public List<Map<String, Integer>> getOrderSettingByMonth(String month) {
        month += "%";
        List<Map<String, Integer>> orderSettingList = orderSettingDao.getOrderSettingByMonth(month);
        return orderSettingList;
    }

    /**
     * 清理过期预约设定
     * @param date
     */
    @Override
    public void cleanExpireOrderSetting(String date) {
        // 清理过期预约设定
        orderSettingDao.cleanExpireOrderSetting(date);
    }
}
