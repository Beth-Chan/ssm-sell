package com.cxf.sell.repository;

import com.cxf.sell.dataobject.OrderMaster;

public interface OrderMasterMapper {
    OrderMaster selectByPrimaryKey(String orderId);
}