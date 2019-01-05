package com.cxf.sell.repository;

import com.cxf.sell.dataobject.ProductInfo;

public interface ProductInfoMapper {
    ProductInfo selectByPrimaryKey(String productId);
}