package com.xftxyz.doctorarrival.order.service;

import com.xftxyz.doctorarrival.domain.order.OrderInfo;

public interface AlipayService {
    String getPayPage(Long userId, Long orderId); // 未支付

    Integer queryOrder(Long userId, Long orderId); // 待支付->已支付

    Integer closeOrder(Long userId, Long orderId); // 待支付->已关闭

    boolean closeOrder4Cancel(OrderInfo orderInfo);

    Integer refundOrder(Long userId, Long orderId); // 已支付->待退款

    boolean refundOrder4Cancel(OrderInfo orderInfo);

    Integer queryRefundOrder(Long userId, Long orderId); // 待退款->已退款
}
