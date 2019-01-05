package com.cxf.sell.repository;

import com.cxf.sell.dataobject.ProductRating;

public interface ProductRatingMapper {
    ProductRating selectByPrimaryKey(String ratingId);
}