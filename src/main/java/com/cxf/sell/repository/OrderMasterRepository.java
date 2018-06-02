package com.cxf.sell.repository;

import com.cxf.sell.dataobject.OrderMaster;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderMasterRepository extends JpaRepository<OrderMaster, String> {
    // 分页查用户的订单
    Page<OrderMaster> findByBuyerOpenid(String buyerOpenid, Pageable pageable);
}