package com.cxf.sell.service;

import com.cxf.sell.dataobject.BuyerInfo;
import com.cxf.sell.dataobject.SellerInfo;

public interface RegAndLoginService {
    Object sellerRegister(SellerInfo seller) throws Exception;

    Object buyerRegister(BuyerInfo buyerInfo) throws Exception;
}
