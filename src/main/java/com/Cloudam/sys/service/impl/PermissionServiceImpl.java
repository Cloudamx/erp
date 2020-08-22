package com.Cloudam.sys.service.impl;

import com.Cloudam.sys.entity.Permission;
import com.Cloudam.sys.dao.PermissionMapper;
import com.Cloudam.sys.service.PermissionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.Serializable;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author KazuGin
 * @since 2020-08-15
 */
@Service
public class PermissionServiceImpl extends ServiceImpl<PermissionMapper, Permission> implements PermissionService {
    @Resource
    private PermissionMapper permissionMapper;

    @Override
    public boolean removeById(Serializable id) {
        //根据菜单id或权限id删除sys_role_permission权限菜单关系表数据
        permissionMapper.deleteRolePermissionByPid(id);
        return super.removeById(id);
    }
}
