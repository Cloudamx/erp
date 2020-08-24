package com.Cloudam.sys.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import org.springframework.context.annotation.Bean;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 
 * </p>
 *
 * @author KazuGin
 * @since 2020-08-15
 */
@TableName("sys_user")
public class User implements Serializable {

    private static final long serialVersionUID=1L;

    /**
     * 用户编号
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 登录名称
     */
    private String loginname;

    /**
     * 登录密码
     */
    private String loginpwd;

    /**
     * 用户真实姓名
     */
    private String name;

    /**
     * 用户地址
     */
    private String address;

    /**
     * 性别(0男1女)
     */
    private Integer sex;

    /**
     * 所在部门编号
     */
    private Integer deptid;

    /**
     * 入职日期
     */
    private Date hiredate;

    /**
     * 所属领导
     */
    private Integer mgr;

    /**
     * 用户类型[0超级管理员1，管理员，2普通用户]
     */
    private Integer type;

    /**
     * 头像地址
     */
    private String imgpath;

    /**
     * 是否可用(0否1可)
     */
    private Integer available;

    /**
     * 密码加密盐值
     */
    private String salt;

    /**
     * 备注
     */
    private String remark;

    /**
     * 部门名称
     */
    @TableField(exist = false)
    private String deptName;

    /**
     * 领导名称
     */
    @TableField(exist = false)
    private String mgrName;



    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLoginname() {
        return loginname;
    }

    public void setLoginname(String loginname) {
        this.loginname = loginname;
    }

    public String getLoginpwd() {
        return loginpwd;
    }

    public void setLoginpwd(String loginpwd) {
        this.loginpwd = loginpwd;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public Integer getDeptid() {
        return deptid;
    }

    public void setDeptid(Integer deptid) {
        this.deptid = deptid;
    }

    public Date getHiredate() {
        return hiredate;
    }

    public void setHiredate(Date hiredate) {
        this.hiredate = hiredate;
    }

    public Integer getMgr() {
        return mgr;
    }

    public void setMgr(Integer mgr) {
        this.mgr = mgr;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getImgpath() {
        return imgpath;
    }

    public void setImgpath(String imgpath) {
        this.imgpath = imgpath;
    }

    public Integer getAvailable() {
        return available;
    }

    public void setAvailable(Integer available) {
        this.available = available;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public String getMgrName() {
        return mgrName;
    }

    public void setMgrName(String mgrName) {
        this.mgrName = mgrName;
    }

    @Override
    public String toString() {
        return "User{" +
        "id=" + id +
        ", loginname=" + loginname +
        ", loginpwd=" + loginpwd +
        ", name=" + name +
        ", address=" + address +
        ", sex=" + sex +
        ", deptid=" + deptid +
        ", hiredate=" + hiredate +
        ", mgr=" + mgr +
        ", type=" + type +
        ", imgpath=" + imgpath +
        ", available=" + available +
        ", salt=" + salt +
        ", remark=" + remark +
        "}";
    }
}
