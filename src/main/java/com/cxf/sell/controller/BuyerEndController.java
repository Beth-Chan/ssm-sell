package com.cxf.sell.controller;

import com.cxf.sell.VO.*;
import com.cxf.sell.controller.base.BasicController;
import com.cxf.sell.dataobject.*;
import com.cxf.sell.dataobject.base.BasicExample;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController /* @ResponseBody和@Controller */
@RequestMapping("/api") /*路由*/
public class BuyerEndController extends BasicController {

/*    @Autowired
    private SellerService sellerService;*/

    @RequestMapping("/goods")
    public Object getGoodList(String sellerId) throws Exception {
        // 因为继承BasicController，BasicController有属性BasicService basicService
        SellerInfo sellerInfo = (SellerInfo) basicService.selectByPrimaryKey(SellerInfo.class,sellerId);
        if(null == sellerInfo){
            return failMsg("该商家不存在");
        }

        //获得类目
        BasicExample exa = new BasicExample(ProductCategoryVO.class);
        exa.createCriteria().andVarEqualTo("seller_id",sellerId);
        List<ProductCategoryVO> pList =  basicService.selectByExample(exa);
        if(pList==null || pList.size()==0){
            return successMsg("该商家还没设置任何商品类目！");
        }
        Map<String,ProductCategoryVO> pMap = new HashMap<>();
        List<String> pIds= new ArrayList<>();
        // 对象数组
        for (ProductCategoryVO category : pList) {
            pIds.add(category.getCategoryId());
            pMap.put(category.getCategoryId().toString(),category);
        }
        //获得商品
        BasicExample infoExa = new BasicExample(ProductInfoVO.class);
        infoExa.createCriteria().andVarIn("category_id",pIds);
        List<ProductInfoVO> infoList = basicService.selectByExample(infoExa);
        if(infoList==null || infoList.size()==0){
            return successMsg("该商家还没上架任何商品！");
        }
        Map<String,ProductInfoVO> infoMap = new HashMap<>();
        List<String> infoIds= new ArrayList<>();
        for (ProductInfoVO info : infoList) {
            infoIds.add(info.getProductId());
            infoMap.put(info.getProductId(),info);
            ProductCategoryVO pVO= pMap.get(info.getCategoryId());
            if(null != pVO.getProductInfoVOList()){
                pVO.getProductInfoVOList().add(info);
            }else {
                List<ProductInfoVO> list = new ArrayList<>();
                list.add(info);
                pVO.setProductInfoVOList(list);
            }
        }
        //获得评论
        BasicExample rateExa = new BasicExample(ProductRatingVO.class);
        rateExa.createCriteria().andVarIn("product_id",infoIds);
        List<ProductRatingVO> rating = basicService.selectByExample(rateExa);
        for (ProductRatingVO rate : rating) {
            ProductInfoVO pVO= infoMap.get(rate.getProductId());
            if(null != pVO.getRatings()){
                pVO.getRatings().add(rate);
            }else {
                List<ProductRatingVO> list = new ArrayList<>();
                list.add(rate);
                pVO.setRatings(list);
            }
        }
        return  successMsg().add("goods",pList);

    }


    @RequestMapping("/ratings")
    public Object getSellerRating(String sellerId) throws Exception {
        BasicExample exa = new BasicExample(SellerRatingVO.class);
        exa.createCriteria().andVarEqualTo("seller_id",sellerId);
        List<SellerRatingVO> rList =  basicService.selectByExample(exa);
        return successMsg().add("ratings",rList);
    }

    @RequestMapping("/seller")
    public Object getSellerInfo(String sellerId) throws Exception {
        //获得商家详情
        SellerInfoVO seller = (SellerInfoVO) basicService.selectByPrimaryKey(SellerInfoVO.class,sellerId);
        if(seller == null){
            return failMsg("商家不存在");
        }
        //获取活动
        BasicExample exa = new BasicExample(SellerActivityVO.class);
        exa.createCriteria().andVarEqualTo("seller_id",sellerId);
        List<SellerActivityVO> sList =  basicService.selectByExample(exa);
        seller.setSupports(sList);
        return successMsg().add("seller",seller);
    }


    @RequestMapping("/sellerList")
    public Object getSellerList(@RequestParam(defaultValue = "1") int pageNum, @RequestParam(defaultValue = "10") int pageSize) throws Exception {

        //获取
        BasicExample exa = new BasicExample(SellerInfo.class);//初始化表名，主键
        //生成条件
        BasicExample.Criteria criteria = exa.createCriteria();
        exa.setOrderByClause("score desc");
        Page page = PageHelper.startPage(pageNum, pageSize);
        List<SellerInfo> sList =  basicService.selectByExample(exa);
        PageInfo pageInfo = new PageInfo(page.getResult());
        return successMsg().add("sellerList", pageInfo);
    }











}
