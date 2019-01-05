package com.cxf.sell.service.impl;

import com.cxf.sell.dataobject.BuyerInfo;
import com.cxf.sell.dataobject.SellerInfo;
import com.cxf.sell.dataobject.base.BasicExample;
import com.cxf.sell.dataobject.base.Msg;
import com.cxf.sell.repository.SellerInfoMapper;
import com.cxf.sell.service.BasicService;
import com.cxf.sell.service.RegAndLoginService;
import com.cxf.sell.utils.UUID32;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("regAndLoginService")
@Transactional
public class RegAndLoginServiceImpl implements RegAndLoginService {
    @Autowired
    BasicService basicService;

    @Override
    public Object sellerRegister(SellerInfo seller) throws Exception {
        //检查数据库是否已经存在账号,忽略大小写提示设置
        BasicExample example = new BasicExample(SellerInfo.class);
        example.createCriteria().andVarEqualTo("seller_account",seller.getSellerAccount());
        List list = basicService.selectByExample(example);
        if(list.size() > 0){
            return Msg.fail("账号已存在，注册失败");
        }
        seller.setSellerId(UUID32.getID());
        basicService.insertOne(seller);
        return Msg.success("注册成功!");
    }


    @Override
    public Object buyerRegister(BuyerInfo buyerInfo) throws Exception {
        //检查数据库是否已经存在账号,忽略大小写提示设置
        BasicExample example = new BasicExample(BuyerInfo.class);
        example.createCriteria().andVarEqualTo("buyer_account",buyerInfo.getBuyerAccount());
        List list = basicService.selectByExample(example);
        if(list.size()>0){
            return Msg.fail("账号已存在，注册失败");
        }
        buyerInfo.setBuyerId(UUID32.getID());
        basicService.insertOne(buyerInfo);
        return Msg.success("注册成功!");
    }

}
