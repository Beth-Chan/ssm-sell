package com.cxf.sell.service;

import com.alibaba.fastjson.JSONObject;
import com.cxf.sell.dataobject.OrderMaster;

import java.util.List;

public interface BuyerService {

    // 取消订单
    Object cancelOrder(String openid, String orderId) throws Exception;

}

