package com.cxf.sell.VO;

import com.cxf.sell.dataobject.base.IBaseDBPO;
import com.cxf.sell.utils.RegexUtils;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class SellerInfoVO extends IBaseDBPO {
    @Override
    public String _getTableName() {
        return "seller_info";
    }

    @Override
    public String _getPKColumnName() {
        return "seller_id";
    }

    @Override
    public String _getPKValue() {
        return sellerId;
    }

    @Override
    public void _setPKValue(Object var1) {
        this.sellerId = (String) var1;
    }

    private String sellerId;
    //
    // private String sellerAccount;
    //
    // private String sellerPassword;

    @JsonProperty("name")
    private String sellerName;

    @JsonProperty("description")
    private String sellerDescription;

    @JsonProperty("score")
    private Double sellerScore;

    @JsonProperty("serviceScore")
    private Double sellerServiceScore;

    @JsonProperty("foodScore")
    private Double sellerFoodService;

    @JsonProperty("rankRate")
    private Double sellerRankScore;

    @JsonProperty("minPrice")
    private Double sellerMinPrice;

    @JsonProperty("deliveryPrice")
    private Double sellerDeliveryPrice;

    @JsonProperty("ratingCount")
    private Double sellerRatingCount;

    @JsonProperty("sellCount")
    private Integer sellerSellCount;

    @JsonProperty("bulletin")
    private String sellerBulletin;

    @JsonProperty("info")
    private String sellerExtraInfos;

    @JsonProperty("avatar")
    private String sellerAvatar;

    private Integer deliveryTime;

    public String getSellerId() {
        return sellerId;
    }

    public void setSellerId(String sellerId) {
        this.sellerId = sellerId == null ? null : sellerId.trim();
    }
    //
    // public String getSellerAccount() {
    //     return sellerAccount;
    // }
    //
    // public void setSellerAccount(String sellerAccount) {
    //     this.sellerAccount = sellerAccount == null ? null : sellerAccount.trim();
    // }
    //
    // public String getSellerPassword() {
    //     return sellerPassword;
    // }
    //
    // public void setSellerPassword(String sellerPassword) {
    //     this.sellerPassword = sellerPassword == null ? null : sellerPassword.trim();
    // }

    public String getSellerName() {
        return sellerName;
    }

    public void setSellerName(String sellerName) {
        this.sellerName = sellerName == null ? null : sellerName.trim();
    }

    public String getSellerDescription() {
        return sellerDescription;
    }

    public void setSellerDescription(String sellerDescription) {
        this.sellerDescription = sellerDescription == null ? null : sellerDescription.trim();
    }

    public Double getSellerScore() {
        return sellerScore;
    }

    public void setSellerScore(Double sellerScore) {
        this.sellerScore = sellerScore;
    }

    public Double getSellerServiceScore() {
        return sellerServiceScore;
    }

    public void setSellerServiceScore(Double sellerServiceScore) {
        this.sellerServiceScore = sellerServiceScore;
    }

    public Double getSellerFoodService() {
        return sellerFoodService;
    }

    public void setSellerFoodService(Double sellerFoodService) {
        this.sellerFoodService = sellerFoodService;
    }

    public Double getSellerRankScore() {
        return sellerRankScore;
    }

    public void setSellerRankScore(Double sellerRankScore) {
        this.sellerRankScore = sellerRankScore;
    }

    public Double getSellerMinPrice() {
        return sellerMinPrice;
    }

    public void setSellerMinPrice(Double sellerMinPrice) {
        this.sellerMinPrice = sellerMinPrice;
    }

    public Double getSellerDeliveryPrice() {
        return sellerDeliveryPrice;
    }

    public void setSellerDeliveryPrice(Double sellerDeliveryPrice) {
        this.sellerDeliveryPrice = sellerDeliveryPrice;
    }

    public Double getSellerRatingCount() {
        return sellerRatingCount;
    }

    public void setSellerRatingCount(Double sellerRatingCount) {
        this.sellerRatingCount = sellerRatingCount;
    }

    public Integer getSellerSellCount() {
        return sellerSellCount;
    }

    public void setSellerSellCount(Integer sellerSellCount) {
        this.sellerSellCount = sellerSellCount;
    }

    public String getSellerBulletin() {
        return sellerBulletin;
    }

    public void setSellerBulletin(String sellerBulletin) {
        this.sellerBulletin = sellerBulletin == null ? null : sellerBulletin.trim();
    }

    public String getSellerExtraInfos() {
        return sellerExtraInfos;
    }

    public void setSellerExtraInfos(String sellerExtraInfos) {
        this.sellerExtraInfos = sellerExtraInfos == null ? null : sellerExtraInfos.trim();
    }

    public String getSellerAvatar() {
        return sellerAvatar;
    }

    public void setSellerAvatar(String sellerAvatar) {
        this.sellerAvatar = sellerAvatar == null ? null : sellerAvatar.trim();
    }

    public Integer getDeliveryTime() {
        return deliveryTime;
    }

    public void setDeliveryTime(Integer deliveryTime) {
        this.deliveryTime = deliveryTime;
    }


    private List<SellerActivityVO> supports;

    public List<SellerActivityVO> getSupports() {
        return supports;
    }

    public void setSupports(List<SellerActivityVO> supports) {
        this.supports = supports;
    }
}