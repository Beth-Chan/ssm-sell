package com.cxf.sell.dataobject;

import com.cxf.sell.dataobject.base.IBaseDBPO;

import java.util.Date;

public class OrderDetail extends IBaseDBPO {
    private String detailId;

    private Integer productQuantity;

    private Date createTime;

    private Date updateTime;

    private String productId;

    private String orderId;

    public String getDetailId() {
        return detailId;
    }

    public void setDetailId(String detailId) {
        this.detailId = detailId == null ? null : detailId.trim();
    }

    public Integer getProductQuantity() {
        return productQuantity;
    }

    public void setProductQuantity(Integer productQuantity) {
        this.productQuantity = productQuantity;
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

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId == null ? null : productId.trim();
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId == null ? null : orderId.trim();
    }

    @Override
    public String _getTableName() {
        return "order_detail";
    }

    @Override
    public String _getPKColumnName() {
        return "detail_id";
    }

    @Override
    public String _getPKValue() {
        return detailId;
    }

    @Override
    public void _setPKValue(Object var1) {
        this.detailId = (String) var1;
    }
}