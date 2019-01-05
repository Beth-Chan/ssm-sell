package com.cxf.sell.controller;

import com.alibaba.fastjson.JSONObject;
import com.cxf.sell.VO.CartItemVO;
import com.cxf.sell.VO.OrderMasterVO;

import com.cxf.sell.controller.base.BasicController;
import com.cxf.sell.dataobject.*;
import com.cxf.sell.dataobject.base.BasicExample;
import com.cxf.sell.exception.MyException;

import com.cxf.sell.service.BuyerService;

import com.cxf.sell.service.OrderService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/buyer/order")
@Slf4j
public class BuyerOrderController extends BasicController{


    @Autowired
    private OrderService orderService;

    @Autowired
    private BuyerService buyerService;


    // 创建订单
    //item={list:[{"productId":"","count":""},{"productId":"","count":""}]]}
    @RequestMapping("createOrder")
    public Object create(OrderMaster orderMaster, String item) throws Exception {
        BuyerInfo buyerInfo = validBuyer();//验证登录
        // 检验一下表单提交之后有没有错误
        if(StringUtils.isEmpty(orderMaster.getBuyerName())||StringUtils.isEmpty(orderMaster.getBuyerAddress())
                ||StringUtils.isEmpty(orderMaster.getBuyerAddress())){
            return failMsg("买家姓名或地址或联系电话不能为空");
        }
        JSONObject basicData =  JSONObject.parseObject(item).getJSONObject("list");
        if (basicData.isEmpty()|| !basicData.containsKey("list")) {
            return failMsg("购物车不能为空");
        }
        List<JSONObject> list = (List<JSONObject>)basicData.get("list");
        if(list.size()==0){
            return failMsg("购物车不能为空");
        }
        orderService.createOrder(orderMaster,list);
        return successMsg("提交成功，等待商家结单");
    }


    // 订单列表
    @RequestMapping("/list")
    public Object list( @RequestParam(value = "page", defaultValue = "0") Integer page, @RequestParam(value = "size", defaultValue = "10") Integer size) throws Exception {
        BuyerInfo buyerInfo = validBuyer();
        BasicExample exa = new BasicExample(OrderMasterVO.class);
        exa.createCriteria().andVarEqualTo("buyer_id",buyerInfo.getBuyerId());
        Page pageData = PageHelper.startPage(page,size);//分页
        basicService.selectByExample(exa);
        List<OrderMasterVO> masters = pageData.getResult();//需要从分页拿数据,不能拿basicService.selectByExample(exa)，因为后续处理的是分页的并返回
        List<String> ids = new ArrayList<>();
        Map<String,OrderMasterVO> masterMap = new HashMap<>();
        for (OrderMasterVO master : masters) {
            ids.add(master.getOrderId());
            masterMap.put(master.getOrderId(),master);
        }
        if(ids.size()>0){//查询关联的子单详情
            //查出关联的商品
            exa = new BasicExample(OrderDetail.class);//构造新的exa对象
            exa.createCriteria().andVarIn("order_id",ids);
            List<OrderDetail> pro = basicService.selectByExample(exa);//查询商品
            for (OrderDetail info : pro) {
                if(masterMap.containsKey(info.getOrderId())){
                    OrderMasterVO master0 = masterMap.get(info.getOrderId());
                    if(master0.getOrderDetailList()!=null){
                        master0.getOrderDetailList().add(info);
                    }else{
                        List<OrderDetail> list = new ArrayList<>();
                        list.add(info);
                        master0.setOrderDetailList(list);
                    }
                }
            }
        }
        // 返回列表
        return successMsg().add("order",masters);
    }


    /**
     *
     * @param orderId
     * @param checkType 0 是通过买家查询，1是通过商家查询
     * @return
     * @throws Exception
     */
    // 订单详情
    @RequestMapping("/detail")
    public Object detail( @RequestParam("orderid") String orderId,String checkType) throws Exception {
        BuyerInfo buyerInfo=null;
        BasicExample exa = new BasicExample(OrderMaster.class);//构造exa对象
        if(checkType.equals("0")) {
             buyerInfo = validBuyer();//验证是否已经登录
             exa.createCriteria().andVarEqualTo("order_id",orderId).andVarEqualTo("buyer_id",buyerInfo.getBuyerId());
        }
        SellerInfo sellerInfo=null;
        if(checkType.equals("1")) {
            sellerInfo = validSeller();//验证是否已经登录
            exa.createCriteria().andVarEqualTo("order_id",orderId).andVarEqualTo("seller_id",sellerInfo.getSellerId());
        }
        OrderMasterVO master = (OrderMasterVO) basicService.selectByExample(exa);
        if(null == master){
            throw new MyException("订单不存在或者已经被删除");
        }
        exa = new BasicExample(OrderDetail.class);//构造新的exa对象
        exa.createCriteria().andVarEqualTo("order_id",master.getOrderId());
        List<OrderDetail> details = basicService.selectByExample(exa);//查询子单
        master.setOrderDetailList(details);
        return successMsg().add("order",master);
    }


    // 取消订单
    @RequestMapping("/cancel")
    public Object cancel(@RequestParam("orderid") String orderId) throws Exception {
        BuyerInfo buyerInfo = validBuyer();
        OrderMaster master = (OrderMaster) buyerService.cancelOrder(buyerInfo.getBuyerId(), orderId);
        return successMsg().add("master",master);
    }
}

