package com.Cloudam.sys.service.impl;

import com.Cloudam.sys.entity.User;
import com.Cloudam.sys.dao.UserMapper;
import com.Cloudam.sys.service.UserService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author KazuGin
 * @since 2020-08-15
 */
@Service
@Transactional
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Resource
    private UserMapper userMapper;

    @Override
    public User findUserByName(String username) throws Exception {
        //创建条件构造器
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        //指定查询条件
        wrapper.eq("loginname",username);

        return userMapper.selectOne(wrapper);

    }
}
