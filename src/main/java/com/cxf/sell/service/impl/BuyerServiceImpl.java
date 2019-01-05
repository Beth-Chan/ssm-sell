package com.cxf.sell.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.cxf.sell.VO.CartItemVO;
import com.cxf.sell.dataobject.OrderMaster;
import com.cxf.sell.dataobject.ProductInfo;
import com.cxf.sell.dataobject.base.BasicExample;
import com.cxf.sell.enums.ResultEnum;
import com.cxf.sell.exception.MyException;
import com.cxf.sell.exception.SellException;
import com.cxf.sell.service.BasicService;
import com.cxf.sell.service.BuyerService;
import com.cxf.sell.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@Transactional
@Service("buyerService")
@Slf4j
public class BuyerServiceImpl implements BuyerService {

    @Autowired
    private OrderService orderService;

    @Autowired
    private BasicService basicService;


    @Override
    public Object cancelOrder(String buyerId, String orderId) throws Exception {
        OrderMaster orderMaster  = checkOrderOwner(buyerId, orderId);
        if (orderMaster ==  null) {
            //log.error("【取消订单】查不到改订单，orderId={}", orderId);
            throw new MyException("订单不存在,取消失败");
        }
        return orderService.cancel(orderMaster);
    }



    private OrderMaster checkOrderOwner(String buyerId, String orderId) throws Exception {
        OrderMaster orderMaster = (OrderMaster) basicService.selectByPrimaryKey(OrderMaster.class,orderId);
        if (orderMaster == null) {
            return null;
        }else if(!orderMaster.getBuyerId().equals(buyerId)){
            return null;
        }
        return orderMaster;
    }
}
