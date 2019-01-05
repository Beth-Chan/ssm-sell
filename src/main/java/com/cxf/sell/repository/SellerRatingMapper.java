package com.cxf.sell.repository;

import com.cxf.sell.dataobject.SellerRating;

public interface SellerRatingMapper {
    SellerRating selectByPrimaryKey(String ratingId);
}