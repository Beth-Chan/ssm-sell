package com.cxf.sell.service;

import com.cxf.sell.dataobject.ProductCategory;

import java.util.List;

public interface CategoryService {
    /* 后台管理 */
    ProductCategory findOne(Integer categoryId);
    List<ProductCategory> findAll();

    /* 买家端 */
    List<ProductCategory> findByCategoryTypeIn(List<Integer> categoryTypeList);
    // 新增和更新，删除暂时用不到
    ProductCategory save(ProductCategory productCategory);
}
