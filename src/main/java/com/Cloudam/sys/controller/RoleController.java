package com.Cloudam.sys.controller;


import ch.qos.logback.core.net.SyslogConstants;
import com.Cloudam.sys.entity.Permission;
import com.Cloudam.sys.entity.Role;
import com.Cloudam.sys.service.RoleService;
import com.Cloudam.sys.utils.DataGridViewResult;
import com.Cloudam.sys.utils.JSONResult;
import com.Cloudam.sys.utils.SystemConstant;
import com.Cloudam.sys.vo.RoleVo;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Date;

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



}

