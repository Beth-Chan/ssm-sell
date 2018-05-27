package com.cxf.sell.dataobject;

import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * 表名是product_category
 * @Entity注解 数据库映射为对象
 */
@Entity
@DynamicUpdate
@Data /* lombok getter, setter and toString() */
public class ProductCategory {

    /** 类目id. */
    @Id /* 主键 */
    @GeneratedValue /* 自增 */
    private Integer categoryId;

    private String categoryName;

    private Integer categoryType;

    public ProductCategory() {
    }

    public ProductCategory(String categoryName, Integer categoryType) {
        this.categoryName = categoryName;
        this.categoryType = categoryType;
    }
}
