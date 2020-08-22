package com.Cloudam.sys.vo;

import com.Cloudam.sys.entity.User;

import java.util.Set;

/**
 * 登录用户类
 */
public class LoginUserVo {
    private User user;//用户信息
    private Set<String> roles;//角色列表
    private Set<String> permissions;//权限列表

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Set<String> getRoles() {
        return roles;
    }

    public void setRoles(Set<String> roles) {
        this.roles = roles;
    }

    public Set<String> getPermissions() {
        return permissions;
    }

    public void setPermissions(Set<String> permissions) {
        this.permissions = permissions;
    }

    public LoginUserVo() {
    }

    /**
     * 登录用户
     * @param user          当前登录用户信息
     * @param roles         当前用户拥有的角色列表
     * @param permissions   当前用户拥有的角色列表
     */
    public LoginUserVo(User user, Set<String> roles, Set<String> permissions) {
        this.user = user;
        this.roles = roles;
        this.permissions = permissions;
    }
}