package com.xftxyz.doctorarrival.order.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.xftxyz.doctorarrival.domain.order.OrderInfo;
import com.xftxyz.doctorarrival.order.service.OrderInfoService;
import com.xftxyz.doctorarrival.vo.order.OrderInfoQueryVO;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/admin/order/info")
public class OrderInfoAdminController {

    private final OrderInfoService orderInfoService;

    // 新增订单信息
    @PostMapping("/save")
    public Boolean save(@RequestBody OrderInfo orderInfo) {
        return orderInfoService.saveWarp(orderInfo);
    }

    // 删除订单信息
    @DeleteMapping("/remove/id/{id}")
    public Boolean delete(@PathVariable("id") @Min(1) Long id) {
        return orderInfoService.removeByIdWarp(id);
    }

    // 批量删除订单信息
    @DeleteMapping("/remove/batch")
    public Boolean deleteBatch(@RequestBody @NotEmpty List<Long> idList) {
        return orderInfoService.removeByIdsWarp(idList);
    }

    // 修改订单信息
    @PutMapping("/update")
    public Boolean update(@RequestBody OrderInfo orderInfo) {
        return orderInfoService.updateByIdWarp(orderInfo);
    }

    // 设置订单状态
    @PutMapping("/status/{id}/{status}")
    public Boolean setStatus(@PathVariable("id") @Min(1) Long id,
                             @PathVariable("status") @Min(-1) @Max(4) Integer status) {
        return orderInfoService.setStatus(id, status);
    }

    // 根据id查询订单信息
    @GetMapping("/id/{id}")
    public OrderInfo getById(@PathVariable("id") @Min(1) Long id) {
        return orderInfoService.getByIdWarp(id);
    }

    // 条件查询带分页
    @PostMapping("/find")
    public IPage<OrderInfo> find(@RequestBody OrderInfoQueryVO orderInfoQueryVO,
                                 @RequestParam(value = "current", defaultValue = "1") @Min(1) Long current,
                                 @RequestParam(value = "size", defaultValue = "20") @Min(1) Long size) {
        return orderInfoService.find(orderInfoQueryVO, current, size);
    }
}