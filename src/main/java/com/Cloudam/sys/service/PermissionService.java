package com.Cloudam.sys.service;

import com.Cloudam.sys.entity.Permission;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author KazuGin
 * @since 2020-08-15
 */
public interface PermissionService extends IService<Permission> {
    /**
     * 这种看方法名就知道方法作用的就不要注释了吧，罗里吧嗦
     * @param roleId
     * @return
     */
    List<Integer> findRolePermissionByRoleId(int roleId);
}
