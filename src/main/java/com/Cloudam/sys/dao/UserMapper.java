package com.Cloudam.sys.dao;

import com.Cloudam.sys.entity.User;
import com.Cloudam.sys.vo.UserVo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.Set;

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

    @Select("select rid FROM sys_role_user WHERE uid = #{uid}")
    Set<Integer> findUserRoleByUserId(Integer id) throws Exception;

    @Delete("delete from sys_role_user where uid = #{userid}")
    void deleteUserRoleByUserId(int userId) throws Exception;

    @Insert("INSERT into sys_role_user (rid,uid) VALUES(#{rid},#{uid})")
    void insertUserRole(@Param("uid") int userId, @Param("rid")String rid) throws Exception;
}
