package com.Cloudam.sys.service.impl;

import com.Cloudam.sys.entity.User;
import com.Cloudam.sys.dao.UserMapper;
import com.Cloudam.sys.service.UserService;
import com.Cloudam.sys.vo.UserVo;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Set;

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

    @Override
    public IPage<User> findUserListByPage(IPage<User> page, UserVo user) throws Exception {
        return userMapper.findUserListByPage(page,user);
    }

    @Override
    public Set<Integer> findUserRoleByUserId(Integer id) throws Exception {
        return userMapper.findUserRoleByUserId(id);
    }

    @Override
    public boolean saveUserRole(int userId, String roleIds) throws Exception{
        try {
            //先删除sys_role_user表的数据
            userMapper.deleteUserRoleByUserId(userId);
            //再添加sys_role_user表的数据
            String [] rids = roleIds.split(",");
            //循环添加
            for (int i = 0; i < rids.length; i++) {
                userMapper.insertUserRole(userId,rids[i]);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }
}
