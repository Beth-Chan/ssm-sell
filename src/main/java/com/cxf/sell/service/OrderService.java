package com.cxf.sell.service;

import com.cxf.sell.dataobject.OrderMaster;
import com.cxf.sell.dto.OrderDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface OrderService {
    /**
     *  创建订单
      */
    // 不是OrderMaster了，是OrderDTO，传参也是
    OrderDTO create(OrderDTO orderDTO);

    /**
     *  查询订单
     */
    // 查询单个订单
    OrderDTO findOne(String orderId);

    // 查询订单列表
    Page<OrderDTO> findList(String buyerOpenid, Pageable pageable);

    /**
     *   修改订单状态
     */
    // 取消订单
    OrderDTO cancel(OrderDTO orderDTO);

    // 完结订单
    OrderDTO finish(OrderDTO orderDTO);

    // 支付订单
    OrderDTO paid(OrderDTO orderDTO);
}



