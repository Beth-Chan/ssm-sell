//package com.cxf.sell.service.impl;
//
//import com.cxf.sell.dataobject.ProductInfo;
//import com.cxf.sell.enums.ProductStatusEnum;
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
//import java.util.List;
//
//import static org.junit.Assert.*;
//
//@RunWith(SpringRunner.class)
//@SpringBootTest
//public class ProductServiceImplTest {
//
//    @Autowired
//    private ProductServiceImpl productService;
//
//    @Test
//    public void findOne() {
//        ProductInfo productInfo = productService.findOne("1234");
//        Assert.assertEquals("1234", productInfo.getProductId());
//    }
//
//    @Test
//    public void findUpAll() {
//        List<ProductInfo> productInfoList = (List<ProductInfo>) productService.findUpAll();
//        Assert.assertNotEquals(0, productInfoList.size());
//    }
//
//    @Test
//    public void findAll() {
//        // PageRequest继承自AbstractPageRequest，这个类又实现了Pageable接口，Pageable是一个接口，不是具体实现类
//        PageRequest request = new PageRequest(0,2);
//        Page<ProductInfo> productInfoPage = productService.findAll(request);
//        System.out.println(productInfoPage.getTotalElements());
//    }
//
//    @Test
//    public void save() {
//        ProductInfo productInfo = new ProductInfo();
//        productInfo.setProductId("12345678");
//        productInfo.setProductName("皮皮虾");
//        productInfo.setProductPrice(new BigDecimal(68.5));
//        productInfo.setProductStock(120);
//        productInfo.setProductDescription("很好吃的虾");
//        productInfo.setProductIcon("http://xxxxxxxxxxxxxxxxxxxxxxxxxx.jpg");
//        productInfo.setProductStatus(ProductStatusEnum.DOWN.getCode());
//        productInfo.setCategoryType(2);
//
//        ProductInfo result = productService.save(productInfo);
//        Assert.assertNotNull(result);
//    }
//}