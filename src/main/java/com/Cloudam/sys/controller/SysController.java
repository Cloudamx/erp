package com.Cloudam.sys.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @Author: Cloudam
 * @Description:
 * @Date:14:32星期六
 */
@Controller
@RequestMapping("/sys")
public class SysController {

    @RequestMapping("/index")
    public String toIndex(){


        return "system/home/index";
    }

    /**
     * 去工作页面
     * @return
     */

    @RequestMapping("/toDesktopManager")
    public String toDesktopManager(){


        return "system/home/desktopManager";
    }
    /**
     * 去日志管理页面
     * @return
     */

    @RequestMapping("/toLogManager")
    public String toLogManager(){


        return "system/log/logManager";
    }


    /**
     * 去公告管理页面
     * @return
     */

    @RequestMapping("/toNoticeManager")
    public String tonoticeManager(){
        return "system/notice/noticeManager";
    }

    /**
     * 部门管理界面左边
     */
    @RequestMapping("/toDeptLeft")
    public String toDeptLeft(){
        return "system/dept/left";
    }
    //部门右页面
    @RequestMapping("/toDeptRight")
    public String toDeptRight(){
        return "system/dept/right";
    }
    //部门主页面
    @RequestMapping("/toDeptManager")
    public String toDeptManager(){
        return "system/dept/deptManager";
    }

    /**
     * 跳转到菜单管理页面
     * @return
     */
    @RequestMapping("/toMenuManager")
    public String toMenuManager() {
        return "system/menu/menuManager";
    }
    /**
     * 跳转到菜单管理页面-left
     * @return
     */
    @RequestMapping("/toMenuLeft")
    public String toMenuLeft() {
        return "system/menu/left";
    }
    /**
     * 跳转到菜单管理页面-right
     * @return
     */
    @RequestMapping("/toMenuRight")
    public String toMenuRight() {
        return "system/menu/right";
    }

}
