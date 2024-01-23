package com.xftxyz.doctorarrival.order.service.impl;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.domain.AlipayTradeCloseModel;
import com.alipay.api.domain.AlipayTradeFastpayRefundQueryModel;
import com.alipay.api.domain.AlipayTradePagePayModel;
import com.alipay.api.domain.AlipayTradeRefundModel;
import com.alipay.api.request.*;
import com.alipay.api.response.*;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.xftxyz.doctorarrival.config.RabbitMQConfig;
import com.xftxyz.doctorarrival.domain.order.OrderInfo;
import com.xftxyz.doctorarrival.exception.BusinessException;
import com.xftxyz.doctorarrival.order.autoconfigure.AlipayProperties;
import com.xftxyz.doctorarrival.order.mapper.OrderInfoMapper;
import com.xftxyz.doctorarrival.order.service.AlipayService;
import com.xftxyz.doctorarrival.result.ResultEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

@Service
@RequiredArgsConstructor
public class AlipayServiceImpl implements AlipayService {

    private final OrderInfoMapper orderInfoMapper;

    private final AlipayClient alipayClient;
    private final AlipayProperties alipayProperties;

    private final RabbitTemplate rabbitTemplate;

    // https://opendocs.alipay.com/apis/009zih
    @Override
    public String getPayPage(Long userId, Long orderId) {
        OrderInfo orderInfo = getOrderInfo(userId, orderId);
        if (!OrderInfo.ORDER_STATUS_UNPAID.equals(orderInfo.getOrderStatus())) {
            throw new BusinessException(ResultEnum.ORDER_STATUS_NOT_ALLOW);
        }

        // 构造支付宝订单信息
        AlipayTradePagePayRequest request = new AlipayTradePagePayRequest();
        AlipayTradePagePayModel model = new AlipayTradePagePayModel();
        model.setOutTradeNo(orderId.toString());
        model.setTotalAmount(orderInfo.getAmount().toString());
        String subject = orderInfo.getHospitalName() + "-" + orderInfo.getDepartmentName() + "-" + orderInfo.getDoctorName() + "-" + orderInfo.getReserveDate();
        model.setSubject(subject);
        model.setProductCode("FAST_INSTANT_TRADE_PAY");
        request.setBizModel(model);
        request.setReturnUrl(alipayProperties.getSiteOrigin() + "/user/order/detail?orderId=" + orderId);
        try {
            AlipayTradePagePayResponse response = alipayClient.pageExecute(request, "POST");
            return response.getBody();
        } catch (AlipayApiException e) {
            throw new BusinessException(ResultEnum.ALIPAY_ERROR);
        }
    }

    // https://opendocs.alipay.com/apis/009zir
    @Override
    public Integer queryOrder(Long userId, Long orderId) {
        OrderInfo orderInfo = getOrderInfo(userId, orderId);
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
                    // 通知医院
                    rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE_DIRECT_ORDER,
                            RabbitMQConfig.ROUTING_ORDER, orderInfo);
                }
            } catch (AlipayApiException e) {
                throw new BusinessException(ResultEnum.ALIPAY_ERROR);
            }
        }
        return orderInfo.getOrderStatus();
    }

    // https://opendocs.alipay.com/apis/009zhm
    @Override
    public Integer closeOrder(Long userId, Long orderId) {
        OrderInfo orderInfo = getOrderInfo(userId, orderId);
        // 待支付
        if (OrderInfo.ORDER_STATUS_UNPAID.equals(orderInfo.getOrderStatus())) {
            AlipayTradeCloseRequest request = new AlipayTradeCloseRequest();
            AlipayTradeCloseModel model = new AlipayTradeCloseModel();
            model.setOutTradeNo(orderId.toString());
            request.setBizModel(model);
            try {
                alipayClient.execute(request);
                orderInfo.setOrderStatus(OrderInfo.ORDER_STATUS_CLOSED);
                if (orderInfoMapper.updateById(orderInfo) <= 0) {
                    throw new BusinessException(ResultEnum.ORDER_STATUS_UPDATE_FAILED);
                }
                // 通知医院
                rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE_DIRECT_ORDER,
                        RabbitMQConfig.ROUTING_ORDER, orderInfo);
            } catch (AlipayApiException e) {
                throw new BusinessException(ResultEnum.ALIPAY_ERROR);
            }
        }
        return orderInfo.getOrderStatus();
    }

    @Override
    public boolean closeOrder4Cancel(OrderInfo orderInfo) {
        AlipayTradeCloseRequest request = new AlipayTradeCloseRequest();
        AlipayTradeCloseModel model = new AlipayTradeCloseModel();
        model.setOutTradeNo(orderInfo.getId().toString());
        request.setBizModel(model);
        try {
            AlipayTradeCloseResponse response = alipayClient.execute(request);
            orderInfo.setOrderStatus(OrderInfo.ORDER_STATUS_CLOSED);
            if (orderInfoMapper.updateById(orderInfo) <= 0) {
                throw new BusinessException(ResultEnum.ORDER_STATUS_UPDATE_FAILED);
            }
            // 通知医院
            rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE_DIRECT_ORDER,
                    RabbitMQConfig.ROUTING_ORDER, orderInfo);
            return response.isSuccess();
        } catch (AlipayApiException e) {
            throw new BusinessException(ResultEnum.ALIPAY_ERROR);
        }
    }

    // https://opendocs.alipay.com/apis/009zi3
    @Override
    public Integer refundOrder(Long userId, Long orderId) {
        OrderInfo orderInfo = getOrderInfo(userId, orderId);
        // 已支付
        if (OrderInfo.ORDER_STATUS_PAID.equals(orderInfo.getOrderStatus())) {
            AlipayTradeRefundRequest request = new AlipayTradeRefundRequest();
            AlipayTradeRefundModel model = new AlipayTradeRefundModel();
            model.setOutTradeNo(orderId.toString());
            model.setRefundAmount(orderInfo.getAmount().toString());
            request.setBizModel(model);
            try {
                alipayClient.execute(request);
                orderInfo.setOrderStatus(OrderInfo.ORDER_STATUS_REFUNDING);
                if (orderInfoMapper.updateById(orderInfo) <= 0) {
                    throw new BusinessException(ResultEnum.ORDER_STATUS_UPDATE_FAILED);
                }
                // 通知医院
                rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE_DIRECT_ORDER,
                        RabbitMQConfig.ROUTING_ORDER, orderInfo);
            } catch (AlipayApiException e) {
                throw new BusinessException(ResultEnum.ALIPAY_ERROR);
            }
        }
        return orderInfo.getOrderStatus();
    }

    @Override
    public boolean refundOrder4Cancel(OrderInfo orderInfo) {
        AlipayTradeRefundRequest request = new AlipayTradeRefundRequest();
        AlipayTradeRefundModel model = new AlipayTradeRefundModel();
        model.setOutTradeNo(orderInfo.getId().toString());
        model.setRefundAmount(orderInfo.getAmount().toString());
        request.setBizModel(model);
        try {
            AlipayTradeRefundResponse response = alipayClient.execute(request);
            orderInfo.setOrderStatus(OrderInfo.ORDER_STATUS_REFUNDING);
            if (orderInfoMapper.updateById(orderInfo) <= 0) {
                throw new BusinessException(ResultEnum.ORDER_STATUS_UPDATE_FAILED);
            }
            // 通知医院
            rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE_DIRECT_ORDER,
                    RabbitMQConfig.ROUTING_ORDER, orderInfo);
            return response.isSuccess();
        } catch (AlipayApiException e) {
            throw new BusinessException(ResultEnum.ALIPAY_ERROR);
        }
    }

    // https://opendocs.alipay.com/apis/009zi4
    @Override
    public Integer queryRefundOrder(Long userId, Long orderId) {
        OrderInfo orderInfo = getOrderInfo(userId, orderId);
        // 退款中
        if (OrderInfo.ORDER_STATUS_REFUNDING.equals(orderInfo.getOrderStatus())) {
            AlipayTradeFastpayRefundQueryRequest request = new AlipayTradeFastpayRefundQueryRequest();
            AlipayTradeFastpayRefundQueryModel model = new AlipayTradeFastpayRefundQueryModel();
            model.setOutTradeNo(orderId.toString());
            try {
                AlipayTradeFastpayRefundQueryResponse response = alipayClient.execute(request);
                if ("REFUND_SUCCESS".equals(response.getRefundStatus())) {
                    orderInfo.setOrderStatus(OrderInfo.ORDER_STATUS_REFUNDED);
                    if (orderInfoMapper.updateById(orderInfo) <= 0) {
                        throw new BusinessException(ResultEnum.ORDER_STATUS_UPDATE_FAILED);
                    }
                    // 通知医院
                    rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE_DIRECT_ORDER,
                            RabbitMQConfig.ROUTING_ORDER, orderInfo);
                }
            } catch (AlipayApiException e) {
                throw new BusinessException(ResultEnum.ALIPAY_ERROR);
            }
        }
        return orderInfo.getOrderStatus();
    }

    private OrderInfo getOrderInfo(Long userId, Long orderId) {
        LambdaQueryWrapper<OrderInfo> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(OrderInfo::getUserId, userId);
        wrapper.eq(OrderInfo::getId, orderId);
        OrderInfo orderInfo = orderInfoMapper.selectOne(wrapper);
        if (ObjectUtils.isEmpty(orderInfo)) {
            throw new BusinessException(ResultEnum.ORDER_NOT_EXIST);
        }
        return orderInfo;
    }

}
