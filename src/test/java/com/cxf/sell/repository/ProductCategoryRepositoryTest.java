package com.cxf.sell.repository;

import com.cxf.sell.dataobject.ProductCategory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductCategoryRepositoryTest {

    @Autowired
    private ProductCategoryRepository repository;

    @Test
    public void findOneTest() {
        // 写个1，就自动出来id:
        ProductCategory productCategory = repository.findOne(1);
        System.out.println(productCategory.toString());
    }

    @Test
    public void saveTest() { // 更新修改的话也是用saveTest
        // 新增得先构造对象
        ProductCategory productCategory = new ProductCategory();
        // 修改要加setCategoryId，新增可以不用，因为默认自增id
        productCategory.setCategoryId(2);
        productCategory.setCategoryName("精选热菜");
        productCategory.setCategoryType(3);
        repository.save(productCategory);
    }
}