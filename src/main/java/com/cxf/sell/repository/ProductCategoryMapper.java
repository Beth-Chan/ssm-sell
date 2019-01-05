package com.cxf.sell.repository;

import com.cxf.sell.dataobject.ProductCategory;

public interface ProductCategoryMapper {
    ProductCategory selectByPrimaryKey(String categoryId);
}