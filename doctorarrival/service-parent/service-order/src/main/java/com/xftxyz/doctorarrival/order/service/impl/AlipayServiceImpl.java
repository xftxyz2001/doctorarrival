package com.xftxyz.doctorarrival.order.service.impl;

import com.xftxyz.doctorarrival.order.service.AlipayService;
import org.springframework.stereotype.Service;

@Service
public class AlipayServiceImpl implements AlipayService {
    @Override
    public String getPayPage(Long userId, Long orderId) {
        return null;
    }

    @Override
    public Integer queryOrder(Long userId, Long orderId) {
        return null;
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
