package com.Cloudam.bus.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @Author: Cloudam
 * @Description:
 * @Date:22:16星期二
 */
@Controller
@RequestMapping("/bus")
public class BusinessController {

    @RequestMapping("/toCustomerManager")
    public String toCustomerManager(){
        return "business/customer/customerManager";
    }


    //商品分类
    @RequestMapping("/toGoodsTypeLeft")
    public String toGoodsTypeLeft(){
        return "business/goodstype/left";
    }

    @RequestMapping("/toGoodsTypeRight")
    public String toGoodsTypeRight(){
        return "business/goodstype/Right";
    }

    @RequestMapping("/toGoodsManager")
    public String toGoodsTypeManager(){
        return "business/goodstype/goodsTypeManager";
    }

    @RequestMapping("/toLeaveBillManager")
    public String toLeaveBillManager(){
        return "business/leavebill/leavebillManager";
    }


}
