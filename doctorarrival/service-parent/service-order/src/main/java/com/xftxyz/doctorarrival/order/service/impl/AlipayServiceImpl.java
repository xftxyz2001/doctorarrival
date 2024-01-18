package com.xftxyz.doctorarrival.order.service.impl;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.domain.AlipayTradePagePayModel;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.alipay.api.request.AlipayTradeQueryRequest;
import com.alipay.api.response.AlipayTradePagePayResponse;
import com.alipay.api.response.AlipayTradeQueryResponse;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.xftxyz.doctorarrival.domain.order.OrderInfo;
import com.xftxyz.doctorarrival.exception.BusinessException;
import com.xftxyz.doctorarrival.order.mapper.OrderInfoMapper;
import com.xftxyz.doctorarrival.order.service.AlipayService;
import com.xftxyz.doctorarrival.result.ResultEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Service
@RequiredArgsConstructor
public class AlipayServiceImpl implements AlipayService {

    private final OrderInfoMapper orderInfoMapper;

    private final AlipayClient alipayClient;

    @Override
    public String getPayPage(Long userId, Long orderId) {
        LambdaQueryWrapper<OrderInfo> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(OrderInfo::getUserId, userId);
        wrapper.eq(OrderInfo::getId, orderId);
        OrderInfo orderInfo = orderInfoMapper.selectOne(wrapper);
        if (ObjectUtils.isEmpty(orderInfo)) {
            throw new BusinessException(ResultEnum.ORDER_NOT_EXIST);
        }
        if (!OrderInfo.ORDER_STATUS_UNPAID.equals(orderInfo.getOrderStatus())) {
            throw new BusinessException(ResultEnum.ORDER_STATUS_NOT_ALLOW);
        }

        // 构造支付宝订单信息
        AlipayTradePagePayRequest request = new AlipayTradePagePayRequest();
        AlipayTradePagePayModel model = new AlipayTradePagePayModel();
        model.setOutTradeNo(orderId.toString());
        BigDecimal amount = new BigDecimal(orderInfo.getAmount()).divide(new BigDecimal(100), 2, RoundingMode.HALF_UP);
        model.setTotalAmount(amount.toString());
        String subject = orderInfo.getHospitalName() + "-" + orderInfo.getDepartmentName() + "-" + orderInfo.getDoctorName() + "-" + orderInfo.getReserveDate();
        model.setSubject(subject);
        model.setProductCode("FAST_INSTANT_TRADE_PAY");
        request.setBizModel(model);
        request.setReturnUrl("http://localhost:3000/user/order/detail?orderId=" + orderId);
        try {
            AlipayTradePagePayResponse response = alipayClient.pageExecute(request, "POST");
            return response.getBody();
        } catch (AlipayApiException e) {
            throw new BusinessException(ResultEnum.ALIPAY_ERROR);
        }
    }

    @Override
    public Integer queryOrder(Long userId, Long orderId) {
        LambdaQueryWrapper<OrderInfo> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(OrderInfo::getUserId, userId);
        wrapper.eq(OrderInfo::getId, orderId);
        OrderInfo orderInfo = orderInfoMapper.selectOne(wrapper);
        if (ObjectUtils.isEmpty(orderInfo)) {
            throw new BusinessException(ResultEnum.ORDER_NOT_EXIST);
        }
        // 待支付
        if (OrderInfo.ORDER_STATUS_UNPAID.equals(orderInfo.getOrderStatus())) {
            AlipayTradeQueryRequest request = new AlipayTradeQueryRequest();
            AlipayTradePagePayModel model = new AlipayTradePagePayModel();
            model.setOutTradeNo(orderId.toString());
            request.setBizModel(model);
            try {
                AlipayTradeQueryResponse response = alipayClient.execute(request);
                if ("TRADE_SUCCESS".equals(response.getTradeStatus())) {
                    orderInfo.setOrderStatus(OrderInfo.ORDER_STATUS_PAID);
                    if (orderInfoMapper.updateById(orderInfo) <= 0) {
                        throw new BusinessException(ResultEnum.ORDER_STATUS_UPDATE_FAILED);
                    }
                }
            } catch (AlipayApiException e) {
                throw new BusinessException(ResultEnum.ALIPAY_ERROR);
            }
        }
        return orderInfo.getOrderStatus();
    }

    @Override
    public Integer closeOrder(Long userId, Long orderId) {
        return null;
    }

    @Override
    public Integer refundOrder(Long userId, Long orderId) {
        return null;
    }

    @Override
    public Integer queryRefundOrder(Long userId, Long orderId) {
        return null;
    }
}
