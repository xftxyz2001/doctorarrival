package com.xftxyz.doctorarrival.order.controller;

import com.xftxyz.doctorarrival.helper.JwtHelper;
import com.xftxyz.doctorarrival.order.service.AlipayService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Validated
@RestController
@RequiredArgsConstructor
@Tag(name = "支付宝支付相关")
@RequestMapping("/api/order/alipay")
public class AlipayApiController {

    private final AlipayService alipayService;

    @Operation(summary = "获取下单支付页面")
    @GetMapping("/auth/pay/{orderId}")
    public String getPayPage(@RequestHeader(JwtHelper.X_USER_ID) Long userId,
                             @PathVariable("orderId") Long orderId) {
        return alipayService.getPayPage(userId, orderId);
    }

    @Operation(summary = "交易查询")
    @GetMapping("/auth/query/{orderId}")
    public Integer queryOrder(@RequestHeader(JwtHelper.X_USER_ID) Long userId,
                              @PathVariable("orderId") Long orderId) {
        return alipayService.queryOrder(userId, orderId);
    }

    @Operation(summary = "交易关闭")
    @PutMapping("/auth/close/{orderId}")
    public Integer closeOrder(@RequestHeader(JwtHelper.X_USER_ID) Long userId,
                              @PathVariable("orderId") Long orderId) {
        return alipayService.closeOrder(userId, orderId);
    }

    @Operation(summary = "交易退款")
    @PutMapping("/auth/refund/{orderId}")
    public Integer refundOrder(@RequestHeader(JwtHelper.X_USER_ID) Long userId,
                               @PathVariable("orderId") Long orderId) {
        return alipayService.refundOrder(userId, orderId);
    }

    @Operation(summary = "交易退款查询")
    @GetMapping("/auth/refund/query/{orderId}")
    public Integer queryRefundOrder(@RequestHeader(JwtHelper.X_USER_ID) Long userId,
                                    @PathVariable("orderId") Long orderId) {
        return alipayService.queryRefundOrder(userId, orderId);
    }
}
