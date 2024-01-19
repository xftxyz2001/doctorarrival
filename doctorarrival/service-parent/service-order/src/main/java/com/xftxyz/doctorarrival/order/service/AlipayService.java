package com.xftxyz.doctorarrival.order.service;

import com.alipay.api.AlipayApiException;

public interface AlipayService {
    String getPayPage(Long userId, Long orderId);

    Integer queryOrder(Long userId, Long orderId);

    Integer closeOrder(Long userId, Long orderId);

    Integer refundOrder(Long userId, Long orderId);

    Integer queryRefundOrder(Long userId, Long orderId);
}