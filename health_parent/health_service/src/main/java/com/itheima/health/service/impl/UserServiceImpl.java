package com.itheima.health.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.itheima.health.dao.UserDao;
import com.itheima.health.pojo.User;
import com.itheima.health.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author homin
 * 日期2021-01-14 18:07
 */
@Service(interfaceClass = UserService.class)
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    /**
     * 根据用户名查询用户详情
     * @param username
     * @return
     */
    @Override
    public User getUserDetails(String username) {
        User user = userDao.getUserDetails(username);
        return user;
    }
}
