package com.Cloudam.sys.controller;

import com.Cloudam.sys.entity.Permission;
import com.Cloudam.sys.entity.User;
import com.Cloudam.sys.service.PermissionService;
import com.Cloudam.sys.utils.DataGridViewResult;
import com.Cloudam.sys.utils.SystemConstant;
import com.Cloudam.sys.utils.TreeNode;
import com.Cloudam.sys.utils.TreeNodeBuilder;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

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
        List<Permission> permissionList= new ArrayList<>();

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
            treeNodes.add(new TreeNode(permission.getId(), permission.getPid(),
                    permission.getTitle(), permission.getIcon(),
                    permission.getHref(), spread));
        }

        //构建层级关系
        List<TreeNode> treeNodeList = TreeNodeBuilder.build(treeNodes,1);


        return new DataGridViewResult(treeNodeList);
    }
}
