package com.Cloudam.sys.service;

import com.Cloudam.sys.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;

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
}
