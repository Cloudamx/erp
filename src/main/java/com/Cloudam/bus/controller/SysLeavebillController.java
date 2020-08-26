package com.Cloudam.bus.controller;


import ch.qos.logback.core.net.SyslogConstants;
import com.Cloudam.bus.common.utils.DataGridViewResult;
import com.Cloudam.bus.common.utils.JSONResult;
import com.Cloudam.bus.common.utils.SystemConstant;
import com.Cloudam.bus.entity.SysLeavebill;
import com.Cloudam.bus.service.SysLeavebillService;
import com.Cloudam.bus.vo.LeavebillVo;
import com.Cloudam.sys.entity.User;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author KazuGin
 * @since 2020-08-26
 */
@RestController
@RequestMapping("/bus/leavebill")
public class SysLeavebillController {

    @Resource
    private SysLeavebillService sysLeavebillService;

    @RequestMapping("/leavebillList")
    public DataGridViewResult leavebillList(LeavebillVo leavebillVo, HttpSession session){
        //获取当前用户
        User user = (User) session.getAttribute(SystemConstant.LOGINUSER);
        //请假人
        leavebillVo.setUserid(user.getId());


        //创建分页查询
        IPage<SysLeavebill> page = new Page<>(leavebillVo.getPage(),leavebillVo.getLimit());

        //调用分页
        try {
            sysLeavebillService.findLeaveBillByPage(page,leavebillVo);
        } catch (Exception e) {
            e.printStackTrace();
        }


        return new DataGridViewResult(page.getTotal(),page.getRecords());
    }

    @RequestMapping("/addLeavebill")
    public JSONResult addLeavebill(SysLeavebill sysLeavebill, HttpServletRequest request){
        User user = (User) request.getSession().getAttribute(SystemConstant.LOGINUSER);
        sysLeavebill.setUserid(user.getId());
        sysLeavebill.setCreatetime(new Date());
        if(sysLeavebill.getState()==SystemConstant.LEAVE_CREATE_STATE){
            sysLeavebill.setState(SystemConstant.LEAVE_CREATE_STATE);
        }else if(sysLeavebill.getState()==SystemConstant.LEAVE_CHECKING_STATE) {
            sysLeavebill.setState(SystemConstant.LEAVE_CHECKING_STATE);
            sysLeavebill.setCommittime(new Date());
        }
        sysLeavebill.setCheckPerson(user.getMgr());

        if(sysLeavebillService.save(sysLeavebill)){
            return SystemConstant.ADD_SUCCESS;
        }
        return SystemConstant.ADD_ERROR;


    }


    @RequestMapping("/updateLeavebill")
    public JSONResult updateLeavebill(SysLeavebill sysLeavebill){

        if(sysLeavebill.getState()!=null){
            sysLeavebill.setState(SystemConstant.LEAVE_CREATE_STATE);
        }else {
            sysLeavebill.setState(SystemConstant.LEAVE_CHECKING_STATE);
            sysLeavebill.setCommittime(new Date());
        }
        if(sysLeavebillService.updateById(sysLeavebill)){
            return SystemConstant.UPDATE_SUCCESS;
        }
        return SystemConstant.UPDATE_ERROR;
    }
}

