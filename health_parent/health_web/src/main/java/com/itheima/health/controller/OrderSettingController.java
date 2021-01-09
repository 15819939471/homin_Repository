package com.itheima.health.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.health.constant.MessageConstant;
import com.itheima.health.entity.Result;
import com.itheima.health.pojo.OrderSetting;
import com.itheima.health.service.OrderSettingService;
import com.itheima.health.utils.POIUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author homin
 * 日期2021-01-09 17:02
 */
@RestController
@RequestMapping("/ordersetting")
public class OrderSettingController {

    @Reference
    private OrderSettingService orderSettingService;

    private static final Logger log = LoggerFactory.getLogger(OrderSettingController.class);

    /**
     * 批量导入设置预约信息
     *
     * @return
     */
    @PostMapping("/upload")
    public Result upload(MultipartFile excelFile) {
        try {
            // 将excell文件对象解析为String集合
            List<String[]> orderList = POIUtils.readExcel(excelFile);
            // 解析为OrderSetting类型
            // 1、先创建一个OrderSetting类型的集合
            List<OrderSetting> orderSettingList = new ArrayList<>();
            // 2、规定日期格式
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(POIUtils.DATE_FORMAT);
            // 3、给OrderSetting类型的集合赋值
            for (String[] strings : orderList) {
                // 索引为0的是预约日期，索引为1的是可预约数量
                try {
                    OrderSetting orderSetting = new OrderSetting(simpleDateFormat.parse(strings[0]), new Integer(strings[1]));
                    orderSettingList.add(orderSetting);
                } catch (ParseException e) {
                }
            }
            // 调用订阅接口提供的add方法添加预约信息
            orderSettingService.addBatch(orderSettingList);
            return new Result(true, "设置预约信息成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, "设置预约信息失败");
        }
    }

    /**
     * 根据月份获取预约设置
     * @return
     * eg:month= 2021-01
     */
    @GetMapping("/getOrderSettingByMonth")
    public Result findAll(String month){
        // 根据月份获取预约设置信息
        List<Map<String, Integer>> orderSettingList = orderSettingService.getOrderSettingByMonth(month);
        return new Result(true, MessageConstant.GET_ORDERSETTING_SUCCESS,orderSettingList);
    }

    /**
     * 根据日期修改预约数
     * eg：  number: "500"
     *      orderDate: "2021-01-13"
     * @return
     */
    @PostMapping("/editNumberByDate")
    public Result editNumberByDate (@RequestBody OrderSetting orderSetting ) throws ParseException {
        orderSettingService.update(orderSetting);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return new Result(true,"设置"+simpleDateFormat.format(orderSetting.getOrderDate())+"可预约人数成功");
    }
}
