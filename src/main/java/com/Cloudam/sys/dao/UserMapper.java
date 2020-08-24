package com.Cloudam.sys.dao;

import com.Cloudam.sys.entity.User;
import com.Cloudam.sys.vo.UserVo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author KazuGin
 * @since 2020-08-15
 */
public interface UserMapper extends BaseMapper<User> {

    IPage<User> findUserListByPage(@Param("page") IPage<User> page, @Param("user")  UserVo user) throws Exception;


}
