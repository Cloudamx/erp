package com.Cloudam.sys.controller;


import com.Cloudam.sys.entity.Log;
import com.Cloudam.sys.service.LogService;
import com.Cloudam.sys.utils.JSONResult;
import com.Cloudam.sys.utils.SystemConstant;
import com.Cloudam.sys.vo.LoginUserVo;
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
import java.util.Date;

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

}

