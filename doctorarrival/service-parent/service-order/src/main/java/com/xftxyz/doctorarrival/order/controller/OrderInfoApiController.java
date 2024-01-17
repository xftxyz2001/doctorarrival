package com.xftxyz.doctorarrival.order.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.xftxyz.doctorarrival.common.helper.JwtHelper;
import com.xftxyz.doctorarrival.domain.order.OrderInfo;
import com.xftxyz.doctorarrival.order.service.OrderInfoService;
import com.xftxyz.doctorarrival.vo.order.SubmitOrderParam;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/order/info")
public class OrderInfoApiController {

    private final OrderInfoService orderInfoService;

    // 生成订单
    @PostMapping("/auth/submit")
    public Long submitOrder(@RequestHeader(JwtHelper.X_USER_ID) Long userId,
                            @RequestBody @NotNull SubmitOrderParam submitOrderParam) {
        return orderInfoService.submitOrder(userId, submitOrderParam);
    }

    // 根据id查询订单
    @GetMapping("/auth/detail/{orderId}")
    public OrderInfo getOrderDetail(@RequestHeader(JwtHelper.X_USER_ID) Long userId,
                                    @PathVariable("orderId") Long orderId) {
        return orderInfoService.getOrderDetail(userId, orderId);
    }

    // 获取订单列表
    @GetMapping("/auth/list")
    public IPage<OrderInfo> getOrderList(@RequestHeader(JwtHelper.X_USER_ID) Long userId,
                                         @RequestParam(value = "current", defaultValue = "1") Long current,
                                         @RequestParam(value = "size", defaultValue = "10") Long size) {
        return orderInfoService.getOrderList(userId, current, size);
    }

    // 取消订单
    @PutMapping("/auth/cancel/{orderId}")
    public Boolean cancelOrder(@RequestHeader(JwtHelper.X_USER_ID) Long userId,
                               @PathVariable("orderId") Long orderId) {
        return orderInfoService.cancelOrder(userId, orderId);
    }


}
