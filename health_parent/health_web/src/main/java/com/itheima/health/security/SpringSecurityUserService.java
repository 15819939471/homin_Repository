package com.itheima.health.security;

import com.alibaba.dubbo.config.annotation.Reference;

import com.itheima.health.pojo.Permission;
import com.itheima.health.pojo.Role;

import com.itheima.health.pojo.User;
import com.itheima.health.service.UserService;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;


import java.util.ArrayList;
import java.util.List;
import java.util.Set;


/**
 * @author homin
 * 日期2021-01-14 17:31
 */
@Component
public class SpringSecurityUserService implements UserDetailsService {

    @Reference
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException{
        // 查询用户信息
        User dbuser = userService.getUserDetails(username);
        if(dbuser != null){
            // 获取角色权限的授权集合
            List<GrantedAuthority> grantedAuthorityList = new ArrayList<>();
            Set<Role> roles = dbuser.getRoles();
            roles.forEach(role -> {
                // 获取角色的授权添加到权限集合
                SimpleGrantedAuthority roleAuthority = new SimpleGrantedAuthority(role.getKeyword());
                grantedAuthorityList.add(roleAuthority);
                // 遍历每个角色获取对应的权限
                Set<Permission> permissions = role.getPermissions();
                permissions.forEach(permission -> {
                    // 遍历权限，添加到权限集合中
                    SimpleGrantedAuthority permissionAuthority = new SimpleGrantedAuthority(permission.getKeyword());
                    grantedAuthorityList.add(permissionAuthority);
                });
            });

            // 封装到sercurity框架的User对象，进行校验登录
            org.springframework.security.core.userdetails.User securityUser = new org.springframework.security.core.userdetails.User(username, dbuser.getPassword(), grantedAuthorityList);
            return securityUser;
        }
        return null;
    }
}
