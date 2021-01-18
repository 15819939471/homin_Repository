package com.itheima.health.dao;

import com.itheima.health.pojo.Member;
import com.itheima.health.pojo.Order;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * @author homin
 * 日期2021-01-13 15:53
 */
public interface MemberDao {
    /**
     * 根据电话号码查询会员信息
     * @param phoneNumber
     * @return
     */
    Member findMemberByTelephone(String phoneNumber);

    /**
     * 添加会员信息
     * @param member
     */
    void addMember(Member member);

    /**
     * 查询会员信息
     * @param memberId
     * @return
     */
    Member findById(Integer memberId);

    /**
     * 根据月份查询每月会员人数
     * @return
     */
    Integer getMemberCount(String date);

    /**
     * 今日新增会员
     * @param reportDate
     * @return
     */
    int findMemberCountByDate(String reportDate);

    /**
     * 会员总数
     * @return
     */
    int findMemberTotalCount();

    /**
     * 本周新增会员数
     * @param monday
     * @return
     */
    int findMemberCountAfterDate(String monday);
}
