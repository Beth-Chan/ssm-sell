package com.cxf.sell.service;

public interface SellerService {

    // 取消订单
    Object cancelOrder(String sellerId, String orderId) throws Exception;
}

