package com.Cloudam.sys.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.util.Date;
import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author KazuGin
 * @since 2020-08-20
 */
@TableName("sys_dept")
public class Dept implements Serializable {

    private static final long serialVersionUID=1L;

    /**
     * 部门编号
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 父级部门编号
     */
    private Integer pid;

    /**
     * 父级部门名称
     */
    private String title;

    /**
     * 是否展开(0-展开,1-不展开)
     */
    private Integer open;

    /**
     * 部门地址
     */
    private String address;

    /**
     * 创建时间
     */
    private Date createtime;

    /**
     * 备注
     */
    private String remark;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getOpen() {
        return open;
    }

    public void setOpen(Integer open) {
        this.open = open;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Override
    public String toString() {
        return "Dept{" +
        "id=" + id +
        ", pid=" + pid +
        ", title=" + title +
        ", open=" + open +
        ", address=" + address +
        ", createtime=" + createtime +
        ", remark=" + remark +
        "}";
    }
}
