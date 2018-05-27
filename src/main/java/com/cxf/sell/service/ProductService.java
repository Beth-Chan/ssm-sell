package com.cxf.sell.service;

import com.cxf.sell.dataobject.ProductInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProductService {
    ProductInfo findOne(String productId);

    // 买家查询所有在架商品列表
    List<ProductInfo> findUpAll();
    // 后台管理可以查看所有商品列表
    Page<ProductInfo> findAll(Pageable pageable);

    ProductInfo save(ProductInfo productInfo);

    // 增加库存

    // 减少库存
}
