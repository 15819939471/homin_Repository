package com.itheima.health.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author homin
 * 日期2021-01-15 16:36
 */
public interface MemberService {
    /**
     * 根据月份集合查询每月会员数量
     * @param months
     * @return
     */
    List<Integer> getMemberCount(List<String> months);
}
