//package com.cxf.sell.service.impl;
//
//import com.cxf.sell.dataobject.OrderDetail;
//import com.cxf.sell.dto.OrderDTO;
//import com.cxf.sell.enums.OrderStatusEnum;
//import com.cxf.sell.enums.PayStatusEnum;
//import com.cxf.sell.enums.ResultEnum;
//import lombok.extern.slf4j.Slf4j;
//import org.junit.Assert;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.PageRequest;
//import org.springframework.test.context.junit4.SpringRunner;
//
//import javax.xml.ws.Service;
//import java.util.ArrayList;
//import java.util.List;
//
//import static org.junit.Assert.*;
//
//@RunWith(SpringRunner.class)
//@SpringBootTest
//@Slf4j
//public class OrderServiceImplTest {
//
//    @Autowired
//    private OrderServiceImpl orderService;
//
//    private final String BUYER_OPENID = "120120";
//    private final String ORDER_ID = "1527909424163397121";
//
//    @Test
//    public void create() {
//        OrderDTO orderDTO = new OrderDTO();
//        orderDTO.setBuyerName("cxf");
//        orderDTO.setBuyerAddress("zhku");
//        orderDTO.setBuyerPhone("13212345678");
//        orderDTO.setBuyerOpenid(BUYER_OPENID);
//
//        // 购物车
//        List<OrderDetail> orderDetailList = new ArrayList<>();
//
//        OrderDetail o1 = new OrderDetail();
//        // 这里必须是数据库里已经有的商品
//        o1.setProductId("1234");
//        o1.setProductQuantity(1);
//        orderDetailList.add(o1);
//
//        OrderDetail o2 = new OrderDetail();
//        // 这里必须是数据库里已经有的商品
//        o2.setProductId("12345678");
//        o2.setProductQuantity(2);
//        orderDetailList.add(o2);
//
//        orderDTO.setOrderDetailList(orderDetailList);
//
//        OrderDTO result = orderService.create(orderDTO);
//        log.info("【创建订单】result={}", result);
//        Assert.assertNotNull(result);
//    }
//
//    @Test
//    public void findOne() {
//        OrderDTO result = orderService.findOne(ORDER_ID);
//        log.info("【查询单个订单】result={}", result);
//        Assert.assertEquals(ORDER_ID, result.getOrderId());
//    }
//
//    @Test
//    public void findList() {
//        PageRequest request = new PageRequest(0, 2);
//        Page<OrderDTO> orderDTOPage = orderService.findList(BUYER_OPENID, request);
//        Assert.assertNotEquals(0, orderDTOPage.getTotalElements());
//    }
//
//    @Test
//    public void cancel() {
//        OrderDTO orderDTO = orderService.findOne(ORDER_ID);
//        OrderDTO result = orderService.cancel(orderDTO);
//        Assert.assertEquals(OrderStatusEnum.CANCEL.getCode(), result.getOrderStatus());
//    }
//
//    @Test
//    public void finish() {
//        OrderDTO orderDTO = orderService.findOne(ORDER_ID);
//        OrderDTO result = orderService.finish(orderDTO);
//        Assert.assertEquals(OrderStatusEnum.FINISHED.getCode(), result.getOrderStatus());
//    }
//
//    @Test
//    public void paid() {
//        OrderDTO orderDTO = orderService.findOne(ORDER_ID);
//        OrderDTO result = orderService.paid(orderDTO);
//        Assert.assertEquals(PayStatusEnum.SUCCESS.getCode(), result.getPayStatus());
//    }
//}