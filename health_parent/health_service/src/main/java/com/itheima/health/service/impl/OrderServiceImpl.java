package com.itheima.health.service.impl;

import com.alibaba.dubbo.config.annotation.Service;

import com.itheima.health.dao.MemberDao;
import com.itheima.health.dao.OrderDao;
import com.itheima.health.dao.OrderSettingDao;
import com.itheima.health.dao.SetMealDao;
import com.itheima.health.exception.HealthException;
import com.itheima.health.pojo.Member;
import com.itheima.health.pojo.Order;
import com.itheima.health.pojo.OrderSetting;

import com.itheima.health.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import redis.clients.jedis.JedisPool;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author homin
 * 日期2021-01-13 08:27
 */
@Service(interfaceClass = OrderService.class)
public class OrderServiceImpl implements OrderService {

    @Autowired
    private JedisPool jedisPool;

    @Autowired
    private OrderDao orderDao;

    @Autowired
    private OrderSettingDao orderSettingDao;

    @Autowired
    private MemberDao memberDao;

    @Autowired
    private SetMealDao setMealDao;

    /**
     * 提交预定
     *
     * @param dataMap
     * @throws HealthException
     */
    @Override
    @Transactional
    public Order submit(Map<String, String> dataMap) throws HealthException {
        // 获取客户端请求预约日期
        Date orderDate = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            orderDate = sdf.parse(dataMap.get("orderDate"));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        // 根据日期查询当天是否可以预约
        OrderSetting orderSetting = orderSettingDao.findOrderSettingByOrderDate(orderDate);
        if (orderSetting != null) {
            // 判断当天预约人数是否已满
            int number = orderSetting.getNumber();
            int reservations = orderSetting.getReservations();
            if (reservations < number) {
                // 预约人数未满,校验会员信息是否存在，不存在则添加会员信息
                // 将该会员信息用pojo进行封装
                Member member = new Member();
                member.setName(dataMap.get("name"));
                member.setSex(dataMap.get("sex"));
                member.setPhoneNumber(dataMap.get("telephone"));
                member.setIdCard(dataMap.get("idCard"));
                member.setRegTime(new Date());
                // 根据手机号判断该用户信息是否存在
                Member memberData = memberDao.findMemberByTelephone(member.getPhoneNumber());
                Integer id;
                if (memberData == null) {
                    // 该用户信息不存在，则添加该用户信息
                    memberDao.addMember(member);
                    id = member.getId();
                } else {
                    id = memberData.getId();
                }
                // 校验会员是否重复预约
                Map<String, Object> memberMap = new HashMap<>();
                memberMap.put("member_id", id);
                memberMap.put("orderDate", orderDate);
                memberMap.put("id", dataMap.get("setmealId"));
                Order orderData = orderDao.findOrderByMember(memberMap);

                if (orderData == null) {
                    // 该用户没有预约当天的此套餐，可以进行预约
                    // 登记预约信息
                    Order order = new Order();
                    // 将预约信息封装到pojo类中
                    order.setSetmealId(Integer.valueOf(dataMap.get("setmealId")));
                    order.setMemberId(id);
                    order.setOrderDate(orderDate);
                    order.setOrderType(Order.ORDERTYPE_WEIXIN);
                    order.setOrderStatus(Order.ORDERSTATUS_NO);
                    orderDao.addOrder(order);
                    // 更新预约设置人数
                    orderSettingDao.editReservationsByOrderDate(orderDate);
                    return order;
                } else {
                    // 该用户已预约，不可重复预约
                    throw new HealthException(orderDate + "此套餐已预约，请勿重复申请!");
                }

            } else {
                // 预约人数已满
                throw new HealthException(orderDate + "预约人数已满，请选择其他日期进行预约");
            }
        } else {
            //当天不可预约
            throw new HealthException(orderDate + "不可预约，请重新选择日期");
        }


    }

    /**
     * 根据id查询预约信息
     *
     * @param id
     * @return
     */
    @Override
    public Map<String, Object> findById(int id) {
        Map<String, Object> data = orderDao.findById4Detail(id);
        return data;
    }


}
