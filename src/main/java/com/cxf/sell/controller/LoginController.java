package com.cxf.sell.controller;

import com.cxf.sell.VO.BuyerInfoVO;
import com.cxf.sell.VO.SellerInfoVO;
import com.cxf.sell.controller.base.BasicController;
import com.cxf.sell.dataobject.BuyerInfo;
import com.cxf.sell.dataobject.SellerInfo;
import com.cxf.sell.dataobject.base.BasicExample;
import com.cxf.sell.service.RegAndLoginService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("open")
public class LoginController extends BasicController {

    @Autowired
    RegAndLoginService regAndLoginService;

    @RequestMapping("seller/register")
    public Object sellerRegister(SellerInfo seller) throws Exception {
        if(StringUtils.isEmpty(seller.getSellerAccount())){
            return failMsg("账号不能为空");
        }else if(StringUtils.isEmpty(seller.getSellerPassword())){
            return failMsg("密码不能为空");
        }
        return regAndLoginService.sellerRegister(seller);
    }

    @RequestMapping("seller/login")
    public Object sellerLogin(SellerInfo seller) throws Exception {
        if(StringUtils.isEmpty(seller.getSellerAccount())){
            return failMsg("账号不能为空");
        }else if(StringUtils.isEmpty(seller.getSellerPassword())){
            return failMsg("密码不能为空");
        }
        BasicExample example = new BasicExample(SellerInfo.class);
        example.createCriteria().andVarEqualTo("seller_account",seller.getSellerAccount())
                .andVarEqualTo("seller_password",seller.getSellerPassword());
        SellerInfo sellerSuccess = (SellerInfo) basicService.selectOneByExample(example);
        if(null == sellerSuccess){
            return failMsg("账号或密码有误，请重新登录");
        }
        session.setAttribute("user",sellerSuccess);
        SellerInfoVO vo = new SellerInfoVO();
        BeanUtils.copyProperties(sellerSuccess,vo);
        return   successMsg("登录成功").add("seller",vo);
    }

    @RequestMapping("getPreSeller")
    public Object getPreSeller() throws Exception {
        SellerInfo seller = validSeller();
        SellerInfoVO vo = new SellerInfoVO();
        BeanUtils.copyProperties(vo,seller);
        return   successMsg().add("seller",vo);
    }

    @RequestMapping("buyer/register")
    public Object buyerRegister(BuyerInfo buyerInfo) throws Exception {
        if(StringUtils.isEmpty(buyerInfo.getBuyerAccount())){
            return failMsg("账号不能为空");
        }else if(StringUtils.isEmpty(buyerInfo.getBuyerPassword())){
            return failMsg("密码不能为空");
        }
        return regAndLoginService.buyerRegister(buyerInfo);
    }

    @RequestMapping("buyer/login")
    public Object buyerLogin(BuyerInfo buyerInfo) throws Exception {
        if(StringUtils.isEmpty(buyerInfo.getBuyerAccount())){
            return failMsg("账号不能为空");
        }else if(StringUtils.isEmpty(buyerInfo.getBuyerPassword())){
            return failMsg("密码不能为空");
        }
        BasicExample example = new BasicExample(BuyerInfo.class);
        example.createCriteria().andVarEqualTo("buyer_account",buyerInfo.getBuyerAccount())
                .andVarEqualTo("buyer_password",buyerInfo.getBuyerPassword());
        BuyerInfo  buyerInfoSuc = (BuyerInfo) basicService.selectOneByExample(example);
        if(null == buyerInfoSuc){
            return failMsg("账号或密码有误，请重新登录");
        }
        session.setAttribute("user",buyerInfoSuc);
        BuyerInfoVO vo = new BuyerInfoVO();
        BeanUtils.copyProperties(buyerInfoSuc,vo);
        return   successMsg("登录成功").add("buyer",vo);
    }

    @RequestMapping("getPreBuyer")
    public Object getPreBuyer() throws Exception {
        BuyerInfo buyer = validBuyer();
        BuyerInfoVO vo = new BuyerInfoVO();
        BeanUtils.copyProperties(vo,buyer);
        return   successMsg().add("buyer",vo);
    }
}
