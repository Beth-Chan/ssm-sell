package com.cxf.sell.repository;

import com.cxf.sell.dataobject.BuyerInfo;

public interface BuyerInfoMapper {
    BuyerInfo selectByPrimaryKey(String buyerId);
}