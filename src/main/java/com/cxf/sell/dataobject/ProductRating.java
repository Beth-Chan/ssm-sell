package com.cxf.sell.dataobject;

import com.cxf.sell.dataobject.base.IBaseDBPO;

import java.util.Date;

public class ProductRating extends IBaseDBPO {


    @Override
    public String _getTableName() {
        return "product_rating";
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

    private String ratingId;

    private Date rateTime;

    private Byte rateType;

    private String rateText;

    private Integer goodsScore;

    private String productId;

    private String buyerId;

    public String getRatingId() {
        return ratingId;
    }

    public void setRatingId(String ratingId) {
        this.ratingId = ratingId == null ? null : ratingId.trim();
    }

    public Date getRateTime() {
        return rateTime;
    }

    public void setRateTime(Date rateTime) {
        this.rateTime = rateTime;
    }

    public Byte getRateType() {
        return rateType;
    }

    public void setRateType(Byte rateType) {
        this.rateType = rateType;
    }

    public String getRateText() {
        return rateText;
    }

    public void setRateText(String rateText) {
        this.rateText = rateText == null ? null : rateText.trim();
    }

    public Integer getGoodsScore() {
        return goodsScore;
    }

    public void setGoodsScore(Integer goodsScore) {
        this.goodsScore = goodsScore;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId == null ? null : productId.trim();
    }

    public String getBuyerId() {
        return buyerId;
    }

    public void setBuyerId(String buyerId) {
        this.buyerId = buyerId == null ? null : buyerId.trim();
    }
}