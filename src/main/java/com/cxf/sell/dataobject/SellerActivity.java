package com.cxf.sell.dataobject;

import com.cxf.sell.dataobject.base.IBaseDBPO;

public class SellerActivity extends IBaseDBPO {
    @Override
    public String _getTableName() {
        return "Seller_Activity";
    }

    @Override
    public String _getPKColumnName() {
        return "activity_Id";
    }

    @Override
    public String _getPKValue() {
        return activityId;
    }

    @Override
    public void _setPKValue(Object var1) {
        this.activityId = (String) var1;
    }

    private String activityId;

    private String sellerId;

    private Byte activityType;

    private String activityDescription;

    public String getActivityId() {
        return activityId;
    }

    public void setActivityId(String activityId) {
        this.activityId = activityId == null ? null : activityId.trim();
    }

    public String getSellerId() {
        return sellerId;
    }

    public void setSellerId(String sellerId) {
        this.sellerId = sellerId == null ? null : sellerId.trim();
    }

    public Byte getActivityType() {
        return activityType;
    }

    public void setActivityType(Byte activityType) {
        this.activityType = activityType;
    }

    public String getActivityDescription() {
        return activityDescription;
    }

    public void setActivityDescription(String activityDescription) {
        this.activityDescription = activityDescription == null ? null : activityDescription.trim();
    }
}