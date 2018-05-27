package com.cxf.sell.repository;

import com.cxf.sell.dataobject.ProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

// ProductCategory和Integer是对象和主键类型
public interface ProductCategoryRepository extends JpaRepository<ProductCategory, Integer> {

    // 通过category_type查商品列表
    List<ProductCategory> findByCategoryTypeIn(List<Integer> categoryTypeList);
}

