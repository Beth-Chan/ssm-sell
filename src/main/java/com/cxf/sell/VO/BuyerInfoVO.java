package com.cxf.sell.VO;

import com.cxf.sell.dataobject.base.IBaseDBPO;

public class BuyerInfoVO extends IBaseDBPO {
    private String buyerId;

/*    private String buyerAccount;

    private String buyerPassword;*/

    private String buyerName;

    private String buyerPhone;

    private String buyerAddress;

    private String buyerAvatar;

    public String getBuyerId() {
        return buyerId;
    }

    public void setBuyerId(String buyerId) {
        this.buyerId = buyerId == null ? null : buyerId.trim();
    }
/*

    public String getBuyerAccount() {
        return buyerAccount;
    }

    public void setBuyerAccount(String buyerAccount) {
        this.buyerAccount = buyerAccount == null ? null : buyerAccount.trim();
    }

    public String getBuyerPassword() {
        return buyerPassword;
    }

    public void setBuyerPassword(String buyerPassword) {
        this.buyerPassword = buyerPassword == null ? null : buyerPassword.trim();
    }
*/

    public String getBuyerName() {
        return buyerName;
    }

    public void setBuyerName(String buyerName) {
        this.buyerName = buyerName == null ? null : buyerName.trim();
    }

    public String getBuyerPhone() {
        return buyerPhone;
    }

    public void setBuyerPhone(String buyerPhone) {
        this.buyerPhone = buyerPhone == null ? null : buyerPhone.trim();
    }

    public String getBuyerAddress() {
        return buyerAddress;
    }

    public void setBuyerAddress(String buyerAddress) {
        this.buyerAddress = buyerAddress == null ? null : buyerAddress.trim();
    }

    public String getBuyerAvatar() {
        return buyerAvatar;
    }

    public void setBuyerAvatar(String buyerAvatar) {
        this.buyerAvatar = buyerAvatar == null ? null : buyerAvatar.trim();
    }

    @Override
    public String _getTableName() {
        return "buyer_info";
    }

    @Override
    public String _getPKColumnName() {
        return "buyer_id";
    }

    @Override
    public String _getPKValue() {
        return buyerId;
    }

    @Override
    public void _setPKValue(Object var1) {
        this.buyerId = (String) var1;
    }
}