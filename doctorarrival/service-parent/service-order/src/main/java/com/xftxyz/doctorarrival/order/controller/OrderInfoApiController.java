package com.xftxyz.doctorarrival.order.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.xftxyz.doctorarrival.domain.order.OrderInfo;
import com.xftxyz.doctorarrival.helper.JwtHelper;
import com.xftxyz.doctorarrival.order.service.OrderInfoService;
import com.xftxyz.doctorarrival.vo.order.OrderInfoQueryParam;
import com.xftxyz.doctorarrival.vo.order.SubmitOrderParam;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Validated
@RestController
@RequiredArgsConstructor
@Tag(name = "订单相关")
@RequestMapping("/api/order/info")
public class OrderInfoApiController {

    private final OrderInfoService orderInfoService;

    @Operation(summary = "下单（生成订单）")
    @PostMapping("/auth/submit")
    public Long submitOrder(@RequestHeader(JwtHelper.X_USER_ID) Long userId,
                            @RequestBody @NotNull SubmitOrderParam submitOrderParam) {
        return orderInfoService.submitOrder(userId, submitOrderParam);
    }

    @Operation(summary = "通过ID获取订单")
    @GetMapping("/auth/detail/{orderId}")
    public OrderInfo getOrderDetail(@RequestHeader(JwtHelper.X_USER_ID) Long userId,
                                    @PathVariable("orderId") Long orderId) {
        return orderInfoService.getOrderDetail(userId, orderId);
    }

    @Operation(summary = "条件查询订单带分页")
    @PostMapping("/auth/list")
    public IPage<OrderInfo> getOrderList(@RequestHeader(JwtHelper.X_USER_ID) Long userId,
                                         @RequestBody @NotNull OrderInfoQueryParam orderInfoQueryParam,
                                         @RequestParam(value = "current", defaultValue = "1") Long current,
                                         @RequestParam(value = "size", defaultValue = "10") Long size) {
        return orderInfoService.getOrderList(userId, orderInfoQueryParam, current, size);
    }

    @Operation(summary = "取消订单")
    @PutMapping("/auth/cancel/{orderId}")
    public Boolean cancelOrder(@RequestHeader(JwtHelper.X_USER_ID) Long userId,
                               @PathVariable("orderId") Long orderId) {
        return orderInfoService.cancelOrder(userId, orderId);
    }

}
