//package com.cxf.sell.repository;
//
//import com.cxf.sell.dataobject.OrderMaster;
//import org.junit.Assert;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.PageRequest;
//import org.springframework.data.domain.Pageable;
//import org.springframework.test.context.junit4.SpringRunner;
//
//import java.math.BigDecimal;
//
//import static org.junit.Assert.*;
//
//@RunWith(SpringRunner.class)
//@SpringBootTest
//public class OrderMasterRepositoryTest {
//
//    private final String OPENID = "122123";
//
//    @Autowired
//    OrderMasterRepository repository;
//
//    @Test
//    public void saveTest() {
//        OrderMaster orderMaster = new OrderMaster();
//        orderMaster.setOrderId("123");
//        orderMaster.setBuyerName("cxf");
//        orderMaster.setBuyerPhone("13412345678");
//        orderMaster.setBuyerAddress("zhku");
//        orderMaster.setBuyerOpenid(OPENID);
//        orderMaster.setOrderAmount(new BigDecimal(53.5));
//
//        OrderMaster result = repository.save(orderMaster);
//        Assert.assertNotNull(result);
//
//    }
//
//    @Test
//    public void findByBuyerOpenid() {
//        // Pageable是一个接口，用的时候用具体实现类
//        PageRequest request = new PageRequest(0, 1);
//        Page<OrderMaster> result = repository.findByBuyerOpenid(OPENID, request);
//        System.out.println(result.getTotalElements());
//    }
//}