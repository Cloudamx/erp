package com.Cloudam.bus.entity;

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
 * @since 2020-08-25
 */
@TableName("bus_customer")
public class Customer implements Serializable {

    private static final long serialVersionUID=1L;

    /**
     * 客户编号
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 客户姓名
     */
    private String customername;

    /**
     * 客户地址
     */
    private String address;

    /**
     * 客户公司电话
     */
    private String telephone;

    /**
     * 联系人
     */
    private String linkman;

    /**
     * 联系人手机
     */
    private String phone;

    /**
     * 开户银行
     */
    private String bank;

    /**
     * 客户银行账号
     */
    private String account;

    /**
     * 联系人邮箱
     */
    private String email;

    /**
     * 联系人传真
     */
    private String fax;

    /**
     * 客户邮编
     */
    private String zipcode;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCustomername() {
        return customername;
    }

    public void setCustomername(String customername) {
        this.customername = customername;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getLinkman() {
        return linkman;
    }

    public void setLinkman(String linkman) {
        this.linkman = linkman;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getBank() {
        return bank;
    }

    public void setBank(String bank) {
        this.bank = bank;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    @Override
    public String toString() {
        return "Customer{" +
        "id=" + id +
        ", customername=" + customername +
        ", address=" + address +
        ", telephone=" + telephone +
        ", linkman=" + linkman +
        ", phone=" + phone +
        ", bank=" + bank +
        ", account=" + account +
        ", email=" + email +
        ", fax=" + fax +
        ", zipcode=" + zipcode +
        "}";
    }
}
