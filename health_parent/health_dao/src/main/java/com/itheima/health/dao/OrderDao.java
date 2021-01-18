package com.itheima.health.dao;

import com.itheima.health.pojo.Member;
import com.itheima.health.pojo.Order;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @author homin
 * 日期2021-01-13 09:43
 */
public interface OrderDao {
    /**
     * 通过会员信息查询预约信息
     * @param memberMap
     * @return
     */
    Order findOrderByMember(Map<String, Object> memberMap);

    /**
     * 添加预约信息
     * @param order
     */
    void addOrder(Order order);

    /**
     * 根据id查询预约信息
     * @param id
     * @return
     */
    Map<String,Object> findById4Detail(int id);

    /**
     * 今天新增预约人数
     * @param reportDate
     * @return
     */
    int findOrderCountByDate(String reportDate);

    /**
     * 今日就诊人数
     * @param reportDate
     * @return
     */
    int findVisitsCountByDate(String reportDate);

    /**
     * 本周预约人数
     * @param monday
     * @param sunday
     * @return
     */
    int findOrderCountBetweenDate(@Param("startDate") String monday, @Param("endDate") String sunday);

    /**
     * 本周到诊数
     * @param monday
     * @return
     */
    int findVisitsCountAfterDate(String monday);

    /**
     * 热门套餐
     * @return
     */
    List<Map<String, Object>> findHotSetmeal();
}
