package com.cxf.sell.dataobject;

import com.cxf.sell.enums.OrderStatusEnum;
import com.cxf.sell.enums.PayStatusEnum;
import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Entity
@Data
@DynamicUpdate
public class OrderMaster {
    @Id
    private String orderId;
    private String buyerName;
    private String buyerPhone;
    private String buyerAddress;
    // 买家微信openid
    private String buyerOpenid;
    private BigDecimal orderAmount;
    // 订单状态，默认0为新下单
    private Integer orderStatus = OrderStatusEnum.NEW.getCode();
    // 支付状态，默认0为未支付
    private Integer payStatus = PayStatusEnum.WAIT.getCode();
    private Date createTime;
    private Date updateTime;

    // 表里没有这个，所以要加这个注解映射表和对象时忽略这个，这个字段就不会去数据库找对应列
    // @Transient
    // private List<OrderDetail> orderDetailList;
}
