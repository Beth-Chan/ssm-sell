//package com.cxf.sell.repository;
//
//import com.cxf.sell.dataobject.ProductCategory;
//import org.junit.Assert;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.junit4.SpringRunner;
//
//import javax.transaction.Transactional;
//import java.util.Arrays;
//import java.util.List;
//
//@RunWith(SpringRunner.class)
//@SpringBootTest
//public class ProductCategoryRepositoryTest {
//
//    @Autowired
//    private ProductCategoryRepository repository;
//
//    @Test
//    public void findOneTest() {
//        // 找出id为1的记录
//        ProductCategory productCategory = repository.findOne(1);
//        System.out.println(productCategory.toString());
//    }
//
//    @Test
//    // @Transactional /* 测试完数据库不要插入数据 */
//    public void saveTest() { // 新增或修改的话都是用saveTest
//        // 更新往往是先查出来，再判断权限等信息，再可以更改
//        // ProductCategory productCategory = repository.findOne(2);
//        // productCategory.setCategoryType(10);
//        // repository.save(productCategory);
//
//        // // 新增得先构造对象
//        // ProductCategory productCategory = new ProductCategory();
//        // // 修改要加setCategoryId，新增可以不用，因为默认自增id
//        // productCategory.setCategoryId(2);
//        // productCategory.setCategoryName("精选热菜");
//        // productCategory.setCategoryType(3);
//        // repository.save(productCategory);
//
//        ProductCategory productCategory = new ProductCategory("女生最爱", 5);
//        ProductCategory result = repository.save(productCategory);
//        Assert.assertNotNull(result);
//        // 等价于Assert.assertNotEquals(null, result);
//    }
//
//    @Test
//    public void findByCategoryTypeInTest() {
//        List<Integer> list = Arrays.asList(2, 3, 4);
//        List<ProductCategory> result = repository.findByCategoryTypeIn(list);
//        Assert.assertNotEquals(0, result.size());
//    }
//}