package com.itheima.health.dao;

import com.itheima.health.pojo.User;

public interface UserDao {
    /**
     * 根据用户名查询用户详情
     * @param username
     * @return
     */
    User getUserDetails(String username);
}
