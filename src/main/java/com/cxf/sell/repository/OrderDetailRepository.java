package com.cxf.sell.repository;

import com.cxf.sell.dataobject.OrderDetail;
import com.cxf.sell.dataobject.OrderMaster;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderDetailRepository extends JpaRepository<OrderDetail, String> {
    // 先从order_master表拿到order_id，再用order_id在order_detail表查，一个order_id可能会有多条详情，所以是List
    List<OrderDetail> findByOrderId(String orderId);
}
