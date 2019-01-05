package com.cxf.sell.VO;

import com.cxf.sell.dataobject.base.IBaseDBPO;

import java.util.Date;

public class SellerRatingVO extends IBaseDBPO {
    private String ratingId;

    private String sellerId;

    private String buyerId;

    private Date ratingTime;

    private Integer ratingType;

    private String ratingText;

    private Double sellerScore;

    public String getRatingId() {
        return ratingId;
    }

    public void setRatingId(String ratingId) {
        this.ratingId = ratingId == null ? null : ratingId.trim();
    }

    public String getSellerId() {
        return sellerId;
    }

    public void setSellerId(String sellerId) {
        this.sellerId = sellerId == null ? null : sellerId.trim();
    }

    public String getBuyerId() {
        return buyerId;
    }

    public void setBuyerId(String buyerId) {
        this.buyerId = buyerId == null ? null : buyerId.trim();
    }

    public Date getRatingTime() {
        return ratingTime;
    }

    public void setRatingTime(Date ratingTime) {
        this.ratingTime = ratingTime;
    }

    public Integer getRatingType() {
        return ratingType;
    }

    public void setRatingType(Integer ratingType) {
        this.ratingType = ratingType;
    }

    public String getRatingText() {
        return ratingText;
    }

    public void setRatingText(String ratingText) {
        this.ratingText = ratingText == null ? null : ratingText.trim();
    }

    public Double getSellerScore() {
        return sellerScore;
    }

    public void setSellerScore(Double sellerScore) {
        this.sellerScore = sellerScore;
    }

    @Override
    public String _getTableName() {
        return "seller_rating";
    }

    @Override
    public String _getPKColumnName() {
        return "rating_id";
    }

    @Override
    public String _getPKValue() {
        return ratingId;
    }

    @Override
    public void _setPKValue(Object var1) {
        this.ratingId= (String) var1;
    }
}