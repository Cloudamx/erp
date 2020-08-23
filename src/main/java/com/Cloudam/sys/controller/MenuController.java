package com.Cloudam.sys.controller;

import com.Cloudam.sys.entity.Permission;
import com.Cloudam.sys.entity.User;
import com.Cloudam.sys.service.PermissionService;
import com.Cloudam.sys.utils.*;
import com.Cloudam.sys.vo.PermissionVo;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: Cloudam
 * @Description:
 * @Date:16:19星期六
 */
@RestController
@RequestMapping("/sys/menu")
public class MenuController {
    

    
    @Resource
    private PermissionService permissionService;
    /**
     * @Description: 加载首页菜单树
     * @Param:
     * @Return:
     * @Author: Cloudam
     * @Date: 2020/8/16 10:49
     */

    //所有数据都放在TreeNode中
    @RequestMapping("/loadIndexLeftMenuTree")
    public DataGridViewResult loadIndexLeftMenuTree(HttpSession session){
        //创建条件构造器
        QueryWrapper<Permission> queryWrapper = new QueryWrapper<>();
        //只查询列为type   值为menu的数据
        queryWrapper.eq("type",SystemConstant.TYPE_MENU);

        //要先判断用户的权限是什么,从Session中获取用户
        User LoginUser = (User)session.getAttribute(SystemConstant.LOGINUSER);
        //创建集合保存权限菜单
        List<Permission> permissionList = new ArrayList<>();

        //如果用户类型为0表示当前用户为超级管理员
        if(LoginUser.getType() == SystemConstant.SUPERUSER){
            //调用查询语句菜单列表
            permissionList = permissionService.list(queryWrapper);

        }else{
            //普通用户   需要按照角色和权限查询
            //暂时List一下
            permissionList = permissionService.list(queryWrapper);

        }
        //创建集合，保存树节点
        List<TreeNode> treeNodes = new ArrayList<TreeNode>();
        for (Permission permission : permissionList) {
            //判断当前节点是否展开，是则为true，否则为false
            Boolean spread = SystemConstant.OPEN_TRUE == permission.getOpen() ? true : false;
            TreeNode treeNode = new TreeNode();
            treeNode.setId(permission.getId());//菜单节点id
            treeNode.setPid(permission.getPid());//父节点编号
            treeNode.setHref(permission.getHref());//菜单路径
            treeNode.setIcon(permission.getIcon());//菜单图标
            treeNode.setTitle(permission.getTitle());//菜单名称
            treeNode.setSpread(spread);//是否展开
            //将树节点对象添加到树节点集合
            treeNodes.add(treeNode);
        }

        //构建层级关系
        List<TreeNode> treeNodeList = TreeNodeBuilder.build(treeNodes,1);


        return new DataGridViewResult(treeNodeList);
    }

    /**
     * 加载菜单管理页面左侧菜单树
     * @param permissionVo
     * @return
     */
    @RequestMapping("/loadMenuTreeLeft")
    public DataGridViewResult loadMenuTreeLeft(PermissionVo permissionVo){
        QueryWrapper<Permission> queryWrapper = new QueryWrapper<Permission>();
        queryWrapper.eq("type",SystemConstant.TYPE_MENU);//只查询菜单，不查权限
        //查询所有菜单
        List<Permission> permissionList = permissionService.list(queryWrapper);
        //创建节点集合
        List<TreeNode> treeNodes = new ArrayList<TreeNode>();
        //循环遍历菜单集合
        for (Permission permission : permissionList) {
            //是否展开
            Boolean spread = permission.getOpen()==1?true:false;
            treeNodes.add(new TreeNode(permission.getId(),permission.getPid(),permission.getTitle(),spread));
        }
        return new DataGridViewResult(treeNodes);
    }


    /**
     * 菜单列表
     * @param permissionVo
     * @return
     */
    @RequestMapping("/menuList")
    public DataGridViewResult findMenuList(PermissionVo permissionVo){
        //创建分页对象
        IPage<Permission> page = new Page<Permission>(permissionVo.getPage(),permissionVo.getLimit());
        //创建条件构造器
        QueryWrapper<Permission> queryWrapper = new QueryWrapper<Permission>();
        //只查询菜单，不查询权限
        queryWrapper.eq("type",SystemConstant.TYPE_MENU);
        //菜单名称查询
        queryWrapper.like(StringUtils.isNotBlank(permissionVo.getTitle()),"title",permissionVo.getTitle());
        //编号
        queryWrapper.eq(permissionVo.getId()!=null,"id",permissionVo.getId()).or().eq(permissionVo.getId()!=null,"pid",permissionVo.getId());
        //排序
        queryWrapper.orderByAsc("ordernum");
        //分页查询
        permissionService.page(page,queryWrapper);
        //返回数据
        return new DataGridViewResult(page.getTotal(),page.getRecords());
    }


    /**
     * 添加菜单
     * @param permission
     * @return
     */
    @PostMapping("/addMenu")
    public JSONResult addMenu(Permission permission){
        try {
            //设置添加类型
            permission.setType(SystemConstant.TYPE_MENU);
            //调用新增的方法
            if(permissionService.save(permission)){
                //新增成功
                return SystemConstant.ADD_SUCCESS;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return SystemConstant.ADD_ERROR;
    }

    /**
     * 修改菜单
     * @param permission
     * @return
     */
    @PostMapping("/updateMenu")
    public JSONResult updateMenu(Permission permission){
        try {
            //调用修改的方法
            if(permissionService.updateById(permission)){
                //修改成功
                return SystemConstant.UPDATE_SUCCESS;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return SystemConstant.UPDATE_ERROR;
    }

    /**
     * 判断该菜单下是否有子菜单
     * @param id
     * @return
     */
    @RequestMapping("/checkMenuHasChildren")
    public String checkMenuHasChildren(int id){
        Map<String,Object> map = new LinkedHashMap<String,Object>();
        //构建条件对象
        QueryWrapper<Permission> queryWrapper = new QueryWrapper<Permission>();
        queryWrapper.eq("pid",id);//查询父节点下是否有数据
        //查询
        List<Permission> permissionList = permissionService.list(queryWrapper);
        //判断集合是否有数据，有则不能删除
        if(permissionList.size()>0){
            map.put(SystemConstant.EXIST,true);//存在
        }else{
            map.put(SystemConstant.EXIST,false);//不存在
        }
        return JSON.toJSONString(map);
    }

    /**
     * 删除权限
     * @param id
     * @return
     */
    @RequestMapping("/deleteById")
    public JSONResult deleteById(int id){
        //删除成功
        if(permissionService.removeById(id)){
            return SystemConstant.DELETE_SUCCESS;
        }
        //删除失败
        return SystemConstant.DELETE_ERROR;
    }




}
