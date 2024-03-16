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

    @Operation(summary = "获取下单支付页面",
               description = "获取指定订单ID的支付页面URL，需要提供用户ID（通过请求头JwtHelper.X_USER_ID获取）。")
    @GetMapping("/auth/pay/{orderId}")
    public String getPayPage(@RequestHeader(JwtHelper.X_USER_ID) Long userId,
                             @PathVariable("orderId") Long orderId) {
        return alipayService.getPayPage(userId, orderId);
    }

    @Operation(summary = "交易查询",
               description = "根据用户ID（通过请求头JwtHelper.X_USER_ID获取）和订单ID查询交易状态，返回交易状态码。")
    @GetMapping("/auth/query/{orderId}")
    public Integer queryOrder(@RequestHeader(JwtHelper.X_USER_ID) Long userId,
                              @PathVariable("orderId") Long orderId) {
        return alipayService.queryOrder(userId, orderId);
    }

    @Operation(summary = "交易关闭",
               description = "根据用户ID（通过请求头JwtHelper.X_USER_ID获取）和订单ID关闭交易，返回操作结果状态码。")
    @PutMapping("/auth/close/{orderId}")
    public Integer closeOrder(@RequestHeader(JwtHelper.X_USER_ID) Long userId,
                              @PathVariable("orderId") Long orderId) {
        return alipayService.closeOrder(userId, orderId);
    }

    @Operation(summary = "交易退款",
               description = "根据用户ID（通过请求头JwtHelper.X_USER_ID获取）和订单ID发起退款操作，返回退款操作结果状态码。")
    @PutMapping("/auth/refund/{orderId}")
    public Integer refundOrder(@RequestHeader(JwtHelper.X_USER_ID) Long userId,
                               @PathVariable("orderId") Long orderId) {
        return alipayService.refundOrder(userId, orderId);
    }

    @Operation(summary = "交易退款查询",
               description = "根据用户ID（通过请求头JwtHelper.X_USER_ID获取）和订单ID查询退款交易状态，返回退款交易状态码。")
    @GetMapping("/auth/refund/query/{orderId}")
    public Integer queryRefundOrder(@RequestHeader(JwtHelper.X_USER_ID) Long userId,
                                    @PathVariable("orderId") Long orderId) {
        return alipayService.queryRefundOrder(userId, orderId);
    }
}
