package com.cxf.sell.controller;

import com.cxf.sell.VO.OrderMasterVO;
import com.cxf.sell.controller.base.BasicController;
import com.cxf.sell.dataobject.*;
import com.cxf.sell.dataobject.base.BasicExample;
import com.cxf.sell.service.BasicService;
import com.cxf.sell.service.SellerService;
import com.cxf.sell.utils.DatePattern;
import com.cxf.sell.utils.RegexUtils;
import com.cxf.sell.utils.UUID32;
import com.cxf.sell.utils.XDateUtils;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("seller")
public class SellerController extends BasicController {


    @Autowired
    private BasicService basicService;

    @Autowired
    private SellerService sellerService;

    @RequestMapping("getSellCount")
    public Object getSellCount() throws Exception {
        SellerInfo seller = validSeller();
        //日订单
        String date = XDateUtils.nowToString(DatePattern.DATE_ONLY);
        String dateStart= date+ " 00:00:00";
        String dateEnd= date+ " 23:59:59";
        BasicExample exa = new BasicExample(OrderMaster.class);
        BasicExample.Criteria criteria = exa.createCriteria();
        criteria.andVarBetween("create_time",dateStart,dateEnd).andVarEqualTo("seller_id",seller.getSellerId());
        int dateCount = basicService.countByExample(exa);
        //月订单
        String thisMonthStart= XDateUtils.thisMonthFirstDate().toString();
        String nextMonthStart= XDateUtils.thisMonthFirstDate().toString();
        BasicExample exa1 = new BasicExample(OrderMaster.class);
        BasicExample.Criteria criteria1 = exa.createCriteria();
        criteria1.andVarBetween("create_time",thisMonthStart,nextMonthStart).andVarEqualTo("seller_id",seller.getSellerId());
        int monthCount = basicService.countByExample(exa1);
        return successMsg().add("dateCount",dateCount).add("monthCount",monthCount);
    }

    // 订单列表
    @RequestMapping("order/list")
    public Object list(@RequestParam(value = "page", defaultValue = "0") Integer page, @RequestParam(value = "size", defaultValue = "10") Integer size) throws Exception {
        SellerInfo seller = validSeller();
        BasicExample exa = new BasicExample(OrderMasterVO.class);
        exa.createCriteria().andVarEqualTo("seller_id",seller.getSellerId());
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

    // 取消订单
    @RequestMapping("/cancel")
    public Object cancel(@RequestParam("orderid") String orderId) throws Exception {
        SellerInfo seller = validSeller();
        OrderMaster master = (OrderMaster) sellerService.cancelOrder(seller.getSellerId(), orderId);
        return successMsg().add("master",master);
    }

    //类目列表
    @RequestMapping("/getCateList")
    public Object getCateList() throws Exception {
        SellerInfo seller = validSeller();
        BasicExample exa = new BasicExample(ProductCategory.class);
        exa.createCriteria().andVarEqualTo("seller_id",seller.getSellerId());
        List list = basicService.selectByExample(exa);
        return successMsg().add("cateList",list);

    }
    //add类目
    @RequestMapping("addCate")
    public Object addCate(ProductCategory category) throws Exception {
        SellerInfo seller = validSeller();
        category.setCategoryId(UUID32.getID());
        if(StringUtils.isEmpty(category.getCategoryName())){
            return failMsg("名称不能为空");
        }
        category.setCreateTime(new Date());
        category.setSellerId(seller.getSellerId());
       basicService.insertOne(category);
        return successMsg();
    }

    //update类目
    @RequestMapping("updateCate")
    public Object updateCate(ProductCategory category) throws Exception {
        if(StringUtils.isEmpty(category.getCategoryId())){
            return failMsg("id不能为空");
        }
        if(StringUtils.isEmpty(category.getCategoryName())){
            return failMsg("名称不能为空");
        }
        ProductCategory old = (ProductCategory) basicService.selectByPrimaryKey(ProductCategory.class,category.getCategoryId());
        SellerInfo seller = validSeller();

        if(null == old){
            return failMsg("类目不存在或已经被删除");
        }
        if(!seller.getSellerId().equals(old.getSellerId())){
            return failMsg("更新失败，此类目属于其他商家");
        }
        category.setUpdateTime(new Date());
        basicService.updateOne(category,true);//选择性更新不为null的字段
        return successMsg();
    }
    //del类目
    @RequestMapping("delCate")
    public Object delCate(String categoryId) throws Exception {
        if(StringUtils.isEmpty(categoryId)){
            return failMsg("id不能为空");
        }
        ProductCategory old = (ProductCategory) basicService.selectByPrimaryKey(ProductCategory.class,categoryId);
        SellerInfo seller = validSeller();
        if(null == old){
            return failMsg("类目不存在或已经被删除");
        }
        if(!seller.getSellerId().equals(old.getSellerId())){
            return failMsg("删除失败，此类目属于其他商家");
        }
        basicService.deleteByPrimaryKey(ProductCategory.class,categoryId);
        return successMsg();
    }

    //商品列表
    @RequestMapping("/getProList")
    public Object getProList(String key,String categoryId) throws Exception {
        SellerInfo seller = validSeller();
        BasicExample exa = new BasicExample(ProductInfo.class);
       BasicExample.Criteria criteria = exa.createCriteria();
        if(StringUtils.isEmpty(categoryId)){
            criteria.andVarEqualTo("category_id",categoryId);
        }
        if(StringUtils.isEmpty(key)){
            criteria.andVarEqualTo("product_name",key);
        }
        List list = basicService.selectByExample(exa);
        return successMsg().add("proList",list);
    }

    //add
    @RequestMapping("addPro")
    public Object addPro(ProductInfo pro) throws Exception {
        SellerInfo seller = validSeller();
        pro.setProductId(UUID32.getID());
        if(StringUtils.isEmpty(pro.getProductName())){
            return failMsg("名称不能为空");
        }
        pro.setProductStatus(1);//默认上架
        pro.setCreateTime(new Date());
        basicService.insertOne(pro);
        return successMsg();
    }

    //update
    @RequestMapping("updatePro")
    public Object updatePro(ProductInfo pro) throws Exception {
        if(StringUtils.isEmpty(pro.getCategoryId())){
            return failMsg("id不能为空");
        }
        if(StringUtils.isEmpty(pro.getProductName())){
            return failMsg("名称不能为空");
        }
        ProductInfo old = (ProductInfo) basicService.selectByPrimaryKey(ProductInfo.class,pro.getProductId());
        ProductCategory cate = (ProductCategory) basicService.selectByPrimaryKey(ProductCategory.class,old.getCategoryId());
        SellerInfo seller = validSeller();
        if(!seller.getSellerId().equals(cate.getSellerId())){
            return failMsg("更新失败，此商品属于其他商家");
        }
        if(null == old){
            return failMsg("菜品不存在或已经被删除");
        }

        old.setUpdateTime(new Date());
        basicService.updateOne(old,true);//选择性更新不为null的字段
        return successMsg();
    }
    //del
    @RequestMapping("delPro")
    public Object delPro(String proId) throws Exception {
        if(StringUtils.isEmpty(proId)){
            return failMsg("id不能为空");
        }
        ProductInfo old = (ProductInfo) basicService.selectByPrimaryKey(ProductInfo.class,proId);
        ProductCategory cate = (ProductCategory) basicService.selectByPrimaryKey(ProductCategory.class,old.getCategoryId());
        SellerInfo seller = validSeller();
        if(!seller.getSellerId().equals(cate.getSellerId())){
            return failMsg("更新失败，此商品属于其他商家");
        }
        if(null == old){
            return failMsg("菜品不存在或已经被删除");
        }
        basicService.deleteByPrimaryKey(ProductCategory.class,proId);
        return successMsg();
    }

    //上架或者下架 status 0 下架，1上架
    @RequestMapping("updateProStatus")
    public Object updateProStatus(String proId,int status) throws Exception {
        if(status!=0||status!=1){
            return failMsg("更新失败，更新状态有误");
        }
        if(StringUtils.isEmpty(proId)){
            return failMsg("id不能为空");
        }
        ProductInfo old = (ProductInfo) basicService.selectByPrimaryKey(ProductInfo.class,proId);
        ProductCategory cate = (ProductCategory) basicService.selectByPrimaryKey(ProductCategory.class,old.getCategoryId());
        SellerInfo seller = validSeller();
        if(!seller.getSellerId().equals(cate.getSellerId())){
            return failMsg("更新失败，此商品属于其他商家");
        }
        if(null == old){
            return failMsg("菜品不存在或已经被删除");
        }
        old.setProductStatus(status);
        basicService.updateOne(old,true);
        return successMsg();
    }

}
