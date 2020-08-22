package com.Cloudam.sys.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author KazuGin
 * @since 2020-08-15
 */
@TableName("sys_permission")
public class Permission implements Serializable {

    private static final long serialVersionUID=1L;

    /**
     * 菜单权限编号
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 所属菜单
     */
    private Integer pid;

    /**
     * 权限类型[menu/permission]
     */
    private String type;

    /**
     * 菜单权限名称
     */
    private String title;

    /**
     * 权限编码[只有type= permission才有  user:view]
     */
    private String percode;

    /**
     * 菜单图标
     */
    private String icon;

    /**
     * 菜单跳转请求路径
     */
    private String href;

    /**
     * 菜单是否展开(0展开，1不展开)
     */
    private Integer open;

    /**
     * 排序码
     */
    private Integer ordernum;

    /**
     * 状态【0不可用1可用】
     */
    private Integer available;


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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPercode() {
        return percode;
    }

    public void setPercode(String percode) {
        this.percode = percode;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public Integer getOpen() {
        return open;
    }

    public void setOpen(Integer open) {
        this.open = open;
    }

    public Integer getOrdernum() {
        return ordernum;
    }

    public void setOrdernum(Integer ordernum) {
        this.ordernum = ordernum;
    }

    public Integer getAvailable() {
        return available;
    }

    public void setAvailable(Integer available) {
        this.available = available;
    }

    @Override
    public String toString() {
        return "Permission{" +
        "id=" + id +
        ", pid=" + pid +
        ", type=" + type +
        ", title=" + title +
        ", percode=" + percode +
        ", icon=" + icon +
        ", href=" + href +
        ", open=" + open +
        ", ordernum=" + ordernum +
        ", available=" + available +
        "}";
    }
}
