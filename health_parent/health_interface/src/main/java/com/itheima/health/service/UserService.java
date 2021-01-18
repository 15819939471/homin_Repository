package com.itheima.health.service;

import com.itheima.health.pojo.User;

public interface UserService {
    /**
     * 根据角色名查询角色信息
     * @param username
     * @return
     */
    User getUserDetails(String username);
}
