package com.cxf.sell.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.cxf.sell.VO.CartItemVO;
import com.cxf.sell.VO.OrderMasterVO;
import com.cxf.sell.dataobject.OrderDetail;
import com.cxf.sell.dataobject.OrderMaster;
import com.cxf.sell.dataobject.ProductInfo;
import com.cxf.sell.dataobject.base.BasicExample;
import com.cxf.sell.enums.OrderStatusEnum;
import com.cxf.sell.enums.PayStatusEnum;
import com.cxf.sell.exception.MyException;
import com.cxf.sell.service.BasicService;
import com.cxf.sell.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Slf4j
public class OrderServiceImpl implements OrderService {


    @Autowired
    private BasicService basicService;

   /* @Autowired
    private ProductService productService;*/

    @Override
    @Transactional /* 入库失败抛异常时回滚 */
    public OrderMaster create(OrderMasterVO masterVO) {

/*        // OrderDTO是Service层和Controller层中间传递的对象，给Controller层调用的
        // Controller层不可能把所有数据传过来，传过来的话就不用Service层了，数据不能由前端传过来，要从数据库中取出来，尤其是商品单价

        // 订单创建时就生成订单Id
        // 传统写法
        // List<CartDTO> cartDTOList = new ArrayList<>();

        // 1.查询商品（数量，价格）：首先拿到商品列表，遍历
        for (OrderDetail orderDetail : masterVO.getOrderDetailList()) {
            ProductInfo productInfo =  productService.findOne(orderDetail.getProductId());
            if (productInfo == null) {
                throw new SellException(ResultEnum.PRODUCT_NOT_EXIST);
            }

            // 2.计算订单总价（BigDecimal不能直接用* 的符号）
            orderAmount = productInfo.getProductPrice().multiply(new BigDecimal(orderDetail.getProductQuantity())).add(orderAmount);

            // 3.1 订单详情保存到数据库（OrderDetail）
            // 前端只传过来商品id和商品数量
            orderDetail.setDetailId(KeyUtil.genUniqueKey());
            orderDetail.setOrderId(orderId);
            // 商品图片和其他属性在ProductInfo里面
            // 把productInfo的属性复制到orderDetail里
            BeanUtils.copyProperties(productInfo, orderDetail);
            orderDetailRepository.save(orderDetail);

            // CartDTO cartDTO = new CartDTO(orderDetail.getProductId(), orderDetail.getProductQuantity());
            // cartDTOList.add(cartDTO);
        }

        // 3.2 订单主表入库
        OrderMaster orderMaster = new OrderMaster();
        // 这里要特别注意：拷贝属性时要特别注意是否为null
        orderMaster.setOrderId(orderId);
        BeanUtils.copyProperties(masterVO, orderMaster);
        orderMaster.setOrderAmount(orderAmount);
        // 设断点发现，orderStatus和payStatus在执行BeanUtils.copyProperties后被覆盖，所以要重新写回去
        orderMaster.setOrderStatus(OrderStatusEnum.NEW.getCode());
        orderMaster.setPayStatus(PayStatusEnum.WAIT.getCode());
        orderMasterRepository.save(orderMaster);

        // 4.下单成功的话扣库存（Lambda表达式），一旦入库失败就会抛异常
        List<CartDTO> cartDTOList = master.getOrderDetailList().stream().map(e ->
                new CartDTO(e.getProductId(), e.getProductQuantity()))
                .collect(Collectors.toList());
        productService.decreaseStock(cartDTOList);

        return master;*/

        return null;
    }

    @Transactional
    @Override
    public Object createOrder(OrderMaster orderMaster, List<JSONObject> list) throws Exception {
        List<String> proId=new ArrayList<>();//记录商品id
        HashMap<String,Integer> countMap= new HashMap<>();//记录数量
        CartItemVO cartItemVO;
        for (JSONObject jo :list) {
            cartItemVO = jo.toJavaObject(CartItemVO.class);
            proId.add(cartItemVO.getProductId());
            countMap.put(cartItemVO.getProductId(),cartItemVO.getCount());
        }
        //查询商品库存
        BasicExample exa = new BasicExample(ProductInfo.class);
        exa.createCriteria().andVarEqualTo("seller_id",orderMaster.getSellerId()).andVarIn("product_id",proId);
        List<ProductInfo> proList = basicService.selectByExample(exa);
        double amount=0.0;
        for (ProductInfo info:proList){
            Integer count = countMap.get(info.getProductId());
            if(null!=count && null!=info.getProductStock()){
                amount+=count*count;
                info.setProductStock(info.getProductStock()-count);
                countMap.remove(info.getProductId());//已经处理
            }
        }
        if (!countMap.isEmpty()){//如果还有未处理完的购物车，说明数据异常
            throw new MyException("提交订单失败");
        }
        orderMaster.setOrderAmount(amount);
        orderMaster.setOrderStatus(OrderStatusEnum.FINISHED.getCode());
        orderMaster.setPayStatus(PayStatusEnum.SUCCESS.getCode());
        orderMaster.setCreateTime(new Date());

        basicService.insertOne(orderMaster);
        basicService.updateBatch(proList,true);//更新库存
        return null;
    }

    @Override
    @Transactional
    public Object cancel(OrderMaster master) throws Exception {

        // 先判断订单状态
        if (master.getOrderStatus().equals(OrderStatusEnum.FINISHED.getCode())) {
            //log.error("【取消订单】订单状态不正确，orderId={}, orderStatus={}", orderDTO.getOrderId(), orderDTO.getOrderStatus());
            throw new MyException("订单已经结单，不能取消！");
        }else if(master.getOrderStatus().equals(OrderStatusEnum.CANCEL.getCode())){
            throw new MyException("订单已经被取消，不能重复取消！");
        }

        // 修改订单状态
        master.setOrderStatus(OrderStatusEnum.CANCEL.getCode());
        int  upodateNum = basicService.updateOne(master,true);//选择性更新非空属性
        if (upodateNum == 0) {
            //log.error("【取消订单】更新失败，orderMaster={}", orderMaster);
            throw new MyException("【取消订单】失败");
        }
        // 返回库存
        BasicExample exa = new BasicExample(OrderDetail.class);
        exa.createCriteria().andVarEqualTo("order_id",master.getOrderId());
        List<OrderDetail> details = basicService.selectByExample(exa);//查询子订单
        if(details.size()==0){//尽管某种意外订单无详情商品，但是不能抛异常，因为master 需要update
            return master;
        }
        Set<String> proIds = new HashSet<>();
        Map<String,Integer> proNum = new HashMap<>();//记录购买的商品数量
        for (OrderDetail detail : details) {
            proIds.add(detail.getProductId());
            proNum.put(detail.getProductId(),detail.getProductQuantity());
        }
        //查出关联的商品
        exa = new BasicExample(ProductInfo.class);
        exa.createCriteria().andVarIn("product_id",new ArrayList<>(proIds));
        List<ProductInfo> pro = basicService.selectByExample(exa);//查询商品
        if(pro.size()==0){//尽管某种意外订单无详情商品，但是不能抛异常，因为master 需要update
            return master;
        }
        for (ProductInfo info : pro) {//修改库存
           Integer pre = info.getProductStock();//当前
           Integer sellNum = proNum.get(info.getProductId());
           if(null != pre&&sellNum!=null){//确保不为空才能累加，不然出异常
               info.setProductStock(pre+sellNum);
           }else if(sellNum!=null){//数据库数据null异常，把订单数量直接赋值
               info.setProductStock(sellNum);
           }
        }
        if(pro.size()>0){
            basicService.updateBatch(pro,true);//update
        }

        // 如果已支付，需要退款
        if (master.getPayStatus().equals(PayStatusEnum.SUCCESS.getCode())) {
            // TODO
        }
        return master;
    }



    @Override
    @Transactional
    public OrderMaster finish(OrderMaster orderDTO) throws MyException {
        // 第一步同样要判断订单
        if (!orderDTO.getOrderStatus().equals(OrderStatusEnum.NEW.getCode())) {
            //log.error("【完结订单】订单状态不正确, orderId={], orderStatus={}", orderDTO.getOrderId(), orderDTO.getOrderStatus());
            throw new MyException("【完结订单】失败，该订单被取消或者结单");
        }
        // 然后修改订单状态
        orderDTO.setOrderStatus(OrderStatusEnum.FINISHED.getCode());

        if (0 ==  basicService.updateOne(orderDTO,true)) {
            //log.error("【完结订单】更新失败, orderMaster={}", orderMaster);
            throw new MyException("【完结订单】失败");
        }
        return orderDTO;
    }

    @Override
    @Transactional
    public OrderMaster paid(OrderMaster orderDTO) throws MyException {
        // 必须是新订单，并且是等待支付才能执行支付
        // 判断订单状态
        if (!orderDTO.getOrderStatus().equals(OrderStatusEnum.NEW.getCode())) {
            //log.error("【订单支付】订单状态不正确, orderId={], orderStatus={}", orderDTO.getOrderId(), orderDTO.getOrderStatus());
            throw new MyException("【订单支付】失败，该订单被取消或者结单");
        }

        // 修改支付状态
        if (!orderDTO.getPayStatus().equals(PayStatusEnum.WAIT.getCode())) {
            //log.error("【订单支付】订单支付状态不正确, orderDTO={}", orderDTO);
            throw new MyException("【订单支付】失败，当前不是待支付状态");
        }
        // 修改订单状态
        orderDTO.setPayStatus(PayStatusEnum.SUCCESS.getCode());
        if (0 ==  basicService.updateOne(orderDTO,true)) {//update,更新则返回1
            //log.error("【订单支付】更新失败, orderMaster={}", orderMaster);
            throw new MyException("【订单支付】失败");
        }
        return orderDTO;
    }
}
