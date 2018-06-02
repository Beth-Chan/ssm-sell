package com.cxf.sell.dto;

import com.cxf.sell.dataobject.OrderDetail;
import com.cxf.sell.enums.OrderStatusEnum;
import com.cxf.sell.enums.PayStatusEnum;
import lombok.Data;

import javax.persistence.Id;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
public class OrderDTO {
    private String orderId;
    private String buyerName;
    private String buyerPhone;
    private String buyerAddress;
    // 买家微信openid
    private String buyerOpenid;
    private BigDecimal orderAmount;
    // 订单状态，默认0为新下单
    private Integer orderStatus;
    // 支付状态，默认0为未支付
    private Integer payStatus;
    private Date createTime;
    private Date updateTime;

    List<OrderDetail> orderDetailList;
}
