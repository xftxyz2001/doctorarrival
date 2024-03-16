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

    @Operation(summary = "下单（生成订单）",
               description = "用户通过JWT鉴权传递用户ID，并在请求体中提交必填的SubmitOrderParam参数，完成下单流程并生成新订单，成功后返回新生成的订单ID。")
    @PostMapping("/auth/submit")
    public Long submitOrder(@RequestHeader(JwtHelper.X_USER_ID) Long userId,
                            @RequestBody @NotNull SubmitOrderParam submitOrderParam) {
        return orderInfoService.submitOrder(userId, submitOrderParam);
    }

    @Operation(summary = "通过ID获取订单",
               description = "用户通过JWT鉴权传递用户ID，并在路径参数中指定订单ID，以获取该用户指定的订单详情信息。")
    @GetMapping("/auth/detail/{orderId}")
    public OrderInfo getOrderDetail(@RequestHeader(JwtHelper.X_USER_ID) Long userId,
                                    @PathVariable("orderId") Long orderId) {
        return orderInfoService.getOrderDetail(userId, orderId);
    }

    @Operation(summary = "条件查询订单带分页",
               description = "用户通过JWT鉴权传递用户ID，在请求体中提交非空的OrderInfoQueryParam对象作为查询条件，并指定当前页码（默认为1）和每页大小（默认为10），返回用户符合条件的订单列表分页结果。")
    @PostMapping("/auth/list")
    public IPage<OrderInfo> getOrderList(@RequestHeader(JwtHelper.X_USER_ID) Long userId,
                                         @RequestBody @NotNull OrderInfoQueryParam orderInfoQueryParam,
                                         @RequestParam(value = "current", defaultValue = "1") Long current,
                                         @RequestParam(value = "size", defaultValue = "10") Long size) {
        return orderInfoService.getOrderList(userId, orderInfoQueryParam, current, size);
    }

    @Operation(summary = "取消订单",
               description = "用户通过JWT鉴权传递用户ID，并在路径参数中指定要取消的订单ID，发起取消订单请求，成功取消后返回true。")
    @PutMapping("/auth/cancel/{orderId}")
    public Boolean cancelOrder(@RequestHeader(JwtHelper.X_USER_ID) Long userId,
                               @PathVariable("orderId") Long orderId) {
        return orderInfoService.cancelOrder(userId, orderId);
    }

}
