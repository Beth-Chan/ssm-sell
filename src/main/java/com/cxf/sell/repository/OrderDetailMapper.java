package com.cxf.sell.repository;

import com.cxf.sell.dataobject.OrderDetail;

public interface OrderDetailMapper {
    OrderDetail selectByPrimaryKey(String detailId);
}