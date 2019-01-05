package com.cxf.sell.service;

import com.alibaba.fastjson.JSONObject;
import com.cxf.sell.VO.OrderMasterVO;
import com.cxf.sell.dataobject.OrderMaster;
import com.cxf.sell.exception.MyException;
import com.github.pagehelper.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface OrderService {
    /**
     *  创建订单
     */
    // 不是OrderMaster了，是OrderMaster，传参也是
    OrderMaster create(OrderMasterVO OrderMaster);

    Object createOrder(OrderMaster orderMaster, List<JSONObject> list) throws Exception;

    /**
     *   修改订单状态
     */
    // 取消订单
    Object cancel(OrderMaster OrderMaster) throws Exception;

    // 完结订单
    OrderMaster finish(OrderMaster OrderMaster) throws MyException;

    // 支付订单
    OrderMaster paid(OrderMaster OrderMaster) throws MyException;


}
