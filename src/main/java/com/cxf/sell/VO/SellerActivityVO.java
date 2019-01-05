package com.cxf.sell.VO;

import com.cxf.sell.dataobject.base.IBaseDBPO;
import com.cxf.sell.utils.RegexUtils;
import com.fasterxml.jackson.annotation.JsonProperty;

public class SellerActivityVO extends IBaseDBPO {
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

    @JsonProperty("type")
    private Integer activityType;

    private String sellerId;
    @JsonProperty("description")
    private String activityDescription;

    public String getActivityId() {
        return activityId;
    }

    public void setActivityId(String activityId) {
        this.activityId = activityId == null ? null : activityId.trim();
    }

    public Integer getActivityType() {
        return activityType;
    }

    public void setActivityType(Integer activityType) {
        this.activityType = activityType;
    }

    public String getSellerId() {
        return sellerId;
    }

    public void setSellerId(String sellerId) {
        this.sellerId = sellerId == null ? null : sellerId.trim();
    }

    public String getActivityDescription() {
        return activityDescription;
    }

    public void setActivityDescription(String activityDescription) {
        this.activityDescription = activityDescription == null ? null : activityDescription.trim();
    }
}