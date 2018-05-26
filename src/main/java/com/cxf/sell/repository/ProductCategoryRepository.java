package com.cxf.sell.repository;

import com.cxf.sell.dataobject.ProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;

// ProductCategory和Integer是对象和主键类型
public interface ProductCategoryRepository extends JpaRepository<ProductCategory, Integer> {

}
