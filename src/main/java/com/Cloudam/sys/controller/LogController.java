package com.Cloudam.sys.controller;


import com.Cloudam.sys.entity.Log;
import com.Cloudam.sys.service.LogService;
import com.Cloudam.sys.utils.DataGridViewResult;
import com.Cloudam.sys.utils.JSONResult;
import com.Cloudam.sys.utils.SystemConstant;
import com.Cloudam.sys.vo.LogVo;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Arrays;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author KazuGin
 * @since 2020-08-16
 */
@RestController
@RequestMapping("/sys/log")
public class LogController {

    @Resource
    private LogService logService;

    @RequestMapping("/loglist")
    public DataGridViewResult lgolist(LogVo logVo){
        //创建分页信息，参数1：当前页码，参数2：每页显示数量
        IPage<Log> page = new Page<>(logVo.getPage(),logVo.getLimit());

        //创建条件构造器
        QueryWrapper<Log> queryWrapper = new QueryWrapper<>();

        //操作类型
        queryWrapper.eq(StringUtils.isNotBlank(logVo.getType()),"type",logVo.getType());
        //登录名称
        queryWrapper.like(StringUtils.isNotBlank(logVo.getLoginname()),"loginname",logVo.getLoginname());
        //开始时间
        queryWrapper.ge(logVo.getStartTime()!=null,"createtime",logVo.getStartTime());
        //结束时间
        queryWrapper.le(logVo.getEndTime()!=null,"createtime",logVo.getEndTime());

        queryWrapper.orderByDesc("createtime");

        //需要进行分页查询，调用相关的方法
        IPage<Log> logIPage = logService.page(page, queryWrapper);

        //返回数据,总数和数据列表

        return new DataGridViewResult(logIPage.getTotal(),logIPage.getRecords());
    }

    @RequestMapping("/delete")
    public JSONResult delete(String ids){
        //将字符出串拆分成数组
        String [] str = ids.split(",");
        //判断时候删除成功
        if(logService.removeByIds(Arrays.asList(str))){
            return SystemConstant.DELETE_SUCCESS;
        }else {
            return SystemConstant.DELETE_ERROR;
        }

    }

}

