package com.xftxyz.doctorarrival.order.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.xftxyz.doctorarrival.annotation.NoWrap;
import com.xftxyz.doctorarrival.domain.order.OrderInfo;
import com.xftxyz.doctorarrival.order.service.OrderInfoService;
import com.xftxyz.doctorarrival.vo.order.OrderInfoQueryVO;
import com.xftxyz.doctorarrival.vo.order.OrderStatisticVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Validated
@RestController
@RequiredArgsConstructor
@Tag(name = "订单管理")
@RequestMapping("/admin/order/info")
public class OrderInfoAdminController {

    private final OrderInfoService orderInfoService;

    @Operation(summary = "新增订单",
               description = "接收并保存一个完整的OrderInfo对象作为新的订单信息，成功保存则返回true。")
    @PostMapping("/save")
    public Boolean save(@RequestBody OrderInfo orderInfo) {
        return orderInfoService.saveWarp(orderInfo);
    }

    @Operation(summary = "删除订单",
               description = "根据传入的订单ID（最小值为1）删除指定的订单，删除成功则返回true。")
    @DeleteMapping("/remove/id/{id}")
    public Boolean delete(@PathVariable("id") @Min(1) Long id) {
        return orderInfoService.removeByIdWarp(id);
    }

    @Operation(summary = "批量删除订单",
               description = "接收一个非空的Long类型的ID列表，批量删除指定的订单，删除成功则返回true。")
    @DeleteMapping("/remove/batch")
    public Boolean deleteBatch(@RequestBody @NotEmpty List<Long> idList) {
        return orderInfoService.removeByIdsWarp(idList);
    }

    @Operation(summary = "修改订单",
               description = "接收并更新一个完整的OrderInfo对象，根据其ID进行订单信息更新，成功更新则返回true。")
    @PutMapping("/update")
    public Boolean update(@RequestBody OrderInfo orderInfo) {
        return orderInfoService.updateByIdWarp(orderInfo);
    }

    @Operation(summary = "设置订单状态",
               description = "根据传入的订单ID（最小值为1）和状态值（-1至4之间的整数），更新订单状态，成功更新则返回true。")
    @PutMapping("/status/{id}/{status}")
    public Boolean setStatus(@PathVariable("id") @Min(1) Long id,
                             @PathVariable("status") @Min(-1) @Max(4) Integer status) {
        return orderInfoService.setStatus(id, status);
    }

    @Operation(summary = "通过ID获取订单",
               description = "根据传入的订单ID（最小值为1），获取指定订单的详细信息。")
    @GetMapping("/id/{id}")
    public OrderInfo getById(@PathVariable("id") @Min(1) Long id) {
        return orderInfoService.getByIdWarp(id);
    }

    @Operation(summary = "条件查询订单带分页",
               description = "接收一个非空的OrderInfoQueryVO对象作为查询条件，以及可选的当前页码（默认为1，最小值为1）和每页大小（默认为20，最小值为1），返回符合查询条件的订单分页列表。")
    @PostMapping("/find")
    public IPage<OrderInfo> find(@RequestBody OrderInfoQueryVO orderInfoQueryVO,
                                 @RequestParam(value = "current", defaultValue = "1") @Min(1) Long current,
                                 @RequestParam(value = "size", defaultValue = "20") @Min(1) Long size) {
        return orderInfoService.find(orderInfoQueryVO, current, size);
    }

    @NoWrap
    @Operation(summary = "订单数据统计",
               description = "接收一个非空的OrderStatisticVO对象，根据其中的参数进行订单数据统计计算，返回统计结果。")
    @PostMapping("/inner/statistic")
    public OrderStatisticVO statistic(@RequestBody OrderStatisticVO orderStatisticVO) {
        return orderInfoService.statistic(orderStatisticVO);
    }
}
