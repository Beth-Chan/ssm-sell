package com.cxf.sell.service.impl;

import com.cxf.sell.dataobject.OrderMaster;
import com.cxf.sell.exception.MyException;
import com.cxf.sell.service.BasicService;
import com.cxf.sell.service.BuyerService;
import com.cxf.sell.service.OrderService;
import com.cxf.sell.service.SellerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("sellerService")
@Slf4j
public class SellerServiceImpl implements SellerService {

    @Autowired
    private OrderService orderService;

    @Autowired
    private BasicService basicService;


    @Override
    public Object cancelOrder(String sellerId, String orderId) throws Exception {
        OrderMaster orderMaster  = checkOrderOwner(sellerId, orderId);
        if (orderMaster ==  null) {
            //log.error("【取消订单】查不到改订单，orderId={}", orderId);
            throw new MyException("订单不存在,取消失败");
        }
        return orderService.cancel(orderMaster);
    }

    private OrderMaster checkOrderOwner(String sellerId, String orderId) throws Exception {
        OrderMaster orderMaster = (OrderMaster) basicService.selectByPrimaryKey(OrderMaster.class,orderId);
        if (orderMaster == null) {
            return null;
        }else if(!orderMaster.getSellerId().equals(sellerId)){
            return null;
        }
        return orderMaster;
    }
}
