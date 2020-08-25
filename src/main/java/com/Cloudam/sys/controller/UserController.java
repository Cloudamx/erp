package com.Cloudam.sys.controller;


import com.Cloudam.sys.entity.Log;
import com.Cloudam.sys.entity.User;
import com.Cloudam.sys.service.LogService;
import com.Cloudam.sys.service.RoleService;
import com.Cloudam.sys.service.UserService;
import com.Cloudam.sys.utils.*;
import com.Cloudam.sys.vo.LoginUserVo;
import com.Cloudam.sys.vo.UserVo;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.commons.lang3.SerializationUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.print.DocFlavor;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author KazuGin
 * @since 2020-08-15
 */
@RestController
@RequestMapping("/sys/user")
public class UserController {

    @Resource
    private LogService logService;

    @Resource
    private UserService userService;

    @Resource
    private RoleService roleService;

    @PostMapping("/login")
    public JSONResult login(String loginname, String pwd, HttpServletRequest request){

        //获取当前对象
        Subject subject = SecurityUtils.getSubject();
        //创建令牌对象
        UsernamePasswordToken token = new UsernamePasswordToken(loginname, pwd);

        try {
            //执行登录
            subject.login(token);
            //获取当前登录对象
            LoginUserVo vo = (LoginUserVo) subject.getPrincipal();
            //保存登录对象
            request.getSession().setAttribute(SystemConstant.LOGINUSER,vo.getUser());
            //记录操作日志

            Log log = new Log("用户登录",SystemConstant.LOGIN_ACTION,
                    loginname+"-"+vo.getUser().getName(),
                    vo.getUser().getId(),request.getRemoteAddr(),
                    new Date());
            logService.save(log);

            //登陆成功
            return SystemConstant.LOGIN_SUCCESS;

        } catch (AuthenticationException e) {
            e.printStackTrace();
        }


        return SystemConstant.LOGIN_ERROR_PASS;
    }

    @RequestMapping("/userlist")
    public DataGridViewResult userlist(UserVo userVo){
        try {
            IPage<User> page = new Page<>(userVo.getPage(),userVo.getLimit());
            IPage<User> userIPage = userService.findUserListByPage(page,userVo);
            return new DataGridViewResult(userIPage.getTotal(),userIPage.getRecords());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @RequestMapping("/loadUserByDeptId")
    public DataGridViewResult loadUserByDeptId(Integer deptId){
        QueryWrapper<User> queryWrapper = new QueryWrapper<User>();
        queryWrapper.eq("type",SystemConstant.NORMAL_USER);//只查普通用户
        queryWrapper.eq(deptId!=null,"deptid",deptId);//部门编号
        //调用查询方法
        List<User> userList = userService.list(queryWrapper);
        //返回数据
        return new DataGridViewResult(userList);

    }

    //检查登录名是否存在
    @RequestMapping("/checkLoginName")
    public String checkLoginName(String loginName){
        Map<String,Object> map = new HashMap<>();
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("loginname",loginName);
        //获取验证的数量
        if(userService.count(queryWrapper)>0){
            map.put(SystemConstant.EXIST,true);
            map.put(SystemConstant.MESSAGE,"登录名称已存在,请重新输入");
        }else{
            map.put(SystemConstant.EXIST,false);
            map.put(SystemConstant.MESSAGE,"登录名称可以使用");
        }
        return JSON.toJSONString(map);
    }

    @RequestMapping("/addUser")
    public JSONResult addUser(User user){
        //入职日期
        user.setHiredate(new Date());
        //使用随机32位
        String salt = UUIDUtil.randomUUID();
        //默认密码
        user.setLoginpwd(PasswordUtil.md5(SystemConstant.DEFAULT_PWD,salt,SystemConstant.HASHITERATIONS));
        //盐值
        user.setSalt(salt);
        //用户类型
        user.setType(SystemConstant.NORMAL_USER);
        //默认头像
        user.setImgpath("defaultimage.jpg");

        if(userService.save(user)){
            return SystemConstant.ADD_SUCCESS;
        }else{
            return SystemConstant.ADD_ERROR;
        }
    }

    @RequestMapping("/updateUser")
    public JSONResult updateUser(User user){
        if(userService.updateById(user)){
            return SystemConstant.UPDATE_SUCCESS;
        }else{
            return SystemConstant.UPDATE_ERROR;
        }
    }

    @RequestMapping("/deleteById")
    public JSONResult deleteById(int id){
        if(userService.removeById(id)){
            return SystemConstant.DELETE_SUCCESS;
        }else{
            return SystemConstant.DELETE_ERROR;
        }
    }

    @RequestMapping("/resetPwd")
    public JSONResult resetPwd(int id){
        //重新生成UUID
        String salt = UUIDUtil.randomUUID();
        //创建用户对象
        User user = new User();
        user.setId(id);
        user.setSalt(salt);
        user.setLoginpwd(PasswordUtil.md5(SystemConstant.DEFAULT_PWD,salt,SystemConstant.HASHITERATIONS));
        if(userService.updateById(user)){
            return SystemConstant.RESET_SUCCESS;
        }
        return SystemConstant.RESET_ERROR;

    }

    @RequestMapping("/loadUserById")
    public DataGridViewResult loadUserById(Integer id){
        return    new  DataGridViewResult(userService.getById(id));
    }

    @RequestMapping("/initRoleByUserId")
    public DataGridViewResult initRoleByUserId(Integer id){
        List<Map<String,Object>> mapList = null;

        try {
            //列表图--查询所有的
            mapList = roleService.listMaps();

            //根据id查询个别的
            Set<Integer> roleIdsList = userService.findUserRoleByUserId(id);

            //循环比较当前用户已经拥有的权限
            for (Map<String, Object> objectmap : mapList) {
                //定义一个标记，默认不选中
                boolean flag = false;
                //取出所有角色的Id;
                int roleId = (int)objectmap.get("id");
                //内层循环  目的是选中为了选中  flag<= true
                for (Integer rid : roleIdsList) {
                    if(rid == roleId){
                        flag = true;
                        break;
                    }
                }
                objectmap.put("LAY_CHECKED",flag);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return    new  DataGridViewResult(Long.valueOf(mapList.size()),mapList);
    }

    @RequestMapping("/saveUserRole")
    public JSONResult saveUserRole(int userId,String roleIds){
        try {
            if(userService.saveUserRole(userId,roleIds)){
                return SystemConstant.DISTRIBUTE_SUCCESS;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return SystemConstant.DISTRIBUTE_ERROR;

    }
}

