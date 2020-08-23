package com.Cloudam.sys.vo;

import com.Cloudam.sys.entity.Permission;
import com.Cloudam.sys.entity.Role;

/**
 * @Author: Cloudam
 * @Description:
 * @Date:16:09星期六
 */

public class RoleVo extends Role {
    private Integer page;   //当前页码
    private Integer limit;  //每页显示数量

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }


}
