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

    @Operation(summary = "新增订单")
    @PostMapping("/save")
    public Boolean save(@RequestBody OrderInfo orderInfo) {
        return orderInfoService.saveWarp(orderInfo);
    }

    @Operation(summary = "删除订单")
    @DeleteMapping("/remove/id/{id}")
    public Boolean delete(@PathVariable("id") @Min(1) Long id) {
        return orderInfoService.removeByIdWarp(id);
    }

    @Operation(summary = "批量删除订单")
    @DeleteMapping("/remove/batch")
    public Boolean deleteBatch(@RequestBody @NotEmpty List<Long> idList) {
        return orderInfoService.removeByIdsWarp(idList);
    }

    @Operation(summary = "修改订单")
    @PutMapping("/update")
    public Boolean update(@RequestBody OrderInfo orderInfo) {
        return orderInfoService.updateByIdWarp(orderInfo);
    }

    @Operation(summary = "设置订单状态")
    @PutMapping("/status/{id}/{status}")
    public Boolean setStatus(@PathVariable("id") @Min(1) Long id,
                             @PathVariable("status") @Min(-1) @Max(4) Integer status) {
        return orderInfoService.setStatus(id, status);
    }

    @Operation(summary = "通过ID获取订单")
    @GetMapping("/id/{id}")
    public OrderInfo getById(@PathVariable("id") @Min(1) Long id) {
        return orderInfoService.getByIdWarp(id);
    }

    @Operation(summary = "条件查询订单带分页")
    @PostMapping("/find")
    public IPage<OrderInfo> find(@RequestBody OrderInfoQueryVO orderInfoQueryVO,
                                 @RequestParam(value = "current", defaultValue = "1") @Min(1) Long current,
                                 @RequestParam(value = "size", defaultValue = "20") @Min(1) Long size) {
        return orderInfoService.find(orderInfoQueryVO, current, size);
    }

    @NoWrap
    @Operation(summary = "订单数据统计")
    @PostMapping("/inner/statistic")
    public OrderStatisticVO statistic(@RequestBody OrderStatisticVO orderStatisticVO) {
        return orderInfoService.statistic(orderStatisticVO);
    }
}
