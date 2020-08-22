package com.Cloudam.sys.controller;


import com.Cloudam.sys.entity.Notice;
import com.Cloudam.sys.entity.User;
import com.Cloudam.sys.service.NoticeService;
import com.Cloudam.sys.utils.DataGridViewResult;
import com.Cloudam.sys.utils.JSONResult;
import com.Cloudam.sys.utils.SystemConstant;
import com.Cloudam.sys.vo.NoticeVo;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.Arrays;
import java.util.Date;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author KazuGin
 * @since 2020-08-17
 */
@RestController
@RequestMapping("/sys/notice")
public class NoticeController {

    @Resource
    private NoticeService noticeService;

    @RequestMapping("/noticelist")
    public DataGridViewResult lgolist(NoticeVo noticeVo){
        //创建分页信息，参数1：当前页码，参数2：每页显示数量
        IPage<Notice> page = new Page<>(noticeVo.getPage(),noticeVo.getLimit());

        //创建条件构造器
        QueryWrapper<Notice> queryWrapper = new QueryWrapper<>();

        //标题
        queryWrapper.like(StringUtils.isNotBlank(noticeVo.getTitle()),"title;",noticeVo.getTitle());
        //发布人
        queryWrapper.like(StringUtils.isNotBlank(noticeVo.getOpername()),"opername",noticeVo.getOpername());
        //开始时间
        queryWrapper.ge(noticeVo.getStartTime()!=null,"createtime",noticeVo.getStartTime());
        //结束时间
        queryWrapper.le(noticeVo.getEndTime()!=null,"createtime",noticeVo.getEndTime());

        //根据创建时间进行排序
        queryWrapper.orderByDesc("createtime");

        //需要进行分页查询，调用相关的方法
        IPage<Notice> noticeIPage = noticeService.page(page, queryWrapper);


        //返回数据,总数和数据列表

        return new DataGridViewResult(noticeIPage.getTotal(),noticeIPage.getRecords());
    }

    @RequestMapping("/delete")
    public JSONResult delete(String ids){
        //将字符出串拆分成数组
        String [] str = ids.split(",");
        //判断时候删除成功
        if(noticeService.removeByIds(Arrays.asList(str))){
            return SystemConstant.DELETE_SUCCESS;
        }else {
            return SystemConstant.DELETE_ERROR;
        }
    }

    @PostMapping("/addNotice")
    public JSONResult addNotce(Notice notice, HttpSession session){
        //获取当前登录用户
        User user = (User)session.getAttribute(SystemConstant.LOGINUSER);
        notice.setCreatetime(new Date());
        //发布人
        notice.setOpername(user.getName());
        if(noticeService.save(notice)){
            //添加成功
            return SystemConstant.ADD_SUCCESS;
        }else{
            return SystemConstant.ADD_ERROR;
        }

    }

    /**
     * 修改公告
     * @param notice
     * @param session
     * @return JSONResult
     */



    @PostMapping("/updateNotice")
    public JSONResult updateNotce(Notice notice, HttpSession session){
        //修改时间
        notice.setModifytime(new Date());

        if(noticeService.updateById(notice)){
            //添加成功
            return SystemConstant.UPDATE_SUCCESS;
        }else{
            return SystemConstant.UPDATE_ERROR;
        }

    }
    @PostMapping("/deleteById")
    public JSONResult deleteNotice(int id){
        if(noticeService.removeById(id)){
            //添加成功
            return SystemConstant.DELETE_SUCCESS;
        }else{
            return SystemConstant.DELETE_ERROR;
        }

    }



}

