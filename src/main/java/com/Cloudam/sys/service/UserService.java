package com.Cloudam.sys.service;

import com.Cloudam.sys.entity.User;
import com.Cloudam.sys.vo.UserVo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.ibatis.annotations.Param;

import java.util.Set;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author KazuGin
 * @since 2020-08-15
 */
public interface UserService extends IService<User> {
    User findUserByName(String username) throws Exception;

    IPage<User> findUserListByPage(IPage<User> page,UserVo user) throws Exception;

    Set<Integer> findUserRoleByUserId(Integer id) throws Exception;

    boolean saveUserRole(int userId, String roleIds) throws Exception;
}
