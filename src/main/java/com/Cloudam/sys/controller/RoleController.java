package com.Cloudam.sys.controller;


import ch.qos.logback.core.net.SyslogConstants;
import com.Cloudam.sys.entity.Permission;
import com.Cloudam.sys.entity.Role;
import com.Cloudam.sys.service.PermissionService;
import com.Cloudam.sys.service.RoleService;
import com.Cloudam.sys.utils.DataGridViewResult;
import com.Cloudam.sys.utils.JSONResult;
import com.Cloudam.sys.utils.SystemConstant;
import com.Cloudam.sys.utils.TreeNode;
import com.Cloudam.sys.vo.RoleVo;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author KazuGin
 * @since 2020-08-23
 */
@RestController
@RequestMapping("/sys/role")
public class RoleController {

    @Resource
    private RoleService roleService;

    @Resource
    private PermissionService permissionService;

    //显示数据
    @RequestMapping("/rolelist")
    public DataGridViewResult roleList(RoleVo rolevo){
        //创建分页对象,参数当前页码，每一页需要几个
        IPage<Role> page = new Page<>(rolevo.getPage(),rolevo.getLimit());

        //创建条件构造器
        QueryWrapper<Role> queryWrapper = new QueryWrapper<>();
        //角色编码
        queryWrapper.like(StringUtils.isNotBlank(rolevo.getRolecode()),"rolecode",rolevo.getRolecode());
        //角色名称
        queryWrapper.like(StringUtils.isNotBlank(rolevo.getRolename()),"rolename",rolevo.getRolename());
        //排序
        queryWrapper.orderByAsc("id");

        //调用分页查询的方法
        roleService.page(page,queryWrapper);

        //数据返回
        return new DataGridViewResult(page.getTotal(),page.getRecords());
    }

    @RequestMapping("/addRole")
    public JSONResult addRole(Role role){
        role.setCreatetime(new Date());
        if(roleService.save(role)){
            return SystemConstant.ADD_SUCCESS;
        }
        return SystemConstant.ADD_ERROR;
    }

    @RequestMapping("/updateRole")
    public JSONResult updateRole(Role role){
        if(roleService.updateById(role)){
            return SystemConstant.ADD_SUCCESS;
        }
        return SystemConstant.ADD_ERROR;
    }



    @RequestMapping("/deleteById")
    public JSONResult deleteById(int id){
        //删除成功
        if(roleService.removeById(id)){
            return SystemConstant.DELETE_SUCCESS;
        }
        //删除失败
        return SystemConstant.DELETE_ERROR;
    }

    /**
     * @Description: 初始化权限菜单树
     * @Param: id
     * @Return: DataGridViewResult
     * @Author: Cloudam
     * @Date: 2020/8/23 20:41
     */
    @RequestMapping("/initPermissionByRoleId")
    public DataGridViewResult initPermissionByRoleId(int roleId){
        //创建条件构造器
        QueryWrapper<Permission> queryWrapper = new QueryWrapper<>();
        //用一个集合先查询出所有的权限
        List<Permission> permissions = permissionService.list(queryWrapper);   //所有传不传都无所谓
        //用第二个集合保存已有的权限
        List<Permission> currentpermissions = new ArrayList<>();

        //查询当前角色已经有的权限
        //是根据roleId在关系表中进行查询
        List<Integer> currentPermissionIds = permissionService.findRolePermissionByRoleId(roleId);
        //判断当前角色是否有权限id
        if(currentPermissionIds!=null && currentPermissionIds.size()>0){
            //进行条件查询
            queryWrapper.in("id",currentPermissionIds);

            currentpermissions = permissionService.list(queryWrapper);
        }
        //构建树节点集合
        List<TreeNode> treeNodes = new ArrayList<>();
        //外层循环所有的权限数据
        for (Permission p1 : permissions) {
            //定义变量
            String checkArr = "0" ;  //不选中
            //遍历已有的权限，是否匹配
            for (Permission p2 : currentpermissions) {
                if(p1.getId() == p2.getId()){
                    checkArr = "1";
                    break;
                }

            }
            //组装树节点对象
            Boolean spread = (p1.getOpen() == null || p1.getOpen() == 1) ? true:false;
            //将节点对象添加到节点集合
            treeNodes.add(new TreeNode(p1.getId(),p1.getPid(),p1.getTitle(),spread,checkArr));
        }



        return new DataGridViewResult(treeNodes);
    }





}

