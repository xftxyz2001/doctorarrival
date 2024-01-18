package com.xftxyz.doctorarrival.order.controller;

import com.xftxyz.doctorarrival.helper.JwtHelper;
import com.xftxyz.doctorarrival.order.service.AlipayService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/order/alipay")
public class AlipayApiController {

    private final AlipayService alipayService;

    // 获取下单并支付页面
    @GetMapping("/auth/pay/{orderId}")
    public String getPayPage(@RequestHeader(JwtHelper.X_USER_ID) Long userId,
                             @PathVariable("orderId") Long orderId) {
        return alipayService.getPayPage(userId, orderId);
    }

    // 交易查询
    @GetMapping("/auth/query/{orderId}")
    public Integer queryOrder(@RequestHeader(JwtHelper.X_USER_ID) Long userId,
                              @PathVariable("orderId") Long orderId) {
        return alipayService.queryOrder(userId, orderId);
    }

    // 交易关闭
    @PutMapping("/auth/close/{orderId}")
    public Integer closeOrder(@RequestHeader(JwtHelper.X_USER_ID) Long userId,
                              @PathVariable("orderId") Long orderId) {
        return alipayService.closeOrder(userId, orderId);
    }

    // 交易退款
    @PutMapping("/auth/refund/{orderId}")
    public Integer refundOrder(@RequestHeader(JwtHelper.X_USER_ID) Long userId,
                               @PathVariable("orderId") Long orderId) {
        return alipayService.refundOrder(userId, orderId);
    }

    // 交易退款查询
    @GetMapping("/auth/refund/query/{orderId}")
    public Integer queryRefundOrder(@RequestHeader(JwtHelper.X_USER_ID) Long userId,
                                    @PathVariable("orderId") Long orderId) {
        return alipayService.queryRefundOrder(userId, orderId);
    }
}
