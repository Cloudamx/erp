package com.Cloudam.sys.service.impl;

import com.Cloudam.sys.entity.Role;
import com.Cloudam.sys.dao.RoleMapper;
import com.Cloudam.sys.service.RoleService;
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
 * @since 2020-08-23
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {
    @Resource
    private RoleMapper roleMapper;

    @Override
    public boolean removeById(Serializable id) {
            //删除两张关系表的数据
            //根据角色id删除
            roleMapper.deleteRoleUserByRoleId(id);
            roleMapper.deleteRolePermissionByRoleId(id);
            return super.removeById(id);
    }

    @Override
    public boolean saveRolePermission(int rid, String ids) throws Exception {
        try {
            //先删除原有的权限
            roleMapper.deleteRolePermissionByRoleId(rid);
            //再添加新的权限
            String []pids = ids.split(",");
            //循环遍历有什么权限，存之
            for(int i = 0; i<pids.length;i++){
                //调用保存角色权限的方法
                roleMapper.insertRolePermission(rid,pids[i]);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }
}
