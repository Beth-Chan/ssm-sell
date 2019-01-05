package com.cxf.sell.dataobject;

import com.cxf.sell.dataobject.base.IBaseDBPO;

import java.util.Date;

public class ProductCategory extends IBaseDBPO {
    private String categoryId;

    private String sellerId;

    private String categoryName;

    private Integer categoryType;

    private Date createTime;

    private Date updateTime;

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId == null ? null : categoryId.trim();
    }

    public String getSellerId() {
        return sellerId;
    }

    public void setSellerId(String sellerId) {
        this.sellerId = sellerId == null ? null : sellerId.trim();
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public Integer getCategoryType() {
        return categoryType;
    }

    public void setCategoryType(Integer categoryType) {
        this.categoryType = categoryType;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    public String _getTableName() {
        return "product_category";
    }

    @Override
    public String _getPKColumnName() {
        return "category_id";
    }

    @Override
    public String _getPKValue() {
        return categoryId;
    }

    @Override
    public void _setPKValue(Object var1) {
        this.categoryId = (String) var1;
    }
}