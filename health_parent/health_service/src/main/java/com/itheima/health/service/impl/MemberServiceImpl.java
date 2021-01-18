package com.itheima.health.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.itheima.health.dao.MemberDao;
import com.itheima.health.service.MemberService;
import com.itheima.health.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author homin
 * 日期2021-01-15 16:48
 */
@Service(interfaceClass = MemberService.class)
public class MemberServiceImpl implements MemberService {

    @Autowired
    private MemberDao memberDao;

    /**
     * 根据月份集合查询每月登记的会员数量
     * @param months
     * @return
     */
    @Override
    public List<Integer> getMemberCount(List<String> months) {
        ArrayList<Integer> list = new ArrayList<>();
        for (String month : months) {
            // eg : month:2020-12
            // 需求2020-12-31
            month += "-31";
            int count = memberDao.getMemberCount(month);
            list.add(count);
        }
        return list;
    }
}
