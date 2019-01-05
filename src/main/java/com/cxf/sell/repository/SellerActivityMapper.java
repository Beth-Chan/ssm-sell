package com.cxf.sell.repository;

import com.cxf.sell.dataobject.SellerActivity;

public interface SellerActivityMapper {
    SellerActivity selectByPrimaryKey(String activityId);
}