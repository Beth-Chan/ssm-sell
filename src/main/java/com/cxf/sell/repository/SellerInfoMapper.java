package com.cxf.sell.repository;

import com.cxf.sell.dataobject.SellerInfo;

import java.util.List;

public interface SellerInfoMapper {
    SellerInfo selectByPrimaryKey(String sellerId);

    List<String> selectPswByAccount (String sellerAccount); //对应





}