package com.itheima.health.service;

import java.util.Map;

public interface ReportService {
    /**
     * reportDate，日期
     * todayNewMember，本日新增会员数
     * totalMember，总会员数
     * thisWeekNewMember，本周新增会员数
     * thisMonthNewMember，本月新增会员数
     * todayOrderNumber，今日预约数
     * todayVisitsNumber，今日到诊数
     * thisWeekOrderNumber，本周预约数
     * thisWeekVisitsNumber，本周到诊数
     * thisMonthOrderNumber，本月预约数
     * thisMonthVisitsNumber，本月到诊数
     * hotSetmeal，热门套餐  name: "",setmeal_count: "",proportion: ""
     * 查询商业报告
     *
     * @return
     */
    Map<String, Object> getBuinessReport();
}
